package com.devops.groupb.harbourmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devops.groupb.harbourmaster.dto.WaitingLocation;

@Repository
public interface WaitingLocationRepository extends JpaRepository<WaitingLocation, Integer> {

}
