package com.devops.groupb.harbourmaster.test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.repository.PilotRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.ArrayList;

import java.time.Month;
import java.time.LocalDate;

@SpringBootTest(classes=HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PilotRepositoryTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private PilotRepository pilotRepository;

	@BeforeAll
	public void repositoryCheck() {
		log.info("Checking whether pilotRepository is null.");
		assertNotNull(pilotRepository);
	}

	@Test
	public void createAndReadPilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);
		allowedTo.add(ShipType.CARGO);

		Pilot pilot = new Pilot(allowedTo, "John", "Smith", LocalDate.of(1970, Month.JANUARY, 1));
		log.info("Attempting to save " + pilot + " to the database using PilotRepository.");

		int savedId = pilotRepository.save(pilot).getId();

		Pilot storedPilot = pilotRepository.findById(savedId).get();

		assertEquals(pilot.getFirstName() + pilot.getLastName(), storedPilot.getFirstName() + storedPilot.getLastName());
	}

	@Test
	public void updatePilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);

		Pilot pilot = new Pilot(allowedTo, "Will", "Eastwood", LocalDate.of(1966, Month.AUGUST, 21));
		Pilot savedPilot = pilotRepository.save(pilot);

		savedPilot.setLastName("Wheatley");
		int savedPilotId = pilotRepository.save(savedPilot).getId();

		assertEquals(pilotRepository.findById(savedPilotId).get().getLastName(), "Wheatley");
	}

	@Test
	public void deletePilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);

		Pilot pilot = new Pilot(allowedTo, "Wilbert", "James", LocalDate.of(1963, Month.OCTOBER, 8));

		int savedId = pilotRepository.save(pilot).getId();

		pilotRepository.deleteById(savedId);

		assertFalse(pilotRepository.existsById(savedId));
	}
}
