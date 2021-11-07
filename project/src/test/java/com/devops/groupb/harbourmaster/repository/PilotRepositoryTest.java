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
	public void CRUDTest() {
		log.debug("Testing CRUD capabilities of pilotRepository.");
		/* CREATE */
		log.debug("Testing CREATE of an example pilot.");
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.PASSENGER);
		allowedTo.add(ShipType.CARGO);

		String lastName = "Smith";

		Pilot pilot = new Pilot(allowedTo, "John", lastName, LocalDate.of(1970, Month.JANUARY, 1));
		log.info("Attempting to save " + pilot + " to the database using PilotRepository.");

		int savedId = pilotRepository.save(pilot).getId();

		/* READ */
		log.debug("Testing READ of an example pilot.");
		Pilot storedPilot = pilotRepository.findById(savedId).get();

		assertEquals(pilot.getFirstName() + pilot.getLastName(), storedPilot.getFirstName() + storedPilot.getLastName());

		/* UPDATE */
		log.debug("Testing UPDATE of an example pilot.");
		storedPilot.setLastName("Wheatley");

		Pilot updatedPilot = pilotRepository.save(pilot);

		assertNotEquals(updatedPilot.getLastName(), lastName);

		/* DELETE */
		log.debug("Testing DELETE of an example pilot.");
		pilotRepository.deleteById(savedId);

		assertFalse(pilotRepository.existsById(savedId));
	}
}
