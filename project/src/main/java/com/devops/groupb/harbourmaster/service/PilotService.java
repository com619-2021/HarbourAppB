package com.devops.groupb.harbourmaster.service;

import java.util.UUID;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dao.PilotDAO;
import com.devops.groupb.harbourmaster.dto.ShipType;

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

		if (eligiblePilots.isEmpty()) {
			return null;
		}

		log.info("Selecting random Pilot from " + Arrays.toString(eligiblePilots.toArray()) + ".");
		Random rand = new Random();
		Pilot electedPilot = eligiblePilots.get(rand.nextInt(eligiblePilots.size()));

		log.info("Selected " + electedPilot + " to handle " + shipType.name() + ".");

		return electedPilot;
	}

	public Boolean callPilot(UUID pilotUUID, double lat, double lon) {
		Pilot pilot = pilotDAO.findByUUID(pilotUUID);

		/* use GPS system to calculate the ETA of the pilot to the
		   berth */

		/* implement some way of finding out whether the pilot being called is the
		   same one / someone capable of leading the ship out */

		return pilot != null;
	}
}
