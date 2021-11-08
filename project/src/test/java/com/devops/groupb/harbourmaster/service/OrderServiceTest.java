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
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.service.OrderService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

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
		Pilot pilot = new Pilot(allowedTo, "Brim", "Rock", LocalDate.of(1969, Month.DECEMBER, 2));
		Berth berth = new Berth(9, 53.25, -2.6);

		Order order = new Order(ship, pilot, berth, LocalDate.now().plusDays(1L), LocalDateTime.now().plusDays(1L));

		orderService.placeOrder(order);
		orderService.cancelOrder(order.getId());

		assertEquals(order.getStatus(), OrderStatus.CANCELLED);
	}
}
