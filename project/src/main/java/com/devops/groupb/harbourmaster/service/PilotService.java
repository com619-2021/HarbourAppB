package com.devops.groupb.harbourmaster.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.repository.PilotRepository;

@Service
public class PilotService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private PilotRepository pilotRepository;

	public void addPilot(Pilot pilot) {
		pilotRepository.save(pilot);
	}

	public ArrayList<Pilot> getAllPilots() {
		ArrayList<Pilot> pilots = new ArrayList<>();
		pilotRepository.findAll().forEach(pilots::add);

		return pilots;
	}
}
