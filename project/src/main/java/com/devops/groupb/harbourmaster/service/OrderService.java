package com.devops.groupb.harbourmaster.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dao.OrderDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private OrderDAO orderDAO;

	public Order retrieveOrder(UUID uuid) {
		return orderDAO.findByUUID(uuid);
	}

	public Order placeOrder(Order order, Pilot pilot) {
		if (pilot == null) {
			order.setStatus(OrderStatus.DENIED);
			order.setReason("No pilots available.");
			orderDAO.save(order);
			return order;
		}

		LocalDateTime allocatedTime = order.getRequestedDate().atTime(13, 00);
		order.setPilot(pilot);
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
