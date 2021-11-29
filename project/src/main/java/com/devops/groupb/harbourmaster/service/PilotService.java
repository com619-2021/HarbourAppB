package com.devops.groupb.harbourmaster.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dao.TideDAO;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.ShipType;

import com.devops.groupb.harbourmaster.service.OrderService;
import com.devops.groupb.harbourmaster.service.GPSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PilotService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private PilotDAO pilotDAO;

	@Autowired
	private TideDAO tideDAO;

	@Autowired
	private OrderService orderService;

	@Autowired
	private GPSService gpsService;

	public Boolean createNewPilot(Pilot pilot) {
		pilotDAO.save(pilot);
		return true;
	}

	public Pilot findPilot(UUID uuid) {
		Pilot pilot = pilotDAO.findByUUID(uuid);
		return pilot;
	}

	public List<Pilot> getAllPilots() {
		return pilotDAO.findAll();
	}

	public Boolean deletePilot(UUID uuid) {
		return pilotDAO.deleteByUUID(uuid);
	}

	public void deleteAllPilots() {
		pilotDAO.deleteAll();
	}

	public List<Pilot> findSuitablePilots(ShipType shipType) {
		List<Pilot> eligiblePilots = pilotDAO.findByAllowedTo(shipType);

		if (eligiblePilots.isEmpty()) {
			return null;
		}

		Collections.shuffle(eligiblePilots);

		return eligiblePilots;
	}

	public LocalDateTime callPilot(Ship ship, Berth berth) {
		List<Pilot> pilots = findSuitablePilots(ship.getType());

		if (pilots == null) {
			return null;
		}

		List<Tide> safeTides = tideDAO.getSafeTidesOnDay(LocalDate.now().getDayOfWeek(), ship.getDraft());

		Pilot pilot = orderService.schedulePilot(pilots, safeTides, null, LocalDate.now(), true);

		if (pilot == null) {
			return null;
		}

		LocalDateTime exit = gpsService.calculateExit(berth.getLatitude(), berth.getLongitude());

		log.info("Estimated exit time: " + exit);

		return exit;
	}
}
