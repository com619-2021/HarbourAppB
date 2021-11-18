package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.time.LocalTime;
import java.time.DayOfWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.repository.TideRepository;

@Repository
public class TideDAO {
	@Autowired
	private TideRepository tideRepository;

	public Tide findById(int id) {
		return tideRepository.findById(id).get();
	}

	public Boolean existsById(int id) {
		return tideRepository.existsById(id);
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

	public Tide getTideAt(DayOfWeek day, LocalTime time) {
		int dayInt = day.getValue() - 1;
		return tideRepository.getTideAt(dayInt, time);
	}

	public LocalTime getNextSafeTide(double draft) {
		return tideRepository.getNextSafeTide(draft);
	}

	public List<Tide> getSafeTidesOnDay(DayOfWeek day, double draft) {
		int dayInt = day.getValue() - 1;
		return tideRepository.getSafeTidesOnDay(dayInt, draft);
	}
}
