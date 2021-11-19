package com.devops.groupb.harbourmaster.test.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.service.PilotService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PilotServiceTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	PilotService pilotService;

	@Autowired
	PilotDAO pilotDAO;

	@BeforeAll
	public void daoServiceCheck() {
		log.debug("Checking whether pilotService or pilotDAO are null.");
		assertNotNull(pilotService);
		assertNotNull(pilotDAO);
	}

	@Test
	public void electPilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);

		Pilot pilot = new Pilot(allowedTo, "Russell", "Tillman", LocalDate.of(1994, Month.NOVEMBER, 13));
		pilotDAO.save(pilot);

		assertNotNull(pilotService.findSuitablePilot(ShipType.CARGO));
	}
}
