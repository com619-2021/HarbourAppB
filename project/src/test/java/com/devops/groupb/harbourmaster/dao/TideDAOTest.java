package com.devops.groupb.harbourmaster.test.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.dao.TideDAO;
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

@SpringBootTest(classes = HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class TideDAOTest {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private TideDAO tideDAO;

	@BeforeAll
	public void daoCheck() {
		log.info("Checking whether tideDAO is null.");
		assertNotNull(tideDAO);
	}

	@Test
	public void saveTide() {
		LocalDateTime testStart = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(03, 00, 00));
		LocalDateTime testEnd = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(07, 00, 00));

		Tide tide = new Tide(4.53, testStart, testEnd);
		log.info("Attempting to save " + tide + " to the database.");

		int savedId = tideDAO.save(tide).getId();

		Tide storedTide = tideDAO.findById(savedId);

		assertEquals(tide.getHeight(), storedTide.getHeight());
	}

	@Test
	public void testCurrentTide() {
		LocalDateTime tideStart = LocalDateTime.now();
		LocalDateTime tideEnd = LocalDateTime.now().plusHours(5L);

		Tide tideOfCurrentTime = new Tide(6.36, tideStart, tideEnd);
		log.info("Created example tide: " + tideOfCurrentTime);

		tideDAO.save(tideOfCurrentTime);

		log.info("Attempting to get the current tide.");
		LocalDateTime currentTime = LocalDateTime.now();
		Tide tide = tideDAO.getTideAt(currentTime);

		log.info("Got tide " + tide + ".");

		int compareToStart = currentTime.compareTo(tide.getStart());
		int compareToEnd = currentTime.compareTo(tide.getEnd());
		assertTrue(compareToStart > 0 && compareToEnd < 0);
	}
}