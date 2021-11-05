package com.devops.groupb.harbourmaster.test.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dao.PilotDAO;

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
public class PilotDAOTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private PilotDAO pilotDAO;

	@BeforeAll
	public void daoCheck() {
		log.info("Checking whether pilotDAO is null.");
		assertNotNull(pilotDAO);
	}

	@Test
	public void createAndReadPilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);
		allowedTo.add(ShipType.CARGO);

		Pilot pilot = new Pilot(allowedTo, "John", "Smith", LocalDate.of(1970, Month.JANUARY, 1));
		log.info("Attempting to save " + pilot + " to the database using PilotDAO.");

		int savedId = pilotDAO.save(pilot).getId();

		Pilot storedPilot = pilotDAO.findById(savedId);

		assertEquals(pilot.getFirstName() + pilot.getLastName(), storedPilot.getFirstName() + storedPilot.getLastName());
	}

	@Test
	public void updatePilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);

		Pilot pilot = new Pilot(allowedTo, "Gray", "Woodrow", LocalDate.of(1966, Month.AUGUST, 21));
		Pilot savedPilot = pilotDAO.save(pilot);

		savedPilot.setLastName("White");
		int savedPilotId = pilotDAO.save(savedPilot).getId();

		assertEquals(pilotDAO.findById(savedPilotId).getLastName(), "White");
	}

	@Test
	public void deletePilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);

		Pilot pilot = new Pilot(allowedTo, "Gray", "Woodrow", LocalDate.of(1966, Month.AUGUST, 21));
		int savedId = pilotDAO.save(pilot).getId();

		pilotDAO.deleteById(savedId);

		assertFalse(pilotDAO.existsById(savedId));
	}

	@Test
	public void getLegiblePilots() {
		ShipType ship = ShipType.FERRY;

		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.FERRY);

		Pilot pilot = new Pilot(allowedTo, "Alex", "Walker", LocalDate.of(1994, Month.MARCH, 22));
		log.info("Saving example pilot " + pilot + " to the database using PilotDAO.");

		log.info(pilotDAO.save(pilot));

		List<Pilot> pilotsRetrieved = pilotDAO.findByAllowedTo(ship);

		log.info("Retrieved legible pilots '" + Arrays.toString(pilotsRetrieved.toArray()) + "' to handle ShipType " + ship + ".");

		assertFalse(pilotsRetrieved.isEmpty());
	}
}
