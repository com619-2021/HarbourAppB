package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.OrderChangeRequest;
import com.devops.groupb.harbourmaster.repository.OrderChangeRequestRepository;

@Repository
public class OrderChangeRequestDAO {
	@Autowired
	private OrderChangeRequestRepository orderChangeRequestRepository;

	public OrderChangeRequest findById(int id) {
		return orderChangeRequestRepository.findById(id).isPresent() ? orderChangeRequestRepository.findById(id).get() : null;
	}

	public Boolean existsById(int id) {
		return orderChangeRequestRepository.existsById(id);
	}

	public OrderChangeRequest save(OrderChangeRequest ocr) {
		return orderChangeRequestRepository.save(ocr);
	}

	public List<OrderChangeRequest> findAll() {
		return orderChangeRequestRepository.findAll();
	}

	public void deleteById(int id) {
		orderChangeRequestRepository.deleteById(id);
	}

	public void delete(OrderChangeRequest order) {
		orderChangeRequestRepository.delete(order);
	}

	public void deleteAll() {
		orderChangeRequestRepository.deleteAll();
	}

	public OrderChangeRequest findByUUID(UUID uuid) {
		return orderChangeRequestRepository.findOneByUuid(uuid);
	}
}
