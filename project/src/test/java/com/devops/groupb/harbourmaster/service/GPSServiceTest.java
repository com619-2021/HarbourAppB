package com.devops.groupb.harbourmaster.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.service.OrderService;
import com.devops.groupb.harbourmaster.service.GPSService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest(classes = HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class GPSServiceTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	GPSService gpsService;

	@Autowired
	OrderService orderService;

	@BeforeAll
	public void serviceCheck() {
		log.info("Checking whether gpsService or orderService is null.");
		assertNotNull(gpsService);
		assertNotNull(orderService);
	}

	@Test
	public void pingPresence() {
		log.info("Testing pinging for a ship.");

		/* requirements for an order */
		Ship ship = new Ship(ShipType.CARGO, 1.3);
		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);
		Pilot pilot = new Pilot(allowedTo, "Alex", "Wilbert", LocalDate.of(1985, Month.FEBRUARY, 15));
		Berth berth = new Berth(53.25, -2.6);

		/* placing the order for the gpsService to find; allocatedTime manually
		   set to ensure that the ship is in the permitted time range (2hrs before) */
		Order order = new Order(ship, berth, LocalDate.now().plusDays(1L));
		order.setAllocatedTime(LocalDateTime.now().minusHours(1L));
		orderService.placeOrder(order, pilot);

		/* pinging is dependent on a random number generator, so a while loop
		   is necessary to repeatedly check for a ship to finally appear. null
		   implies that the ship has not appeared or it is out of the permitted time range.*/
		GPS gps = gpsService.pingPresence(ship.getUUID());
		int i = 0;
		/* failing to find in 100 attempts implies problem with GPS or having
		   extremely bad luck. */
		while (gps == null && i != 100) {
			gps = gpsService.pingPresence(ship.getUUID());
			++i;
		}

		log.info("GPS for a legal ship after " + i + " pings: " + gps);
		assertNotNull(gps.getLocation());

		log.info("Attempting to ping a ship that is too early to be allowed in.");

		/* generates a new ship UUID to avoid conflicts with the previous test.
		   this ship should not come up on the GPS as they are too early. */
		Ship ship2 = new Ship(ShipType.CARGO, 1.3);
		Order order2 = new Order(ship2, berth, LocalDate.now());

		order.setAllocatedTime(LocalDateTime.now().minusHours(2L));
		orderService.placeOrder(order2, pilot);

		GPS gps2 = gpsService.pingPresence(ship.getUUID());
		int i2 = 0;
		while (gps2 == null && i2 != 20) {
			gps2 = gpsService.pingPresence(ship.getUUID());
			++i2;
		}

		log.info("GPS for an early ship after " + i2 + " pings: " + gps2);
		assertNull(gps2);
	}
}
