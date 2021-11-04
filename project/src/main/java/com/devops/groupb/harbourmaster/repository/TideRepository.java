package com.devops.groupb.harbourmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.Tide;

@Repository
public interface TideRepository extends JpaRepository<Tide, String> {  

}  
