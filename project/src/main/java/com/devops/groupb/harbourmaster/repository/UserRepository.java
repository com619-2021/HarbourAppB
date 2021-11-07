package com.devops.groupb.harbourmaster.repository;


import com.devops.groupb.harbourmaster.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository 
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE first_name = :firstName AND last_name = :lastName", nativeQuery = true)
    public List<User> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT * FROM users WHERE username = :userName", nativeQuery = true)
    public List<User> findByUserName(@Param("userName") String userName);

}
