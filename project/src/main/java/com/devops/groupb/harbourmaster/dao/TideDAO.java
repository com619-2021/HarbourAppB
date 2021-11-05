package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.repository.TideRepository;

public class TideDAO {
	@Autowired
	private TideRepository tideRepository;

	public Tide findById(int id) {
		return tideRepository.findById(id).get();
	}

	public Tide save(Tide tide) {
		return tideRepository.save(tide);
	}

	public List<Tide> findAll() {
		return tideRepository.findAll();
	}

	public void deleteById(int id) {
		tideRepository.deleteById(id);
	}

	public void delete(Tide tide) {
		tideRepository.delete(tide);
	}

	public void deleteAll() {
		tideRepository.deleteAll();
	}

	public Tide getTideAt(LocalDateTime time) {
		return tideRepository.getTideAt(time);
	}
}
