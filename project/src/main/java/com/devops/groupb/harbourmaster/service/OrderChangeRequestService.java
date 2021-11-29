package com.devops.groupb.harbourmaster.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.OrderChangeRequest;
import com.devops.groupb.harbourmaster.dto.OrderChangeRequestStatus;

import com.devops.groupb.harbourmaster.dao.OrderChangeRequestDAO;
import com.devops.groupb.harbourmaster.dao.OrderDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderChangeRequestService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private OrderChangeRequestDAO orderChangeRequestDAO;

	@Autowired
	private OrderDAO orderDAO;

	public OrderChangeRequest retrieveOrderChangeRequest(UUID uuid) {
		return orderChangeRequestDAO.findByUUID(uuid);
	}

	public OrderChangeRequest placeOrderChangeRequest(UUID parentUUID, OrderChangeRequest ocr, String reason) {
		Order parentOrder = orderDAO.findByUUID(parentUUID);
		if (parentOrder == null) {
			return null;
		}

		ocr.setParentUUID(parentUUID);
		ocr.setStatus(OrderChangeRequestStatus.REQUESTED);
		ocr.setReason(reason);

		List<UUID> changeRequests = parentOrder.getChangeRequests();
		changeRequests.add(ocr.getUuid());

		parentOrder.setStatus(OrderStatus.CHANGE_REQUESTED);
		parentOrder.setChangeRequests(changeRequests);
		orderDAO.save(parentOrder);

		orderChangeRequestDAO.save(ocr);

		return ocr;
	}

	public OrderChangeRequest acceptOrderChangeRequest(UUID uuid) {
		OrderChangeRequest ocr = orderChangeRequestDAO.findByUUID(uuid);

		if (ocr == null) {
			return null;
		}

		Order parentOrder = orderDAO.findByUUID(ocr.getParentUUID());

		ocr.setStatus(OrderChangeRequestStatus.APPROVED);

		parentOrder.setStatus(OrderStatus.CONFIRMED);
		parentOrder.setShip(ocr.getShip());
		parentOrder.setBerth(ocr.getBerth());
		parentOrder.setDayOfArrival(ocr.getDayOfArrival());

		return ocr;
	}

	public OrderChangeRequest denyOrderChangeRequest(UUID uuid) {
		OrderChangeRequest ocr = orderChangeRequestDAO.findByUUID(uuid);

		if (ocr == null) {
			return null;
		}

		Order parentOrder = orderDAO.findByUUID(ocr.getParentUUID());

		parentOrder.setStatus(OrderStatus.CONFIRMED);

		ocr.setStatus(OrderChangeRequestStatus.DENIED);

		return ocr;
	}
}
