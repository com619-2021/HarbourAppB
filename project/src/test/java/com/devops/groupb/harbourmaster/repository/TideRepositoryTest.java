package com.devops.groupb.harbourmaster.test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.repository.TideRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest(classes=HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class TideRepositoryTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private TideRepository tideRepository;

	@BeforeAll
	public void repositoryCheck() {
		log.info("Checking whether tideRepository is null.");
		assertNotNull(tideRepository);
	}

	@Test
	public void createAndReadTide() {
		LocalDateTime testStart = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(03, 00, 00));
		LocalDateTime testEnd = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(07, 00, 00));

		Tide tide = new Tide(4.53, testStart, testEnd);
		log.info("Attempting to save " + tide + " to the database.");

		int savedId = tideRepository.save(tide).getId();

		Tide storedTide = tideRepository.findById(savedId).get();

		assertEquals(tide.getHeight(), storedTide.getHeight());
	}

	@Test
	public void updateTide() {
		double originalDepth = 3.93;
		LocalDateTime testStart = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(03, 00, 00));
		LocalDateTime testEnd = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(07, 00, 00));

		Tide originalTide = tideRepository.save(new Tide(originalDepth, testStart, testEnd));

		double newDepth = 4.78;
		originalTide.setHeight(newDepth);
		int savedTideId = tideRepository.save(originalTide).getId();

		assertNotEquals(tideRepository.findById(savedTideId).get().getHeight(), originalDepth);
	}

	@Test
	public void deleteTide() {
		LocalDateTime testStart = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(03, 00, 00));
		LocalDateTime testEnd = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(07, 00, 00));

		Tide tide = new Tide(5.87, testStart, testEnd);

		int savedId = tideRepository.save(tide).getId();

		tideRepository.deleteById(savedId);

		assertFalse(tideRepository.existsById(savedId));
	}
}
