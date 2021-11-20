package com.devops.groupb.harbourmaster.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.CallPilotWrapper;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.TimeRequestWrapper;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.CreateOrderWrapper;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.service.OrderService;
import com.devops.groupb.harbourmaster.service.PilotService;
import com.devops.groupb.harbourmaster.service.TideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST)
	@ApiOperation("Creates a booking using the given request.")
	public ResponseEntity<Object> bookPilot(@RequestBody CreateOrderWrapper request) {
		log.info("/api/bookPilot: entered.");
		log.info("/api/bookPilot: retrieved " + request + " from request body.");

		/* write sanity checks for request values; i.e. "lat" must
		   be provided and "la" = auto denial. */

		List<Pilot> pilots = pilotService.findSuitablePilots(request.getShip().getType());

		Order order = new Order(request.getShip(), request.getBerth(), request.getDate());
		orderService.placeOrder(order, pilots);

		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/callPilot", method = RequestMethod.POST)
	@ApiOperation("Calls a pilot to lead the ship at a given berth out of port.")
	public ResponseEntity<Object> callPilot(@RequestBody CallPilotWrapper pilotCall) {
		log.info("/api/callPilot: entered.");
		log.info("/api/callPilot: pilot for ship type '" + pilotCall.getShipType() + "' called.");

		Pilot pilot = pilotService.callPilot(pilotCall.getShipType(), pilotCall.getBerth().getLat(), pilotCall.getBerth().getLon());

		/* null response implies that no pilots are available to lead the ship out. */
		return new ResponseEntity<>(pilot, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/getSuitableTimes", method = RequestMethod.POST)
	@ApiOperation("Returns a list of suitable arrival times for a given ship on a given date.")
	public ResponseEntity<Object> getSuitableTimes(@RequestBody TimeRequestWrapper request) {
		log.info("/api/getSuitableTimes: entered.");
		log.info("/api/getSuitableTimes: times for date '" + request.getArrivalDate() + "' requested.");

		Ship ship = request.getShip();
		List<Tide> safeTides = tideService.getSafeTidesOnDay(request.getArrivalDate(), ship.getDraft());

		ArrayList<LocalTime> suitableTimes = new ArrayList();

		/* schedule checks. */

		for (Tide t : safeTides)
			suitableTimes.add(t.getStart());

		return new ResponseEntity<>(suitableTimes, HttpStatus.OK);
	}
}
