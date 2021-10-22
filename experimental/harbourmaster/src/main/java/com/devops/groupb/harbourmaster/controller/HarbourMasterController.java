package com.devops.groupb.harbourmaster.controller;

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
	/* Simple GET request for testing the REST API. */
	@GetMapping(value = "/api/test")
	public ResponseEntity<Object> getTest() {
		log.info("/api/test: entered.");
		return new ResponseEntity<>("REST is working.", HttpStatus.OK);
	}

	/* /api/bookPilot: REST endpoint called to book a pilot to handle a given ship.
	   Example POST request for /api/bookPilot:
	   {
	   "id": "60",
	   "length": "250",
	   "width": "60",
	   "weight": "5000"
	   }
	*/
	@RequestMapping(value = "/api/bookPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> book_pilot(@RequestBody Ship ship) {
		log.info("/api/bookPilot: entered.");
		log.info("/api/bookPilot: retrieved " + ship + " from request body.");

		// add valid checks as to whether all fields of the 'Ship' object has been given; not just id.
		if (ship.is_valid()) {
			// real application would have checks regarding training_level here.
			Pilot pilot = new Pilot(1, 5); // id: 1, training_level: 5
			return new ResponseEntity<>(String.format("Pilot #%d has successfully been booked.", pilot.get_id()), HttpStatus.OK);
		}

		return new ResponseEntity<>("Invalid ship given.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
