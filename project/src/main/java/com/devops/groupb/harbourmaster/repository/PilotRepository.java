package com.devops.groupb.harbourmaster.repository;

import com.devops.groupb.harbourmaster.dto.Pilot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer> {

	@Query(value = "SELECT * FROM pilots WHERE firstName = :firstName AND lastName = :lastName", nativeQuery = true)
	public List<Pilot> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

	@Query(value = "SELECT * FROM pilots WHERE allowedTo LIKE '%{$:shipType}%'", nativeQuery = true)
	public List<Pilot> findByAllowedTo(@Param("shipType") String shipType);
}
