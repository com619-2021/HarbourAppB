package com.devops.groupb.harbourmaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.service.GPSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@Api(description = "Controller for the GPS.")
public class GPSController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	GPSService gpsService;

	@GetMapping(value = "/api/gps/{uuid}")
	@ApiOperation("Pings for the presence of a given ship by its UUID.")
	public ResponseEntity<Object> findOrder(@PathVariable UUID uuid) {
		GPS gps = gpsService.pingPresence(uuid);

		return new ResponseEntity<>(gps, HttpStatus.OK);
	}
}
