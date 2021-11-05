package com.devops.groupb.harbourmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import com.devops.groupb.harbourmaster.dto.Tide;

@Repository
public interface TideRepository extends JpaRepository<Tide, Integer> {
	@Query(value = "SELECT * FROM tides WHERE :time BETWEEN start AND end", nativeQuery = true)
	public Tide getTideAt(@Param("time") LocalDateTime time);
}
