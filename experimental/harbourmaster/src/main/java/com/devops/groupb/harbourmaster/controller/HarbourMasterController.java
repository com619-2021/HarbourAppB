package com.devops.groupb.harbourmaster.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.model.Pilot;
import com.devops.groupb.harbourmaster.model.Ship;
import com.devops.groupb.harbourmaster.model.ShipType;
import com.devops.groupb.harbourmaster.model.Tide;
import com.devops.groupb.harbourmaster.model.TideStage;

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
	/* replaced @Log with this, hopefully stops random 'log is not defined'
	   errors which require 'mvn clean' to fix. */
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	/* /api/test: simple HTTP GET endpoint to test if REST is working. */
	@GetMapping(value = "/api/test")
	public ResponseEntity<Object> get_test() {
		log.info("/api/test: called.");
		return new ResponseEntity<>("REST is working.", HttpStatus.OK);
	}

	/* /api/bookPilot: REST endpoint called to book a pilot to handle a given ship. */
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
				return new ResponseEntity<>(String.format("Pilot #%d has successfully been booked.", pilot.get_id()), HttpStatus.OK);
			} else {
				/* consider more appropriate HTTP status codes for errors like these. */
				log.info("/api/bookPilot: No pilots are available to handle ship type '" + ship.get_type()
						 + "'. Exiting with " + HttpStatus.INTERNAL_SERVER_ERROR + '.');
				return new ResponseEntity<>(String.format("No pilots are available to handle ship type '%s'.", ship.get_type()),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		log.info("/api/bookPilot: invalid ship retrieved. Exiting with " + HttpStatus.INTERNAL_SERVER_ERROR + '.');
		return new ResponseEntity<>("Invalid ship given.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/* /api/requestPilot: REST endpoint called when a ship requires bringing in
	   to port, possibly used by the shipping line(?). consider a more descriptive
	   endpoint name. may also be possible to only require the ship_id as it's
	   likely this will be stored in some sort of pilot schedule .*/
	@RequestMapping(value = "/api/requestPilot", method = RequestMethod.POST)
	public ResponseEntity<Object> request_pilot(@RequestBody Ship ship) {
		log.info("/api/requestPilot: entered.");
		log.info("/api/requestPilot: retrieved " + ship + " from request body.");

		/* as explained in /api/bookPilot, we'll use PilotDAO to find the pilot
		   responsible for this booking. */
		ArrayList<ShipType> allowed_to = new ArrayList<ShipType>();
		allowed_to.add(ShipType.CARGO);
		allowed_to.add(ShipType.PASSENGER);

		Pilot pilot = new Pilot(1, allowed_to);
		log.info("/api/requestPilot: found pilot #" + pilot.get_id() + " to handle the ship.");

		/* here we'll query the SQL database for the current tide at the time
		   using the TideDAO object. how dates are going to be handled depends
		   on the format that we'll acquire them in. */
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Tide tide = new Tide(1, formatter.format(new Date()), 4.53, TideStage.HIGH);

		log.info("/api/requestPilot: current tide height: " + tide.get_height() + ". ship minimum requirement: "
				 + ship.get_minimum_tide_height() + '.');
		log.info("/api/requestPilot: ship safe to bring in?: " + Boolean.toString(tide.get_height() > ship.get_minimum_tide_height()) + '.');

		/* i'm not sure whether there's a 'maximum tide height' at which ships
		   are not allowed to come into port, so this statement might not make
		   sense to someone who knows ships; needs to be checked. this statement
		   also assumes that the ship is valid as, in the real application, it will be. */
		if (tide.get_height() > ship.get_minimum_tide_height()) {
			return new ResponseEntity<>(String.format("Request received. Pilot #%d is en route to handle this ship.", pilot.get_id()), HttpStatus.OK);
		} else {
			/* here we'll make more use of TideDAO to find out when the tide level
			   changes to one that is safe for this ship, letting any callers of
			   this endpoint know how long this ship will be delayed by. */
			return new ResponseEntity<>("Tide levels are unsafe for this ship; unable to bring in - delayed.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Object> handleUnreadableBody() {
		log.info("/api/bookPilot: Exception encountered HttpMessageNotReadableException, This is likely due to the wrong data types being passed to this endpoint");
		return new ResponseEntity<>("Invalid data types give please ensure the ship type is support and the id is a uint32_t", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
