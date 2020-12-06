package com.example.application.data.service;

import com.example.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    User getByUsername(String username);


}
