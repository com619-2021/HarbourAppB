package com.devops.groupb.harbourmaster.controller;

import java.util.UUID;

import com.devops.groupb.harbourmaster.service.OrderService;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.CreateOrderWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "Controller for handling common operations on orders.")
public class OrderController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	OrderService orderService;

	@GetMapping(value = "/api/order/{uuid}")
	@ApiOperation("Returns an order by its UUID.")
	public ResponseEntity<Object> findOrder(@PathVariable UUID uuid) {
		log.info("(GET) /api/order: entered.");
		log.info("(GET) /api/order: order '" + uuid + "' requested.");

		Order order = orderService.retrieveOrder(uuid);

		return order != null
			? new ResponseEntity<>(order, HttpStatus.OK)
			: new ResponseEntity<>(String.format("ERROR: Order '%s' not found. This order may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/api/order/cancel")
	@ApiOperation("Cancels an order with an optional reason.")
	public ResponseEntity<Object> cancelOrder(@RequestParam UUID uuid, @RequestParam(required = false) String reason) {
		log.info("/api/order/cancel: entered.");
		log.info("/api/order/cancel: cancellation of order #" + uuid + " requested.");

		return orderService.cancelOrder(uuid, reason)
			? new ResponseEntity<>(orderService.retrieveOrder(uuid), HttpStatus.CREATED)
			: new ResponseEntity<>(String.format("ERROR: Order '%s' not found. This order may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
	}
}
