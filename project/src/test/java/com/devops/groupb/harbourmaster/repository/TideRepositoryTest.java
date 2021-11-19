package com.devops.groupb.harbourmaster.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.dto.Tide;
import com.devops.groupb.harbourmaster.repository.TideRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
		/* CREATE not needed as tides will be prepopulated. */
		/* READ */
		log.debug("Testing READ of an example tide.");
		Tide storedTide = tideRepository.findById(1).get();

		assertNotNull(storedTide);

		/* UPDATE */
		log.debug("Testing UPDATE of an example tide.");
		double originalHeight = storedTide.getHeight();
		storedTide.setHeight(5.26);

		Tide updatedTide = tideRepository.save(storedTide);

		assertNotEquals(updatedTide.getHeight(), originalHeight);

		/* DELETE */
		log.debug("Testing DELETE of an example tide.");
		int updatedTidePk = updatedTide.getPk();

		tideRepository.deleteById(updatedTidePk);

		assertFalse(tideRepository.existsById(updatedTidePk));
	}
}
