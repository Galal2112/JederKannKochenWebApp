package com.example.application.data.repository;

import com.example.application.data.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {

    @Query("select n from UserNotification n where n.receiverId=:receiverId or n.receiverId IS NULL ORDER BY n.createdAt DESC")
    List<UserNotification> getUserNotifications(Integer receiverId);
}
