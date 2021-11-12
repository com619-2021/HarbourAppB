package com.devops.groupb.harbourmaster.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;

import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.service.TideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@Api(description = "Controller for handling common operations on tides.")
public class TideController {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	TideService tideService;

	@GetMapping(value = "/api/tide/{datetime}")
	@ApiOperation("Returns the tide at the given LocalDateTime (yyyy-MM-dd HH:mm:ss). 404 if the tide is not found.")
	public ResponseEntity<Object> findTideAt(@PathVariable String datetime) {
		log.info("(GET) /api/tide: entered.");
		log.info("(GET) /api/tide: tide at time '" + datetime + "' requested.");

		/* attempts to parse the given datetime variable. */
		Tide tide = null;
		LocalDateTime parsedTime = null;

		try {
			/* considering parsing decimals of a second too. */
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			parsedTime = LocalDateTime.parse(datetime, formatter);
			tide = tideService.getTideAt(parsedTime);
		} catch (DateTimeParseException e) {
			return new ResponseEntity<>(String.format("Unable to parse '%s'. Make sure you are using the format 'yyyy-MM-dd HH:mm:ss' for your request.", datetime), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return tide != null
			? new ResponseEntity<>(tide, HttpStatus.OK)
			: new ResponseEntity<>(String.format("Tide not found at %s. This is likely due to a configuration error with the tide system.", parsedTime), HttpStatus.NOT_FOUND);
	}
}
