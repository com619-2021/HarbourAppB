package com.devops.groupb.harbourmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import com.devops.groupb.harbourmaster.dto.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
