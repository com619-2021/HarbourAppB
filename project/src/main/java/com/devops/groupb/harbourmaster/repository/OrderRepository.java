package com.devops.groupb.harbourmaster.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devops.groupb.harbourmaster.dto.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	public Order findOneByUuid(@Param("uuid") UUID uuid);

	public Order findOneByShipUuid(@Param("uuid") UUID uuid);
}
