package com.devops.groupb.harbourmaster.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devops.groupb.harbourmaster.dto.GPS;

@Repository
public interface GPSRepository extends JpaRepository<GPS, Integer> {
	public GPS findOneByShipUuid(@Param("shipUuid") UUID uuid);
}
