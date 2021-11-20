package com.devops.groupb.harbourmaster.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.TimePeriod;
import com.devops.groupb.harbourmaster.repository.OrderRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

		ArrayList<DayOfWeek> workingDays = new ArrayList();
		workingDays.add(DayOfWeek.WEDNESDAY);
		workingDays.add(DayOfWeek.FRIDAY);

		TimePeriod workingHours = new TimePeriod(LocalTime.of(8, 00), LocalTime.of(17, 00));

		Ship ship = new Ship(ShipType.CARGO, 5.4);
		Pilot pilot = new Pilot(allowedTo, "Cain", "Guy", LocalDate.of(1993, Month.JUNE, 15), workingDays, workingHours);
		Berth berth = new Berth(54.5, -2.3);
		Order order = new Order(ship, berth, LocalDate.now().plusDays(1L));

		/* OrderService has a 'placeOrder' function that takes in an order and a pilot
		   to handle sanity checks. Here, it must be hard-coded. */
		order.setPilot(pilot);

		int savedPk = orderRepository.save(order).getPk();

		/* READ */
		log.debug("Testing READ of an example order.");
		Order savedOrder = orderRepository.findById(savedPk).get();

		assertEquals(savedOrder.getShip(), order.getShip());

		/* UPDATE */
		log.debug("Testing UPDATE of an example order.");

		Berth newBerth = new Berth(54.376, -2.3);
		order.setBerth(newBerth);

		Order updatedOrder = orderRepository.save(order);

		assertNotEquals(updatedOrder.getBerth().getUUID(), berth.getUUID());

		/* DELETE */
		log.debug("Testing DELETE of an example order.");
		orderRepository.deleteById(savedPk);

		assertFalse(orderRepository.existsById(savedPk));
	}
}
