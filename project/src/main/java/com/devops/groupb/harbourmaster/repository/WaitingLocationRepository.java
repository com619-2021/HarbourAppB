package com.devops.groupb.harbourmaster.repository;

import com.devops.groupb.harbourmaster.dto.WaitingLocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingLocationRepository extends JpaRepository<WaitingLocation, Integer> {
	public long count();
}
