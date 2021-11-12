package com.devops.groupb.harbourmaster.controller;

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
public class PilotController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	PilotService pilotService;

	@RequestMapping(value = "/api/pilot/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createPilot(@RequestBody Pilot pilot) {
		log.info("/api/pilot/create: entered.");
		log.info("/api/pilot/create: creation of " + pilot + " requested.");

		pilotService.createNewPilot(pilot);

		return pilotService.createNewPilot(pilot) ? new ResponseEntity<>(pilot, HttpStatus.OK)
			: new ResponseEntity<>("Unable to create new pilot.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/api/pilot/{uuid}")
	public ResponseEntity<Object> findPilot(@PathVariable UUID uuid) {
		log.info("(GET) /api/pilot/: entered.");
		log.info("(GET) /api/pilot/: query of pilot '" + uuid + "' requested.");

		Pilot pilot = pilotService.findPilot(uuid);

		return pilot != null ? new ResponseEntity<>(pilot, HttpStatus.OK)
			: new ResponseEntity<>(String.format("Unable to find pilot '%s' in the database.", uuid), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "/api/pilot/{uuid}")
	@Transactional
	public ResponseEntity<Object> deletePilot(@PathVariable UUID uuid) {
		log.info("(DELETE) /api/pilot/: entered.");
		log.info("(DELETE) /api/pilot/: deletion of pilot '" + uuid + "' requested.");

		return pilotService.deletePilot(uuid) ? new ResponseEntity<>(String.format("Pilot '%s' successfully deleted.", uuid), HttpStatus.OK)
			: new ResponseEntity<>(String.format("Unable to delete pilot '%s'. They may not exist in the database.", uuid), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
