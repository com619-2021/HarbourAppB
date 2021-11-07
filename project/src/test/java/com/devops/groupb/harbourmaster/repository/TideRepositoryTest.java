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
	public void CRUDTest() {
		log.debug("Testing CRUD capabilities of tideRepository.");
		/* CREATE */
		log.debug("Testing CREATE of an example tide.");
		LocalDateTime testStart = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(03, 00, 00));
		LocalDateTime testEnd = LocalDate.of(2021, Month.NOVEMBER, 4).atTime(LocalTime.of(07, 00, 00));

		double tideHeight = 4.53;

		Tide tide = new Tide(tideHeight, testStart, testEnd);

		int savedId = tideRepository.save(tide).getId();

		/* READ */
		log.debug("Testing READ of an example tide.");
		Tide storedTide = tideRepository.findById(savedId).get();

		assertEquals(tide.getHeight(), storedTide.getHeight());

		/* UPDATE */
		log.debug("Testing UPDATE of an example tide.");
		tide.setHeight(5.26);

		Tide updatedTide = tideRepository.save(tide);

		assertNotEquals(updatedTide.getHeight(), tideHeight);

		/* DELETE */
		log.debug("Testing DELETE of an example tide.");

		tideRepository.deleteById(savedId);

		assertFalse(tideRepository.existsById(savedId));
	}
}
