package com.revature.boot.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.boot.entities.User;

public interface UserDao extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "insert into users values (default, :username, :password)", nativeQuery = true)
    void createUser(@Param("username") String username, @Param("password") String password);
}
