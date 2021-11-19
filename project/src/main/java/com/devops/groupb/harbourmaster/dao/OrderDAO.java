package com.devops.groupb.harbourmaster.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.repository.OrderRepository;

@Repository
public class OrderDAO {
	@Autowired
	private OrderRepository orderRepository;

	public Order findById(int id) {
		return orderRepository.findById(id).isPresent() ? orderRepository.findById(id).get() : null;
	}

	public Boolean existsById(int id) {
		return orderRepository.existsById(id);
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public void deleteById(int id) {
		orderRepository.deleteById(id);
	}

	public void delete(Order order) {
		orderRepository.delete(order);
	}

	public void deleteAll() {
		orderRepository.deleteAll();
	}

	public Order findByUUID(UUID uuid) {
		return orderRepository.findOneByUuid(uuid);
	}

	public Order findByShipUUID(UUID uuid) {
		return orderRepository.findOneByShipUuid(uuid);
	}
}
