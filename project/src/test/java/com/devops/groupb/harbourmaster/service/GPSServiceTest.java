package com.devops.groupb.harbourmaster.test.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.DayOfWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.TimePeriod;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.service.GPSService;
import com.devops.groupb.harbourmaster.service.OrderService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

	// @Test
	// public void pingPresence() {
	//	log.info("Testing pinging for a ship.");

	//	/* requirements for an order */
	//	Ship ship = new Ship(ShipType.CARGO, 1.3);
	//	List<ShipType> allowedTo = new ArrayList();
	//	allowedTo.add(ShipType.CARGO);

	//	Map<DayOfWeek, TimePeriod> workingHours = new HashMap<DayOfWeek, TimePeriod>() {{
	//			put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(9, 00), LocalTime.of(18, 00)));
	//			put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(14, 00), LocalTime.of(23, 00)));
	//		}};

	//	Pilot pilot = new Pilot(allowedTo, "Alex", "Wilbert", LocalDate.of(1985, Month.FEBRUARY, 15), workingHours);
	//	Berth berth = new Berth(53.25, -2.6);

	//	/* placing the order for the gpsService to find. */
	//	Order order = new Order(ship, berth, LocalDate.of(2021, 11, 24));
	//	List<Pilot> pilots = new ArrayList<Pilot>();
	//	pilots.add(pilot);
	//	orderService.placeOrder(order, pilots);

	//	/* pinging is dependent on a random number generator, so a for loop
	//	   is necessary to repeatedly check for a ship to finally appear. null
	//	   implies that the ship has not appeared or it is out of the permitted time range.*/
	//	GPS gps = gpsService.pingPresence(order.getShip().getUUID());
	//	/* failing to find in 100 attempts implies problem with GPS or having
	//	   extremely bad luck. */
	//	int i = 0;
	//	for (; gps == null || i != 100; ++i) {
	//		gps = gpsService.pingPresence(ship.getUUID());
	//	}

	//	log.info("GPS for a legal ship after " + i + " pings: " + gps);
	//	assertNotNull(gps.getLocation());

	//	log.info("Attempting to ping a ship that is too early to be allowed in.");

	//	/* generates a new ship UUID to avoid conflicts with the previous test.
	//	   this ship should not come up on the GPS as they are too early. */
	//	Ship ship2 = new Ship(ShipType.CARGO, 1.3);
	//	Order order2 = new Order(ship2, berth, LocalDate.of(2021, 11, 24));
	//	orderService.placeOrder(order2, pilots);

	//	GPS gps2 = gpsService.pingPresence(ship.getUUID());
	//	i = 0;
	//	for (; gps2 == null || i != 20; ++i) {
	//		gps2 = gpsService.pingPresence(ship.getUUID());
	//	}

	//	log.info("GPS for an early ship after " + i + " pings: " + gps2);
	//	assertNull(gps2);
	// }

	// @Test
	// public void calculateDistance() {
	//	log.info("Testing calculation of distance + ETA.");

	//	/* requirements for an order */
	//	Ship ship = new Ship(ShipType.CARGO, 1.3);
	//	List<ShipType> allowedTo = new ArrayList();
	//	allowedTo.add(ShipType.CARGO);

	//	Map<DayOfWeek, TimePeriod> workingHours = new HashMap<DayOfWeek, TimePeriod>() {{
	//			put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(9, 00), LocalTime.of(18, 00)));
	//			put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(14, 00), LocalTime.of(23, 00)));
	//		}};

	//	Pilot pilot = new Pilot(allowedTo, "John", "Doe", LocalDate.of(1953, Month.JUNE, 3), workingHours);
	//	Berth berth = new Berth(50.889356, -1.395104);

	//	List<Pilot> pilots = new ArrayList<Pilot>();
	//	pilots.add(pilot);

	//	Order order = new Order(ship, berth, LocalDate.of(2021, 11, 24));
	//	orderService.placeOrder(order, pilots);

	//	GPS gps = gpsService.pingPresence(order.getShip().getUUID());
	//	for (int i = 0; gps == null || i != 20; ++i) {
	//		gps = gpsService.pingPresence(order.getShip().getUUID());
	//	}

	//	log.info("Got GPS: " + gps + ".");
	//	LocalDateTime eta = gpsService.calculateArrival(gps);
	//	log.info("ETA: " + eta);

	//	/* should take less than three hours to bring the ship in. */
	//	assertTrue(eta.isBefore(LocalDateTime.now().plusHours(3L)));
	// }
}
