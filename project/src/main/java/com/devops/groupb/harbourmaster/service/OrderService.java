package com.devops.groupb.harbourmaster.service;

import java.util.UUID;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;

import com.devops.groupb.harbourmaster.dao.OrderDAO;

@Service
public class OrderService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private OrderDAO orderDAO;

	public Order retrieveOrder(UUID uuid) {
		return orderDAO.findByUUID(uuid);
	}

	public Boolean placeOrder(Order order, Pilot pilot) {
		if (pilot == null) {
			order.setStatus(OrderStatus.DENIED);
			order.setReason("No pilots available.");
			orderDAO.save(order);
			return false;
		}

		LocalDateTime allocatedTime = order.getRequestedDate().atTime(03, 00);
		order.setPilot(pilot);
		order.setAllocatedTime(allocatedTime);
		order.setStatus(OrderStatus.PLACED);

		orderDAO.save(order);

		return true;
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

	public Boolean requestOrderChange(UUID uuid) {
		return true;
	}
}
