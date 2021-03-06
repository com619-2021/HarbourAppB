package com.devops.groupb.harbourmaster.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.Tide;

@Repository
public interface TideRepository extends JpaRepository<Tide, Integer> {
	@Query(value = "SELECT * FROM tides WHERE day = :day AND :time BETWEEN start AND end", nativeQuery = true)
	public Tide getTideAt(@Param("day") int day, @Param("time") LocalTime time);

	@Query(value = "SELECT * FROM tides WHERE height > :draft AND day = :day", nativeQuery = true)
	public List<Tide> getSafeTidesOnDay(@Param("day") int day, @Param("draft") double draft);

	// revise.
	@Query(value = "SELECT * FROM tides WHERE height > :draft", nativeQuery = true)
	public LocalTime getNextSafeTide(@Param("draft") double draft);
}
