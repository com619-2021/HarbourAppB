package com.devops.groupb.harbourmaster.test.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.TimePeriod;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
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
public class OrderServiceTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	OrderService orderService;

	@BeforeAll
	public void serviceCheck() {
		log.debug("Checking whether orderService is null.");
		assertNotNull(orderService);
	}

	@Test
	public void cancelOrder() {
		log.debug("Testing cancelling an order.");
		Ship ship = new Ship(ShipType.FERRY, 6.8);
		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);

		ArrayList<DayOfWeek> workingDays = new ArrayList();
		workingDays.add(DayOfWeek.WEDNESDAY);
		workingDays.add(DayOfWeek.FRIDAY);

		TimePeriod workingHours = new TimePeriod(LocalTime.of(8, 00), LocalTime.of(17, 00));

		Pilot pilot = new Pilot(allowedTo, "Brim", "Rock", LocalDate.of(1969, Month.DECEMBER, 2), workingDays, workingHours);
		Berth berth = new Berth(53.25, -2.6);

		Order order = new Order(ship, berth, LocalDate.now().plusDays(1L));

		orderService.placeOrder(order, pilot);
		orderService.cancelOrder(order.getUUID(), "Test");

		assertEquals(order.getStatus(), OrderStatus.CANCELLED);
	}
}
