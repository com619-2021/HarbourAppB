package com.devops.groupb.harbourmaster.test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Order;

import com.devops.groupb.harbourmaster.repository.OrderRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;

@SpringBootTest(classes=HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class OrderRepositoryTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private OrderRepository orderRepository;

	@BeforeAll
	public void repositoryCheck() {
		log.debug("Checking whether orderRepository is null.");
		assertNotNull(orderRepository);
	}

	@Test
	public void createAndReadOrder() {
		log.debug("Testing placement of an example order.");
		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);

		Ship ship = new Ship(ShipType.CARGO, 5.4);
		Pilot pilot = new Pilot(allowedTo, "Cain", "Guy", LocalDate.of(1993, Month.JUNE, 15));
		Berth berth = new Berth(4, 54.5, -2.3);

		Order order = new Order(ship, pilot, berth);

		int savedId = orderRepository.save(order).getId();
		Order savedOrder = orderRepository.findById(savedId).get();

		assertEquals(savedOrder.getShip(), order.getShip());
	}

	@Test
	public void updateOrder() {
		log.debug("Testing UPDATE of an existing order.");

		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);

		Ship ship = new Ship(ShipType.PASSENGER, 4.9);
		Pilot pilot = new Pilot(allowedTo, "Zac", "Francis", LocalDate.of(2000, Month.FEBRUARY, 12));
		Berth originalBerth = new Berth(2, 54.262, -2.4);

		Order order = new Order(ship, pilot, originalBerth);

		Berth newBerth = new Berth(3, 54.376, -2.3);
		order.setBerth(newBerth);

		Order updatedOrder = orderRepository.save(order);

		assertNotEquals(updatedOrder.getBerth().getBerthId(), originalBerth.getBerthId());
	}

	@Test
	public void deleteOrder() {
		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.FERRY);

		Ship ship = new Ship(ShipType.FERRY, 6.3);
		Pilot pilot = new Pilot(allowedTo, "Andy", "Hopper", LocalDate.of(1993, Month.DECEMBER, 19));
		Berth berth = new Berth(99, 51.86, -1.9);

		Order order = new Order(ship, pilot, berth);
		int savedId = orderRepository.save(order).getId();

		orderRepository.deleteById(savedId);

		assertFalse(orderRepository.existsById(savedId));
	}
}
