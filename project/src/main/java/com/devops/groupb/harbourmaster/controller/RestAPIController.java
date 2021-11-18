package com.devops.groupb.harbourmaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.PilotCall;
import com.devops.groupb.harbourmaster.dto.PilotBookingRequest;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.TimeRequest;

import com.devops.groupb.harbourmaster.service.PilotService;
import com.devops.groupb.harbourmaster.service.OrderService;
import com.devops.groupb.harbourmaster.service.TideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	public ResponseEntity<Object> bookPilot(@RequestBody PilotBookingRequest pilotBookingRequest) {
		log.info("/api/bookPilot: entered.");
		log.info("/api/bookPilot: retrieved " + pilotBookingRequest + " from request body.");

		/* write sanity checks for pilotBookingRequest values; i.e. "lat" must
		   be provided and "la" = auto denial. */

		Pilot pilot = pilotService.findSuitablePilot(pilotBookingRequest.getShip().getType());

		/* Add checks to see whether the pilot is available at this time.
		   This could be done by having a 'schedule' table where each
		   scheduled order is saved with the timespan alongside the
		   pilot ID. Such as pilot '1' booked from xxxx-xx-xx 03:00
		   -> 07:00. If the date given doesn't fall into any of these
		   ranges, then the order is succesful. This will be more
		   difficult to implement here as tide times dictate the
		   time of the day that a pilot is booked, and is something
		   that we have to deal with on our end. */

		Order order = new Order(pilotBookingRequest.getShip(), pilotBookingRequest.getBerth(), pilotBookingRequest.getDate());
		orderService.placeOrder(order, pilot);

		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/callPilot", method = RequestMethod.POST)
	@ApiOperation("Calls a pilot to lead the ship at a given berth out of port.")
	public ResponseEntity<Object> callPilot(@RequestBody PilotCall pilotCall) {
		log.info("/api/callPilot: entered.");
		log.info("/api/callPilot: pilot for ship type '" + pilotCall.getShipType() + "' called.");

		Pilot pilot = pilotService.callPilot(pilotCall.getShipType(), pilotCall.getBerth().getLat(),
											 pilotCall.getBerth().getLon());

		return pilot != null
			? new ResponseEntity<>(
								   String.format("Pilot '%s' has been called and is now en route. They will arrive at --:--.",
												 pilot.getUUID()),
								   HttpStatus.OK)
			: new ResponseEntity<>("No pilots are available to handle this ship.", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/api/getSuitableTimes", method = RequestMethod.POST)
	@ApiOperation("Returns a list of suitable arrival times for a given ship on a given date.")
	public ResponseEntity<Object> getSuitableTimes(@RequestBody TimeRequest timeRequest) {
		log.info("/api/getSuitableTimes: entered.");
		log.info("/api/getSuitableTimes: times for date '" + timeRequest.getArrivalDate() + "' requested.");

		Ship ship = timeRequest.getShip();
		List<Tide> safeTides = tideService.getSafeTidesOnDay(timeRequest.getArrivalDate(), ship.getDraft());

		ArrayList<LocalTime> suitableTimes = new ArrayList();

		for (Tide t : safeTides)
			suitableTimes.add(t.getStart());

		return new ResponseEntity<>(suitableTimes, HttpStatus.OK);
	}
}
