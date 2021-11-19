package com.devops.groupb.harbourmaster.repository;

import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.GPS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GPSRepository extends JpaRepository<GPS, Integer> {
	public GPS findOneByShipUuid(@Param("shipUuid") UUID uuid);
}
