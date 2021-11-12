package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.WaitingLocation;
import com.devops.groupb.harbourmaster.repository.WaitingLocationRepository;

@Repository
public class WaitingLocationDAO {
	@Autowired
	private WaitingLocationRepository waitingLocationRepository;

	public WaitingLocation findById(int id) {
		return waitingLocationRepository.findById(id).get();
	}

	public Boolean existsById(int id) {
		return waitingLocationRepository.existsById(id);
	}

	public WaitingLocation save(WaitingLocation waitingLocation) {
		return waitingLocationRepository.save(waitingLocation);
	}

	public List<WaitingLocation> findAll() {
		return waitingLocationRepository.findAll();
	}

	public void deleteById(int id) {
		waitingLocationRepository.deleteById(id);
	}

	public void delete(WaitingLocation waitingLocation) {
		waitingLocationRepository.delete(waitingLocation);
	}

	public void deleteAll() {
		waitingLocationRepository.deleteAll();
	}
}
