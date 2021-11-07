package com.devops.groupb.harbourmaster.test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import com.devops.groupb.harbourmaster.HarbourMaster;
import com.devops.groupb.harbourmaster.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Month;

@SpringBootTest(classes=HarbourMaster.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UserRepositoryTest {
    private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

    @Autowired
    private UserRepository userRepo;

    @BeforeAll
    public void repositoryCheck() {
        log.debug("Checking if Repository is null");
        assertNotNull(userRepo);
    }

}
