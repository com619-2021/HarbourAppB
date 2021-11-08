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

import java.time.DayOfWeek;
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
	public void testCurrentTide() {
		log.info("Attempting to get the current tide.");
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		/* must changes tides in the database to use only
		   time and not date; these tests will fail if tides
		   up to the day of testing are not added!! */
		Tide tide = tideDAO.getTideAt(currentDate.getDayOfWeek(), currentTime);

		log.info("Time: " + currentTime);
		log.info("Got tide " + tide + ".");

		int compareToStart = currentTime.compareTo(tide.getStart());
		int compareToEnd = currentTime.compareTo(tide.getEnd());
		assertTrue(compareToStart > 0 && compareToEnd < 0);
	}
}
