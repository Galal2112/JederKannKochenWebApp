package com.example.application.data.service;

import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.data.entity.UserNotification;
import com.example.application.data.repository.UserNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.Date;
import java.util.List;

@Service
public class UserNotificationService extends CrudService<UserNotification, Integer> {

    private UserNotificationRepository repository;

    public UserNotificationService(@Autowired UserNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected UserNotificationRepository getRepository() {
        return repository;
    }

    public List<UserNotification> getUserNotifications(int userId) {
        return repository.getUserNotifications(userId);
    }

    public UserNotification sendNotificationToAllUser(User sender, String message) throws IllegalArgumentException {
        return createNotification(sender, null, message);
    }
/*
    public UserNotification sendNotificationToUser(User sender, int receiverId, String message) throws IllegalArgumentException {
        return createNotification(sender, receiverId, message);
    }*/

    private UserNotification createNotification(User sender, Integer receiverId, String message) throws IllegalArgumentException {
        if (sender.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can send notifications");
        }
        UserNotification userNotification = new UserNotification(message, sender, receiverId, new Date());
        repository.save(userNotification);
        return userNotification;
    }

}
