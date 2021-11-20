package com.devops.groupb.harbourmaster.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
		if (pilots.isEmpty()) {
			order.setStatus(OrderStatus.DENIED);
			order.setReason("No pilots available.");
			orderDAO.save(order);
			return order;
		}

		LocalDate date = order.getRequestedDate();
		Ship ship = order.getShip();

		List<Tide> tides = tideDAO.getSafeTidesOnDay(date.getDayOfWeek(), ship.getDraft());

		Pilot chosenPilot = null;
		for (Pilot p : pilots) {
			List<TimePeriod> occupiedOnDate = p.getOccupiedTimes().get(date);

			if (occupiedOnDate == null) {
				Map<LocalDate, ArrayList<TimePeriod>> occupiedTimes = p.getOccupiedTimes();

				ArrayList<TimePeriod> newOccupied = new ArrayList<TimePeriod>();
				newOccupied.add(new TimePeriod(tides.get(0).getStart(), tides.get(0).getStart().plusHours(1L)));

				occupiedTimes.put(date, newOccupied);
				chosenPilot = p;
			}

			/* wrong !!!!!! :(((( */
			if (chosenPilot == null) {
				for (TimePeriod t : occupiedOnDate) {
					for (Tide x : tides)
						if (!(x.getStart().isAfter(t.getStart()) && x.getStart().isBefore(t.getEnd()))) {
							Map<LocalDate, ArrayList<TimePeriod>> occupiedTimes = p.getOccupiedTimes();

							ArrayList<TimePeriod> newOccupied = new ArrayList<TimePeriod>();
							newOccupied.add(new TimePeriod(x.getStart(), x.getStart().plusHours(1L)));

							occupiedTimes.put(date, newOccupied);
							chosenPilot = p;

							pilotDAO.save(chosenPilot);

							break;
						}
				}
			}
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
