package com.devops.groupb.harbourmaster.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.repository.PilotRepository;
import com.devops.groupb.harbourmaster.repository.TideRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Arrays;

@Transactional
public class TideTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private TideRepository tideRepository;

	@Transactional
	@Test
	public void getAllTides() {
		log.info("Testing if any tides are present in the database.");
		List<Tide> tides = tideRepository.findAll();

		log.info("Got tides " + Arrays.toString(tides.toArray()) + ".");

		assertFalse(tides.isEmpty());
	}
}
