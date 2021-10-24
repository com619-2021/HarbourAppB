package com.devops.groupb.harbourmaster.controller;

import java.util.ArrayList;

import com.devops.groupb.harbourmaster.model.ShipType;
import com.devops.groupb.harbourmaster.model.Pilot;
import com.devops.groupb.harbourmaster.model.Ship;

import lombok.extern.java.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	/* TODO: consider handling HttpMessageNotReadableException that is thrown when
	   a ShipType is given that is not present in the enum */
	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> book_pilot(@RequestBody Ship ship) {
		log.info("/api/bookPilot: called.");
		log.info("/api/bookPilot: retrieved " + ship + " from request body.");

		/* checks to see whether ship has any empty or invalid values. */
		if (ship.is_valid()) {
			log.info("/api/bookPilot: ship valid.");
			/* in the actual program, a pilot with the correct ShipType
			   will be queried for from the Pilots database. */
			ArrayList<ShipType> allowed_to = new ArrayList<ShipType>();
			allowed_to.add(ShipType.CARGO);
			allowed_to.add(ShipType.PASSENGER);

			Pilot pilot = new Pilot(1, allowed_to); /* sample pilot */

			/* in the actual program, such an if statement won't be used.
			   if the PilotDAO returns no records when querying for a record
			   containing ship.get_type(), then an error is returned. */
			if (pilot.get_allowed_to().contains(ship.get_type())) {
				log.info("/api/bookPilot: pilot available. Assigned pilot #" + pilot.get_id()
						 + " to handle ship type '" + ship.get_type() + "'.");
				return new ResponseEntity<>(
											String.format("Pilot #%d has successfully been booked.", pilot.get_id()), HttpStatus.OK);
			} else {
				/* consider more appropriate HTTP status codes for errors like these. */
				log.info("/api/bookPilot: No pilots are available to handle ship type '" + ship.get_type()
						 + "'. Exiting with " + HttpStatus.INTERNAL_SERVER_ERROR + '.');
				return new ResponseEntity<>(
											String.format("No pilots are available to handle ship type '%s'.", ship.get_type()),
											HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		log.info("/api/bookPilot: invalid ship retrieved. Exiting with " + HttpStatus.INTERNAL_SERVER_ERROR + '.');
		return new ResponseEntity<>("Invalid ship given.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
