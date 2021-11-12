package com.devops.groupb.harbourmaster.test.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.PilotBookingRequest;

import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dao.OrderDAO;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Month;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	PilotDAO pilotDAO;

	@Autowired
	OrderDAO orderDAO;

	@BeforeAll
	public void checkDAOs() {
		log.debug("Checking whether DAOs are null.");
		assertNotNull(pilotDAO);
		assertNotNull(orderDAO);
	}

	@Test
	public void bookPilot() {
		RestTemplate restTemplate = new RestTemplate();
		String endpoint = "http://localhost:8080/api/bookPilot";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.FERRY);
		Pilot pilot = new Pilot(allowedTo, "Elbert", "Duncan", LocalDate.of(1977, Month.APRIL, 8));
		pilotDAO.save(pilot);

		log.debug("Attempting to call '" + endpoint + "'.");

		Ship ship = new Ship(ShipType.FERRY, 6.52);
		LocalDate date = LocalDate.now();
		Berth berth = new Berth(51.25, -1.53);

		PilotBookingRequest bookingRequest = new PilotBookingRequest(ship, date, berth);

		HttpEntity<PilotBookingRequest> entity = new HttpEntity<PilotBookingRequest>(bookingRequest, headers);
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}
}
