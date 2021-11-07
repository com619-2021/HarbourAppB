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
	public void getLegiblePilots() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.FERRY);

		Pilot pilot = new Pilot(allowedTo, "Alex", "Walker", LocalDate.of(1994, Month.MARCH, 22));
		log.info("Saving example pilot " + pilot + " to the database using PilotDAO.");

		pilotDAO.save(pilot);

		List<Pilot> pilotsRetrieved = pilotDAO.findByAllowedTo(ShipType.FERRY);

		log.info("Retrieved legible pilots '" + Arrays.toString(pilotsRetrieved.toArray()) + ".");

		assertFalse(pilotsRetrieved.isEmpty());
	}

	@Test
	public void findByFullName() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.FERRY);

		Pilot pilot = new Pilot(allowedTo, "Arthur", "Lane", LocalDate.of(1978, Month.MAY, 5));

		pilotDAO.save(pilot);

		List<Pilot> pilotsRetrieved = pilotDAO.findByFullName("Arthur", "Lane");

		log.info("Retrieved pilots '" + Arrays.toString(pilotsRetrieved.toArray()) + "' by full name.");

		assertFalse(pilotsRetrieved.isEmpty());
	}
}
