package com.devops.groupb.harbourmaster.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;

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
public class HarbourMasterController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	// /api/test: a simple REST endpoint for testing.
	@GetMapping(value = "/api/test")
	public ResponseEntity<Object> getTest() {
		log.info("/api/test: called.");
		return new ResponseEntity<>("REST is working.", HttpStatus.OK);
	}

	// /api/bookPilot: REST endpoint called to book a pilot to handle a given ship.
	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> bookPilot(@RequestBody Ship ship) {
		log.info("/api/bookPilot: called.");
		log.info("/api/bookPilot: retrieved " + ship + " from request body.");

		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);
		allowedTo.add(ShipType.FERRY);

		Pilot pilot = new Pilot(1L, allowedTo, "John", "Smith", "01-01-1970");

		return new ResponseEntity<>(String.format("Pilot #%d has successfully been booked.", pilot.getId()), HttpStatus.OK);
	}

	// /api/requestPilot: REST endpoint that is called by the shipping team when a ship requires
	// piloting in to port. Pilot must be booked beforehand.
	@RequestMapping(value = "/api/requestPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> requestPilot(@RequestBody String pilotId) {
		log.info("/api/requestPilot: entered.");
		log.info("/api/requestPilot: pilot #" + pilotId + " requested.");

		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);
		allowedTo.add(ShipType.FERRY);

		Pilot pilot = new Pilot(Long.parseLong(pilotId), allowedTo, "John", "Smith", "01-01-1970");

		return new ResponseEntity<>(String.format("Pilot #%d has been called and is now en route.", pilot.getId()), HttpStatus.OK);
	}

	// Handles HttpMessageNotReadableException that occurs when a wrong ShipType is given.
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Object> handleUnreadableBody() {
		log.info("/api/bookPilot: Exception encountered HttpMessageNotReadableException. This is likely due to the wrong data types being passed to this endpoint.");
		return new ResponseEntity<>("Invalid data types given. Please ensure the ShipType is supported and the ID is a uint32_t", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
