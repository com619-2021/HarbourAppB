package com.devops.groupb.harbourmaster.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.repository.PilotRepository;
import com.devops.groupb.harbourmaster.repository.TideRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Arrays;
import java.util.Calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

//@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest(classes=HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class TideTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private TideRepository tideRepository;

	@BeforeAll
	public void repositoryCheck() {
		log.info("Checking whether tideRepository is null.");
		assertNotNull(tideRepository);
	}

	@Test
	public void addTide() {
		LocalDateTime testStart = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(03, 00, 00));
		LocalDateTime testEnd = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(07, 00, 00));

		Tide tide = new Tide(4.53, testStart, testEnd);
		log.info("Attempting to save " + tide + " to the database.");

		tideRepository.save(tide);

		Tide storedTide = tideRepository.findById(1).get();

		assertEquals(tide.getHeight(), storedTide.getHeight());
	}
}
