package com.devops.groupb.harbourmaster.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dao.TideDAO;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.TimePeriod;
import com.devops.groupb.harbourmaster.dao.OrderDAO;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.Tide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private TideDAO tideDAO;

	@Autowired
	private PilotDAO pilotDAO;

	public Order retrieveOrder(UUID uuid) {
		return orderDAO.findByUUID(uuid);
	}

	public Order findConfirmedByShipUUID(UUID uuid) {
		return orderDAO.findConfirmedByShipUUID(uuid);
	}

	public List<Order> findAll() {
		return orderDAO.findAll();
	}

	public Order placeOrder(Order order, List<Pilot> pilots) {
		if (pilots == null) {
			order.setStatus(OrderStatus.DENIED);
			order.setReason("No pilots are available.");
			orderDAO.save(order);
			return order;
		}

		LocalDate date = order.getRequestedDate();
		Ship ship = order.getShip();

		List<Tide> safeTides = tideDAO.getSafeTidesOnDay(date.getDayOfWeek(), ship.getDraft());

		Pilot chosenPilot = schedulePilot(pilots, safeTides, order, order.getRequestedDate(), true);

		if (chosenPilot == null) {
			order.setStatus(OrderStatus.DENIED);
			order.setReason("No pilots are available.");
			orderDAO.save(order);
			return order;
		}


		return order;
	}

	public Boolean cancelOrder(UUID uuid, String reason) {
		Order order = orderDAO.findByUUID(uuid);
		if (order == null) {
			return false;
		}

		log.info("Found matching order: " + order + ".");
		order.setStatus(OrderStatus.CANCELLED);
		order.setReason(reason);

		Pilot pilot = order.getPilot();
		ArrayList<TimePeriod> occupiedOnDate;

		if (pilot.getOccupiedTimes() != null) {
			occupiedOnDate = pilot.getOccupiedTimes().get(order.getRequestedDate());
		} else {
			return false;
		}

		for (TimePeriod time : occupiedOnDate) {
			if ((time.getStart().equals(order.getAllocatedStart().toLocalTime()))) {
				occupiedOnDate.remove(time);
				break;
			}
		}

		pilotDAO.save(pilot);
		orderDAO.save(order);
		return true;
	}

	public Pilot schedulePilot(List<Pilot> pilots, List<Tide> safeTides, Order order, LocalDate date, Boolean writeToSchedule) {
		if (pilots == null) {
			return null;
		}

		for (Pilot p : pilots) {
			/* checks to see whether this pilot works on the day that the
			   order has requested. */
			if (p.getWorkingHours().get(date.getDayOfWeek()) == null) {
				break;
			} else {
				/* if the pilot has no 'occupiedOnDate' list, they are completely
				   free for work on the given day. */
				if (!p.getOccupiedTimes().containsKey(date)) {
					TimePeriod workingHours = p.getWorkingHours().get(date.getDayOfWeek());
					ArrayList<TimePeriod> occupiedOnDate = new ArrayList<TimePeriod>();
					log.info("WORKING HOURS: " + workingHours.getStart() + " -> " + workingHours.getEnd());

					for (Tide tide : safeTides) {
						Boolean possible = false;

						LocalTime targetStart = tide.getStart();
						LocalTime targetEnd = targetStart.plusHours(1L);

						log.info("TIDE: " + tide.getStart() + " -> " + tide.getEnd());

						/* tide is out of working hours range */
						if (tide.getEnd().minusHours(1L).isBefore(workingHours.getStart())
							|| tide.getStart().isAfter(workingHours.getEnd())) {
							log.info("TIDE IS OUTSIDE OF WORKING HOURS.");
						} else {
							/* sets the targetStart and targetEnd relative to workingHours
							   to avoid useless computation of times before. */
							if (tide.getStart().isBefore(workingHours.getStart())) {
								targetStart = workingHours.getStart();
								targetEnd = targetStart.plusHours(1L);
								log.info("TARGET: " + targetStart + " -> " + targetEnd);
							}

							/* whilst the targeted start time is before the end of the pilot's work shift and the targeted end time is before
							   the end of the current tide (i.e. the range is between the pilot's workingHours), keep incrementing by an hour
							   each time (the length of each booking) until a range is reached where it's after the starting time of the pilot's
							   work day and before the end of the tide. after that point, the next tide is tested. */
							while (targetStart.isBefore(workingHours.getEnd().minusHours(1L))
								   && targetEnd.isBefore(tide.getEnd()) && targetEnd.isBefore(workingHours.getEnd())) {
								if (targetEnd.isBefore(tide.getEnd()) && (targetStart.isAfter(workingHours.getStart())
																		  || targetStart.equals(workingHours.getStart()))) {
									possible = true;
									break;
								}

								/* increments the booking time range by an hour (length of a booking) each iteration. */
								targetStart = targetStart.plusHours(1L);
								targetEnd = targetStart.plusHours(1L);
								log.info("TARGET: " + targetStart + " -> " + targetEnd);
							}
						}

						/* if a booking is possible here, add it to the pilot's (new) 'occupiedTimes' list. */
						if (possible) {
							log.info("POSSIBLE!");

							if (writeToSchedule) {
								occupiedOnDate.add(new TimePeriod(targetStart, targetEnd));
								p.getOccupiedTimes().put(date, occupiedOnDate);
							}

							if (order != null) {
								LocalDateTime allocatedStart = LocalDateTime.of(date, targetStart);
								LocalDateTime allocatedEnd = LocalDateTime.of(date, targetEnd);

								order.setPilot(p);
								order.setAllocatedStart(allocatedStart);
								order.setAllocatedEnd(allocatedEnd);
								order.setStatus(OrderStatus.CONFIRMED);

								orderDAO.save(order);

								pilotDAO.save(p);
							}

							return p;
						}
					}
				} else {
					ArrayList<TimePeriod> occupiedOnDate = p.getOccupiedTimes().get(date);
					Boolean possible = false;
					TimePeriod workingHours = p.getWorkingHours().get(date.getDayOfWeek());
					log.info("WORKING HOURS: " + workingHours.getStart() + " -> " + workingHours.getEnd());

					for (Tide tide : safeTides) {
						LocalTime targetStart = tide.getStart();
						LocalTime targetEnd = targetStart.plusHours(1L);

						if (possible) {
							break;
						}

						log.info("TIDE: " + tide.getStart() + " -> " + tide.getEnd());

						/* tide is out of working hours range */
						if (tide.getEnd().minusHours(1L).isBefore(workingHours.getStart())
							|| tide.getStart().isAfter(workingHours.getEnd())) {
							log.info("TIDE IS OUTSIDE OF WORKING HOURS.");
						} else {
							/* sets the targetStart and targetEnd relative to workingHours
							   to avoid useless computation of times before. */
							if (tide.getStart().isBefore(workingHours.getStart())) {
								targetStart = workingHours.getStart();
								targetEnd = targetStart.plusHours(1L);
								log.info("TARGET: " + targetStart + " -> " + targetEnd);
							}

							/* wtf */
							while (targetStart.isBefore(workingHours.getEnd().minusHours(1L))
								   && targetEnd.isBefore(tide.getEnd()) && targetEnd.isBefore(workingHours.getEnd())) {
								if ((targetEnd.isBefore(tide.getEnd()) || targetEnd.equals(tide.getEnd()))
									&& (targetStart.isAfter(workingHours.getStart()) || targetStart.equals(workingHours.getStart()))) {
									log.info("VALID TIME FOUND. CHECKING AGAINST OCCUPIED TIMES.");
									for (TimePeriod time : occupiedOnDate) {
										log.info("possible = " + possible);
										possible = true;
										log.info("OCCUPIED: " + time.getStart() + " -> " + time.getEnd());
										log.info("TARGET: " + targetStart + " -> " + targetEnd);

										if (((targetStart.isAfter(time.getStart()) || targetStart.equals(time.getStart())) && (targetStart.isBefore(time.getEnd()) || targetStart.equals(time.getEnd())))
											|| ((targetEnd.isAfter(time.getStart()) || targetEnd.equals(time.getStart())) && (targetEnd.isBefore(time.getEnd()) || targetEnd.equals(time.getEnd())))) {
											possible = false;
											break;
										}
									}
								}

								/* if a booking is possible here, add it to the pilot's (new) 'occupiedTimes' list. */
								if (possible) {
									log.info("POSSIBLE!");

									if (writeToSchedule) {
										occupiedOnDate.add(new TimePeriod(targetStart, targetEnd));
										p.getOccupiedTimes().put(date, occupiedOnDate);
									}

									if (order != null) {
										LocalDateTime allocatedStart = LocalDateTime.of(date, targetStart);
										LocalDateTime allocatedEnd = LocalDateTime.of(date, targetEnd);

										order.setPilot(p);
										order.setAllocatedStart(allocatedStart);
										order.setAllocatedEnd(allocatedEnd);
										order.setStatus(OrderStatus.CONFIRMED);

										orderDAO.save(order);

										pilotDAO.save(p);
									}

									return p;
								} else {
									/* increments the booking time range by an hour (length of a booking) each iteration. */
									targetStart = targetStart.plusHours(1L);
									targetEnd = targetStart.plusHours(1L);
									log.info("TARGET: " + targetStart + " -> " + targetEnd);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
