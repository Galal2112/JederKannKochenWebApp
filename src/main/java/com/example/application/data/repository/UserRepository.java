package com.example.application.data.repository;

import com.example.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.role=0")
    List<User> getUsersList();

    @Query("select u from User u where u.role=1")
    List<User> getAdminsList();

    @Query("select u from User u where u.email=:email and u.password=:password")
    User getByCredentials(String email, String password);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName = :firstname, u.lastName = :lastname where u.id = :userId")
    void updateUserInfoById(Integer userId, String firstname, String lastname);

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("delete from User u where u.id = :userId")
    void deleteUser(Integer userId);
}