package com.devops.groupb.harbourmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devops.groupb.harbourmaster.dto.GPS;

@Repository
public interface GPSRepository extends JpaRepository<GPS, Integer> {
	@Query(value = "SELECT * FROM gps JOIN ships ON ships.id = gps.ship_id WHERE gps.ship_id = :shipId", nativeQuery = true)
	public GPS findByShipId(@Param("shipId") int shipId);
}
