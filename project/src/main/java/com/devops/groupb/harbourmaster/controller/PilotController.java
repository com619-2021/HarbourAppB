package com.devops.groupb.harbourmaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.Pilot;

import com.devops.groupb.harbourmaster.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "Controller for the CRUD operations of pilots.")
public class PilotController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	PilotService pilotService;

	@RequestMapping(value = "/api/pilot/create", method = RequestMethod.POST)
	@ApiOperation("Creates a pilot using the given pilot object. 400 if creation fails.")
	public ResponseEntity<Object> createPilot(@RequestBody Pilot pilot) {
		log.info("/api/pilot/create: entered.");
		log.info("/api/pilot/create: creation of " + pilot + " requested.");

		pilotService.createNewPilot(pilot);

		return pilotService.createNewPilot(pilot) ? new ResponseEntity<>(pilot, HttpStatus.CREATED)
			: new ResponseEntity<>("Unable to create new pilot.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/api/pilot/{uuid}")
	@ApiOperation("Returns the pilot of the given UUID. 404 if pilot is not found.")
	public ResponseEntity<Object> findPilot(@PathVariable UUID uuid) {
		log.info("(GET) /api/pilot: entered.");
		log.info("(GET) /api/pilot: query of pilot '" + uuid + "' requested.");

		Pilot pilot = pilotService.findPilot(uuid);

		return pilot != null ? new ResponseEntity<>(pilot, HttpStatus.OK)
			: new ResponseEntity<>(String.format("Unable to find pilot '%s' in the database.", uuid), HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/api/pilot/{uuid}")
	@Transactional
	@ApiOperation("Deletes the pilot of the given UUID. 404 if the pilot is not found.")
	public ResponseEntity<Object> deletePilot(@PathVariable UUID uuid) {
		log.info("(DELETE) /api/pilot: entered.");
		log.info("(DELETE) /api/pilot: deletion of pilot '" + uuid + "' requested.");

		return pilotService.deletePilot(uuid) ? new ResponseEntity<>(String.format("Pilot '%s' successfully deleted.", uuid), HttpStatus.NO_CONTENT)
			: new ResponseEntity<>(String.format("Unable to delete pilot '%s'. They may not exist in the database.", uuid), HttpStatus.NOT_FOUND);
	}
}
