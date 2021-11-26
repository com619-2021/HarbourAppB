package com.devops.groupb.harbourmaster.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.dao.TideDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TideService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private TideDAO tideDAO;

	public List<Tide> getSafeTidesOnDay(LocalDate date, double draft) {
		return tideDAO.getSafeTidesOnDay(date.getDayOfWeek(), draft);
	}

	public Tide getTideAt(LocalDateTime time) {
		return tideDAO.getTideAt(time.getDayOfWeek(), time.toLocalTime());
	}

	public Boolean getTideSafety(LocalDate date, LocalTime time, int draft) {
		DayOfWeek day = date.getDayOfWeek();
		return tideDAO.getTideAt(day, time).getHeight() > draft;
	}

	public LocalTime getNextSafeTide(int draft) {
		return tideDAO.getNextSafeTide(draft);
	}
}
