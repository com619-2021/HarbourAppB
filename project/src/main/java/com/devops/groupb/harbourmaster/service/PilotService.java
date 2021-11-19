package com.devops.groupb.harbourmaster.service;

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

	public Pilot findSuitablePilot(ShipType shipType) {
		List<Pilot> eligiblePilots = pilotDAO.findByAllowedTo(shipType);

		/* check against the pilot's schedule here..? */

		if (eligiblePilots.isEmpty()) {
			return null;
		}

		log.info("Selecting random Pilot from " + Arrays.toString(eligiblePilots.toArray()) + ".");
		Random rand = new Random();
		Pilot electedPilot = eligiblePilots.get(rand.nextInt(eligiblePilots.size()));

		log.info("Selected " + electedPilot + " to handle " + shipType.name() + ".");

		return electedPilot;
	}

	public Pilot callPilot(ShipType shipType, double lat, double lon) {
		Pilot pilot = findSuitablePilot(shipType);

		return pilot;
	}
}
