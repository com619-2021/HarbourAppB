package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.repository.GPSRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GPSDAO {
	@Autowired
	private GPSRepository GPSRepository;

	public GPS findById(int id) {
		return GPSRepository.findById(id).isPresent() ? GPSRepository.findById(id).get() : null;
	}

	public Boolean existsById(int id) {
		return GPSRepository.existsById(id);
	}

	public GPS save(GPS GPS) {
		return GPSRepository.save(GPS);
	}

	public List<GPS> findAll() {
		return GPSRepository.findAll();
	}

	public void deleteById(int id) {
		GPSRepository.deleteById(id);
	}

	public void delete(GPS GPS) {
		GPSRepository.delete(GPS);
	}

	public void deleteAll() {
		GPSRepository.deleteAll();
	}

	public GPS findByShipUUID(UUID uuid) {
		return GPSRepository.findOneByShipUuid(uuid);
	}
}
