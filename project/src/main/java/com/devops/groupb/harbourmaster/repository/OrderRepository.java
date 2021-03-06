package com.devops.groupb.harbourmaster.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	public Order findOneByUuid(@Param("uuid") UUID uuid);

	public List<Order> findByShipUuid(@Param("uuid") UUID uuid);
}
