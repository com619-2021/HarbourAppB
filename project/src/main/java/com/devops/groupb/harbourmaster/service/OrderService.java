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
			LocalTime targetStart = LocalTime.of(0, 0, 0);
			LocalTime targetEnd = targetStart.plusHours(1L);

			ArrayList<TimePeriod> occupiedOnDate = p.getOccupiedTimes().get(date);

			if (occupiedOnDate == null) {
				occupiedOnDate = new ArrayList<TimePeriod>();
				occupiedOnDate.add(new TimePeriod(safeTides.get(0).getStart(), safeTides.get(0).getStart().plusHours(1L)));
				p.getOccupiedTimes().put(date, occupiedOnDate);

				pilotDAO.save(p);

				chosenPilot = p;
				break;
			}


			Boolean spotAvailable = true;

			for (TimePeriod time : occupiedOnDate) {
				if (targetStart.isAfter(LocalTime.of(23, 0)) || targetStart.equals(LocalTime.of(23, 0))) {
					spotAvailable = false;
					break;
				}

				while (targetStart.isAfter(time.getStart()) && targetEnd.isBefore(time.getEnd()) || targetStart.equals(time.getStart())) {
					spotAvailable = false;
					targetStart = targetStart.plusHours(1L);
					targetEnd = targetEnd.plusHours(1L);
				}
				spotAvailable = true;
			}

			if (spotAvailable) {
				occupiedOnDate.add(new TimePeriod(targetStart, targetEnd));
				p.getOccupiedTimes().put(date, occupiedOnDate);

				pilotDAO.save(p);

				chosenPilot = p;
				break;
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
