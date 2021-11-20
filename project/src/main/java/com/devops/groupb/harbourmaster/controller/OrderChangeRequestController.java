package com.devops.groupb.harbourmaster.controller;

import java.util.UUID;

import com.devops.groupb.harbourmaster.service.OrderChangeRequestService;
import com.devops.groupb.harbourmaster.dto.OrderChangeRequest;
import com.devops.groupb.harbourmaster.dto.OrderChangeRequestWrapper;
import com.devops.groupb.harbourmaster.dto.CreateOrderWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "Controller for handling common operations on order change requests.")
public class OrderChangeRequestController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	OrderChangeRequestService orderChangeRequestService;

	@GetMapping(value = "/api/orderChangeRequest/{uuid}")
	@ApiOperation("Returns an order change request by its UUID.")
	public ResponseEntity<Object> findOrder(@PathVariable UUID uuid) {
		log.info("(GET) /api/orderChangeRequest: entered.");
		log.info("(GET) /api/orderChangeRequest: order change request '" + uuid + "' requested.");

		OrderChangeRequest ocr = orderChangeRequestService.retrieveOrderChangeRequest(uuid);

		return ocr != null
			? new ResponseEntity<>(ocr, HttpStatus.OK)
			: new ResponseEntity<>(String.format("ERROR: Order change request '%s' not found. This order may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/api/orderChangeRequest/create", method = RequestMethod.POST)
	@ApiOperation("Creates an order change request for a given order.")
	public ResponseEntity<Object> createOrderChangeRequest(@RequestBody OrderChangeRequestWrapper request) {
		log.info("/api/orderChangeRequest/create: entered.");
		log.info("/api/orderChangeRequest/create: retrieved new request '" + request + "'.");

		CreateOrderWrapper newOrder = request.getNewRequest();

		OrderChangeRequest ocr = new OrderChangeRequest(newOrder.getShip(), newOrder.getBerth(), newOrder.getDate());

		return orderChangeRequestService.placeOrderChangeRequest(request.getParentUUID(), ocr, request.getReason()) != null
			? new ResponseEntity<>(orderChangeRequestService.retrieveOrderChangeRequest(ocr.getUuid()), HttpStatus.CREATED)
			: new ResponseEntity<>(String.format("ERROR: Order '%s' not found. This order may not exist in the database.", request.getParentUUID()), HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/api/orderChangeRequest/accept/{uuid}")
	@ApiOperation("Accepts a given order change request and amends the parent order.")
	public ResponseEntity<Object> acceptOrderChangeRequest(@PathVariable UUID uuid) {
		log.info("/api/orderChangeRequest/accept: entered.");
		log.info("/api/orderChangeRequest/accept: acceptance of order '" + uuid + "' requested.");

		OrderChangeRequest ocr = orderChangeRequestService.acceptOrderChangeRequest(uuid);

		return ocr != null
			? new ResponseEntity<>(orderChangeRequestService.retrieveOrderChangeRequest(ocr.getUuid()), HttpStatus.CREATED)
			: new ResponseEntity<>(String.format("ERROR: Order change request '%s' not found. This order may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/api/orderChangeRequest/deny/{uuid}")
	@ApiOperation("Denies a given order change request.")
	public ResponseEntity<Object> denyOrderChangeRequest(@PathVariable UUID uuid) {
		log.info("/api/orderChangeRequest/deny: entered.");
		log.info("/api/orderChangeRequest/deny: denial of order '" + uuid + "' requested.");

		OrderChangeRequest ocr = orderChangeRequestService.denyOrderChangeRequest(uuid);

		return ocr != null
			? new ResponseEntity<>(orderChangeRequestService.retrieveOrderChangeRequest(ocr.getUuid()), HttpStatus.CREATED)
			: new ResponseEntity<>(String.format("ERROR: Order change request '%s' not found. This order may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
	}
}
