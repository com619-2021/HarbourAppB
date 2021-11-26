package com.devops.groupb.harbourmaster.repository;

import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.OrderChangeRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderChangeRequestRepository extends JpaRepository<OrderChangeRequest, Integer> {
	public OrderChangeRequest findOneByUuid(@Param("uuid") UUID uuid);
}
