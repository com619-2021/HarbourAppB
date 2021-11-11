package com.devops.groupb.harbourmaster.controller;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
public class OrderController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	OrderService orderService;

	@GetMapping(value = "/api/order/find")
	@ResponseBody
	public ResponseEntity<Object> findOrder(@RequestParam UUID uuid) {
		log.info("/api/order/find: entered.");
		log.info("/api/order/find: order '" + uuid + "' requested.");

		Order order = orderService.retrieveOrder(uuid);

		if (order == null) {
			return new ResponseEntity<>(String.format("ERROR: Order '%s' not found. This order may not exist in the database.", uuid),	HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(order, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api/order/cancel")
	@ResponseBody
	public ResponseEntity<Object> cancelOrder(@RequestParam UUID uuid, @RequestParam(required = false) String reason) {
		log.info("/api/order/cancel: entered.");
		log.info("/api/order/cancel: cancellation of order #" + uuid + " requested.");

		if (orderService.cancelOrder(uuid, reason)) {
			return new ResponseEntity<>(String.format("Order '%s' has been set to %s.\nReason: '%s'", uuid, OrderStatus.CANCELLED.name(), reason), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(String.format("ERROR: Order '%s' not found. This order may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
		}
	}
}
