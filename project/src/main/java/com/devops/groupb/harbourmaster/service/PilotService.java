package com.devops.groupb.harbourmaster.service;

import java.time.LocalDate;
import java.time.DayOfWeek;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.ShipType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PilotService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private PilotDAO pilotDAO;

	public Boolean createNewPilot(Pilot pilot) {
		pilotDAO.save(pilot);
		return true;
	}

	public Pilot findPilot(UUID uuid) {
		Pilot pilot = pilotDAO.findByUUID(uuid);
		return pilot;
	}

	public Boolean deletePilot(UUID uuid) {
		return pilotDAO.deleteByUUID(uuid);
	}

	public List<Pilot> findSuitablePilots(ShipType shipType) {
		List<Pilot> eligiblePilots = pilotDAO.findByAllowedTo(shipType);

		if (eligiblePilots.isEmpty()) {
			return null;
		}

		return eligiblePilots;
	}

	public Pilot callPilot(ShipType shipType, double lat, double lon) {
		Pilot pilot = new Pilot();
		//		Pilot pilot = findSuitablePilots(shipType);

		/* time to get to the ship should be here. */

		return pilot;
	}
}
