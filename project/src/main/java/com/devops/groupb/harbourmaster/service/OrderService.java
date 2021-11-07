package com.devops.groupb.harbourmaster.service;

import java.util.Random;
import java.util.List;
import java.util.Arrays;

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

	public Boolean placeOrder(Order order) {
		order.setStatus(OrderStatus.PLACED);

		orderDAO.save(order);
		// Any additional checks to go here.

		return true;
	}

	public Boolean requestOrderChange(int orderId) {

		return true;
	}

}
