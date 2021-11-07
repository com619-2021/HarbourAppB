package com.devops.groupb.harbourmaster.repository;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.ShipType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer> {
	@Query(value = "SELECT * FROM pilots WHERE first_name = :firstName AND last_name = :lastName", nativeQuery = true)
	public List<Pilot> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

	@Query(value = "SELECT * FROM pilots JOIN allowed_to ON pilots.id = allowed_to.id WHERE allowed_to.allowed_to LIKE %:shipType%", nativeQuery = true)
	public List<Pilot> findByAllowedTo(@Param("shipType") ShipType shipType);
}
