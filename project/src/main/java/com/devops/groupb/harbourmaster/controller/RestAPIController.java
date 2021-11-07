package com.devops.groupb.harbourmaster.controller;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.PilotCall;
import com.devops.groupb.harbourmaster.dto.PilotBookingRequest;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Order;

import com.devops.groupb.harbourmaster.service.PilotService;
import com.devops.groupb.harbourmaster.service.OrderService;

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
public class RestAPIController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	PilotService pilotService;

	@Autowired
	OrderService orderService;

	// /api/test: a simple REST endpoint for testing.
	@GetMapping(value = "/api/test")
	public ResponseEntity<Object> getTest() {
		log.info("/api/test: called.");
		return new ResponseEntity<>("REST is working.", HttpStatus.OK);
	}

	/* /api/bookPilot: REST endpoint called to book a pilot to handle a given ship.
	   This may be more suitable for the 'OrderController' class that is likely to
	   be used in the future. */
	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> bookPilot(@RequestBody PilotBookingRequest pilotBookingRequest) {
		log.info("/api/bookPilot: entered.");
		log.info("/api/bookPilot: retrieved " + pilotBookingRequest + " from request body.");

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

		if (pilot != null) {
			LocalDateTime allocatedTime = pilotBookingRequest.getDate().atTime(03, 00);
			String allocatedTimeStr = allocatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


			Order order = new Order(pilotBookingRequest.getShip(), pilot, pilotBookingRequest.getBerth(),
									pilotBookingRequest.getDate(), allocatedTime);

			if (orderService.placeOrder(order)) {
				return new ResponseEntity<>(String.format("Your order (ID: %d) for handling of Ship '%s' has been placed.\nOrder Details: %s", order.getId(), pilotBookingRequest.getShip(), order), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Unable to place order.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			String requestDate = pilotBookingRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return new ResponseEntity<>(String.format("No pilots are available to handle ship type '%s' of ship on %s.", pilotBookingRequest.getShip().getType().name(), requestDate), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* /api/callPilot: A possible REST endpoint that is to be called when a
	   a ship is coming into port. We believe, at this point, that the port team
	   is to make use of this endpoint, though we still need some further
	   clarifaction. */
	@RequestMapping(value = "/api/callPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> callPilot(@RequestBody PilotCall pilotCall) {
		log.info("/api/callPilot: entered.");
		log.info("/api/callPilot: pilot #" + pilotCall.getPilotId() + " called.");

		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);
		allowedTo.add(ShipType.FERRY);

		Pilot pilot = new Pilot(pilotCall.getId(), allowedTo, "John", "Smith", LocalDate.of(1970, Month.JANUARY, 1));

		return new ResponseEntity<>(String.format("Pilot #%d has been called and is now en route. They will arrive at .", pilot.getId()), HttpStatus.OK);
	}
}
