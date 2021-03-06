package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.repository.PilotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PilotDAO {
	@Autowired
	private PilotRepository pilotRepository;

	public Pilot findById(int id) {
		return pilotRepository.findById(id).isPresent() ? pilotRepository.findById(id).get() : null;
	}

	public Boolean existsById(int id) {
		return pilotRepository.existsById(id);
	}

	public Pilot save(Pilot pilot) {
		return pilotRepository.save(pilot);
	}

	public List<Pilot> findAll() {
		return pilotRepository.findAll();
	}

	public void deleteById(int id) {
		pilotRepository.deleteById(id);
	}

	public void delete(Pilot pilot) {
		pilotRepository.delete(pilot);
	}

	public void deleteAll() {
		pilotRepository.deleteAll();
	}

	public List<Pilot> findByFullName(String firstName, String lastName) {
		return pilotRepository.findByFullName(firstName, lastName);
	}

	public List<Pilot> findByAllowedTo(ShipType shipType) {
		return pilotRepository.findByAllowedTo(shipType);
	}

	public Pilot findByUUID(UUID uuid) {
		return pilotRepository.findOneByUuid(uuid);
	}

	public Boolean deleteByUUID(UUID uuid) {
		if (pilotRepository.findOneByUuid(uuid) != null) {
			pilotRepository.deleteByUuid(uuid);
			return true;
		}
		return false;
	}

	public List<Pilot> getAllPilots() {
		return pilotRepository.findAll();
	}
}
