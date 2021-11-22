package com.devops.groupb.harbourmaster.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dao.OrderDAO;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.dao.TideDAO;
import com.devops.groupb.harbourmaster.dto.TimePeriod;


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
		Pilot chosenPilot = null;

		for (Pilot p : pilots) {
			/* checks to see whether this pilot works on the day that the
			   order has requested. */

			Boolean spotAvailable = false;

			if (chosenPilot != null) {
				break;
			}

			if (p.getWorkingHours().get(date.getDayOfWeek()) == null) {
				break;
			} else {
				ArrayList<TimePeriod> occupiedOnDate = p.getOccupiedTimes().get(date);

				/* if the pilot has no 'occupiedOnDate' list, they are completely
				   free for work on the given day. */
				if (occupiedOnDate == null) {
					TimePeriod workingHours = p.getWorkingHours().get(date.getDayOfWeek());
					occupiedOnDate = new ArrayList<TimePeriod>();
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
							possible = false;
						} else {
							log.info("TARGET: " + targetStart + " -> " + targetEnd);
							/* whilst the targeted start time is before the end of the pilot's work shift and the targeted end time is before
							   the end of the current tide, keep incrementing by an hour each time (the length of each booking) until a range
							   is reached where it's after the starting time of the pilot's work day and before the end of the tide. after
							   that point, the next tide is tested. */
							while (targetStart.isBefore(workingHours.getEnd().minusHours(1L))
								   && targetEnd.isBefore(tide.getEnd()) && targetEnd.isBefore(workingHours.getEnd())) {
								possible = false;

								if (targetEnd.isBefore(tide.getEnd()) && (targetStart.isAfter(workingHours.getStart())
																		  || targetStart.equals(workingHours.getStart()))) {
									possible = true;
									break;
								}

								targetStart = targetStart.plusHours(1L);
								targetEnd = targetStart.plusHours(1L);
								log.info("TARGET: " + targetStart + " -> " + targetEnd);
							}
						}

						/* if a booking is possible here, add it to the pilot's (new) 'occupiedTimes' list. */
						if (possible) {
							log.info("POSSIBLE!");
							occupiedOnDate.add(new TimePeriod(targetStart, targetEnd));
							p.getOccupiedTimes().put(date, occupiedOnDate);

							pilotDAO.save(p);

							chosenPilot = p;
							break;
						}
					}
				} else {
					spotAvailable = false;
					TimePeriod workingHours = p.getWorkingHours().get(date.getDayOfWeek());
					log.info("WORKING HOURS: " + workingHours.getStart() + " -> " + workingHours.getEnd());
					LocalTime targetStart, targetEnd;

					for (Tide tide : safeTides) {
						targetStart = tide.getStart();
						targetEnd = targetStart.plusHours(1L);

						if (spotAvailable) {
							break;
						}

						log.info("TIDE: " + tide.getStart() + " -> " + tide.getEnd());

						/* tide is out of working hours range */
						if (tide.getEnd().minusHours(1L).isBefore(workingHours.getStart())
							|| tide.getStart().isAfter(workingHours.getEnd())) {
							log.info("TIDE IS OUTSIDE OF WORKING HOURS.");
							spotAvailable = false;
						} else {
							log.info("TARGET: " + targetStart + " -> " + targetEnd);
							while (targetStart.isBefore(workingHours.getEnd().minusHours(1L))
								   && targetEnd.isBefore(tide.getEnd()) && targetEnd.isBefore(workingHours.getEnd()) && !spotAvailable) {
								spotAvailable = false;

								if (targetEnd.isBefore(tide.getEnd()) && (targetStart.isAfter(workingHours.getStart())
																		  || targetStart.equals(workingHours.getStart()))) {
									for (TimePeriod time : occupiedOnDate) {
										if (!((targetStart.isAfter(time.getStart()) && targetStart.isBefore(time.getEnd()))) &&
											(targetEnd.isAfter(time.getStart()) && targetEnd.isBefore(time.getEnd()))) {
											spotAvailable = true;
											break;
										}
									}

									if (spotAvailable) {
										break;
									}

									break;
								}

								targetStart = targetStart.plusHours(1L);
								targetEnd = targetEnd.plusHours(1L);
							}
						}

						if (spotAvailable && chosenPilot == null) {
							log.info("POSSIBLE!");
							occupiedOnDate.add(new TimePeriod(targetStart, targetEnd));
							p.getOccupiedTimes().put(date, occupiedOnDate);

							pilotDAO.save(p);

							chosenPilot = p;
							break;
						}
					}
				}

			}

		}

		if (chosenPilot == null) {
			order.setStatus(OrderStatus.DENIED);
			order.setReason("No pilots are available.");
			orderDAO.save(order);
			return order;
		}

		LocalDateTime allocatedTime = order.getRequestedDate().atTime(13, 00);
		order.setPilot(chosenPilot);
		order.setAllocatedTime(allocatedTime);
		order.setStatus(OrderStatus.CONFIRMED);

		orderDAO.save(order);

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

		// Free up the time from the pilot's schedule.

		orderDAO.save(order);
		return true;
	}
}
