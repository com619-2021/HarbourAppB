package com.devops.groupb.harbourmaster.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.service.GPSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "Controller for the GPS.")
public class GPSController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	GPSService gpsService;

	@GetMapping(value = "/api/gps/{uuid}", produces = "application/json")
	@ApiOperation("Pings for the presence of a given ship by its UUID.")
	public ResponseEntity<Object> findOrder(@PathVariable UUID uuid) {
		GPS gps = gpsService.pingPresence(uuid);

		return new ResponseEntity<>(gps, HttpStatus.OK);
	}

	@GetMapping(value = "/api/gps/sendPilot/{uuid}", produces = "application/json")
	@ApiOperation("Sends a pilot to retrieve the ship.")
	public ResponseEntity<Object> sendPilot(@PathVariable UUID uuid) {
		GPS gps = gpsService.pingPresence(uuid);

		LocalDateTime eta = gpsService.calculateArrival(gps);

		return new ResponseEntity<>(eta, HttpStatus.OK);
	}
}
