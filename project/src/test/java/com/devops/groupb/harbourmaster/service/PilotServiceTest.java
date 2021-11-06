package com.devops.groupb.harbourmaster.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.service.PilotService;

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
public class PilotServiceTest {

	@Autowired
	PilotService pilotService;

	@Autowired
	PilotDAO pilotDAO;

	@Test
	public void electPilot() {
		ArrayList<ShipType> allowedTo = new ArrayList();
		allowedTo.add(ShipType.CARGO);

		Pilot pilot = new Pilot(allowedTo, "Russell", "Tillman", LocalDate.of(1994, Month.NOVEMBER, 13));
		pilotDAO.save(pilot);

		assertNull(pilotService.findSuitablePilot(ShipType.FERRY));
		assertNotNull(pilotService.findSuitablePilot(ShipType.CARGO));
	}
}
