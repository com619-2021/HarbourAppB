package com.devops.groupb.harbourmaster.controller;

import java.time.LocalDateTime;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import com.devops.groupb.harbourmaster.dto.Receipt;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.TimeRequestWrapper;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.CreateOrderWrapper;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.service.OrderService;
import com.devops.groupb.harbourmaster.service.PilotService;
import com.devops.groupb.harbourmaster.service.TideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "Controller for the major REST endpoints.")
public class RestAPIController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	PilotService pilotService;

	@Autowired
	OrderService orderService;

	@Autowired
	TideService tideService;

	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation("Creates a booking using the given request.")
	public ResponseEntity<Object> bookPilot(@RequestBody CreateOrderWrapper request) {
		log.info("/api/bookPilot: entered.");
		log.info("/api/bookPilot: retrieved " + request + " from request body.");

		/* write sanity checks for request values; i.e. "lat" must
		   be provided and "la" = auto denial. a draft of 0 = impossible. */

		List<Pilot> pilots = pilotService.findSuitablePilots(request.getShip().getType());

		Order order = new Order(request.getShip(), request.getBerth(), request.getDate());
		orderService.placeOrder(order, pilots);

		Receipt receipt;
		if (order.getStatus() == OrderStatus.CONFIRMED) {
			receipt = new Receipt(order.getStatus(), order.getOrderDate(), order.getAllocatedStart(),
								  order.getAllocatedEnd(), order.getFare(), order.getUUID(), order.getShip().getUUID(),
								  order.getBerth().getUUID(), order.getPilot().getUUID(), order.getPilot().getFirstName(),
								  order.getPilot().getLastName());
		} else {
			receipt = new Receipt(order.getStatus(), order.getOrderDate(), order.getAllocatedStart(),
								  order.getAllocatedEnd(), order.getFare(), order.getUUID(), order.getShip().getUUID(),
								  order.getBerth().getUUID(), null, null, null);
		}

		return new ResponseEntity<>(receipt, HttpStatus.CREATED);
	}

	@GetMapping(value = "/api/callPilot/{shipUUID}", produces = "application/json")
	@ApiOperation("Calls a pilot to lead the ship out of port.")
	public ResponseEntity<Object> callPilot(@PathVariable UUID shipUUID) {
		log.info("/api/callPilot: entered.");
		log.info("/api/callPilot: pilot for ship '" + shipUUID + "' called.");

		Order order = orderService.findConfirmedByShipUUID(shipUUID);

		if (order == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		Ship ship = order.getShip();
		Berth berth = order.getBerth();
		log.info("/api/callPilot: retrieved ship '" + ship + "' and berth '" + berth + "'.");

		LocalDateTime eta = pilotService.callPilot(ship, berth);

		/* null response implies that no pilots are available to lead the ship out. */
		return new ResponseEntity<>(eta, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/checkBookingPossible", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation("Returns a boolean regarding whether or not a booking is possible on a given day.")
	public ResponseEntity<Object> checkBookingPossible(@RequestBody TimeRequestWrapper request) {
		log.info("/api/checkBookingPossible: entered.");
		log.info("/api/checkBookingPossible: possibility for booking '" + request + "' requested.");

		List<Tide> safeTides = tideService.getSafeTidesOnDay(request.getArrivalDate(), request.getShip().getDraft());
		List<Pilot> pilots = pilotService.findSuitablePilots(request.getShip().getType());

		Pilot pilot = orderService.schedulePilot(pilots, safeTides, null, request.getArrivalDate(), true);

		Boolean possible = pilot != null;
		JSONObject response = new JSONObject();

		try {
			response.put("possible", possible);
		} catch (org.json.JSONException e) {
			log.debug("Unable to parse JSON.");
		}

		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
}
