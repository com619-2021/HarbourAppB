package com.devops.groupb.harbourmaster.controller;

import java.util.ArrayList;

import com.devops.groupb.harbourmaster.model.ShipType;
import com.devops.groupb.harbourmaster.model.Pilot;
import com.devops.groupb.harbourmaster.model.Ship;

import lombok.extern.java.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class HarbourMasterController {
	/* /api/test: simple HTTP GET endpoint to test if REST is working. */
	@GetMapping(value = "/api/test")
	public ResponseEntity<Object> getTest() {
		log.info("/api/test: called.");
		return new ResponseEntity<>("REST is working.", HttpStatus.OK);
	}

	/* /api/bookPilot: REST endpoint called to book a pilot to handle a given ship. */
	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> book_pilot(@RequestBody Ship ship) {
		log.info("/api/bookPilot: called.");
		log.info("/api/bookPilot: retrieved " + ship + " from request body.");

		/* checks to see whether ship has any empty values */
		if (ship.is_valid()) {
			ArrayList<ShipType> allowed_to = new ArrayList<ShipType>();
			allowed_to.add(ShipType.CARGO);
			allowed_to.add(ShipType.PASSENGER);

			Pilot pilot = new Pilot(1, allowed_to); /* id: 1, allowed_to: PASSENGER, CARGO */
			log.info("/api/bookPilot: ship valid. Assigned pilot #" + pilot.get_id() + " to handle ship type '"
					 + ship.get_type() + "'.");
			return new ResponseEntity<>(String.format("Pilot #%d has successfully been booked.", pilot.get_id()), HttpStatus.OK);
		}

		return new ResponseEntity<>("Invalid ship given.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
