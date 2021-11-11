package com.devops.groupb.harbourmaster.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devops.groupb.harbourmaster.dto.GPS;

@Repository
public interface GPSRepository extends JpaRepository<GPS, Integer> {
	@Query(value = "SELECT * FROM gps JOIN ships ON ships.uuid = gps.ship_uuid WHERE gps.ship_uuid = :uuid", nativeQuery = true)
	public GPS findByShipUUID(@Param("uuid") UUID uuid);
}
