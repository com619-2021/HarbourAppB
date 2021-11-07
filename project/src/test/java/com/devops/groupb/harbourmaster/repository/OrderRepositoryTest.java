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
import java.time.LocalDateTime;
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
	public void CRUDTest() {
		log.debug("Testing CRUD capabilities of orderRepository.");
		/* CREATE */
		log.debug("Testing CREATE of an example order.");
		List<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);

		Ship ship = new Ship(ShipType.CARGO, 5.4);
		Pilot pilot = new Pilot(allowedTo, "Cain", "Guy", LocalDate.of(1993, Month.JUNE, 15));
		Berth berth = new Berth(4, 54.5, -2.3);
		Order order = new Order(ship, pilot, berth, LocalDate.now().plusDays(1L), LocalDateTime.now().plusDays(1L));

		int savedId = orderRepository.save(order).getId();

		/* READ */
		log.debug("Testing READ of an example order.");
		Order savedOrder = orderRepository.findById(savedId).get();

		assertEquals(savedOrder.getShip(), order.getShip());

		/* UPDATE */
		log.debug("Testing UPDATE of an example order.");

		Berth newBerth = new Berth(3, 54.376, -2.3);
		order.setBerth(newBerth);

		Order updatedOrder = orderRepository.save(order);

		assertNotEquals(updatedOrder.getBerth().getBerthId(), berth.getBerthId());

		/* DELETE */
		log.debug("Testing DELETE of an example order.");
		orderRepository.deleteById(savedId);

		assertFalse(orderRepository.existsById(savedId));
	}
}
