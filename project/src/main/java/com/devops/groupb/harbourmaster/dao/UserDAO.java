package com.devops.groupb.harbourmaster.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository; 

import com.devops.groupb.harbourmaster.dto.User;
import com.devops.groupb.harbourmaster.repository.UserRepository;


@Repository
public class UserDAO {
    @Autowired
    private UserRepository repo;
    
	public User findById(int id) {
		return repo.findById(id).isPresent() ? repo.findById(id).get() : null;
	}

	public Boolean existsById(int id) {
		return repo.existsById(id);
	}

	public User save(User user) {
		return repo.save(user);
	}

	public List<User> findAll() {
		return repo.findAll();
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

	public void delete(User user) {
		repo.delete(user);
	}

	public void deleteAll() {
		repo.deleteAll();
	}

}
