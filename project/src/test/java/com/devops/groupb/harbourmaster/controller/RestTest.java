package com.devops.groupb.harbourmaster.test.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dao.OrderDAO;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.TimePeriod;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.CreateOrderWrapper;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

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
		headers.put("Content-Type",  Arrays.asList("application/json"));

		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.FERRY);

		Map<DayOfWeek, TimePeriod> workingHours = new HashMap<DayOfWeek, TimePeriod>() {{
				put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(9, 00), LocalTime.of(18, 00)));
				put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(14, 00), LocalTime.of(23, 00)));
			}};

		Pilot pilot = new Pilot(allowedTo, "Elbert", "Duncan", LocalDate.of(1977, Month.APRIL, 8), workingHours);
		pilotDAO.save(pilot);

		log.debug("Attempting to call '" + endpoint + "'.");

		Ship ship = new Ship(ShipType.FERRY, 6.52);
		LocalDate date = LocalDate.now();
		Berth berth = new Berth(51.25, -1.53);

		CreateOrderWrapper bookingRequest = new CreateOrderWrapper(ship, date, berth);

		/* demonstration of how a request json would be made. other parties won't have the same
		   'ship' or 'berth' objects as us, so they can't directly pass them into a 'HttpEntity'
		   object. */
		JSONObject request = new JSONObject();
		JSONObject berthJson = new JSONObject();
		JSONObject shipJson = new JSONObject();

		try {
			berthJson.put("latitude", bookingRequest.getBerth().getLatitude());
			berthJson.put("longitude", bookingRequest.getBerth().getLongitude());
			berthJson.put("uuid", bookingRequest.getBerth().getUUID());

			shipJson.put("draft", bookingRequest.getShip().getDraft());
			shipJson.put("type", bookingRequest.getShip().getType().name());
			shipJson.put("uuid", bookingRequest.getShip().getUUID());

			request.put("berth", berthJson);
			request.put("dayOfArrival", date);
			request.put("ship", shipJson);

		} catch (org.json.JSONException e) {
			log.debug("Unable to parse JSON.");
		}

		log.debug("Created JSON request '" + request + "' from " + bookingRequest);

		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}
}
