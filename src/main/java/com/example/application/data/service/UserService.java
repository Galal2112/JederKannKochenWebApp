package com.example.application.data.service;

import com.example.application.data.entity.Gender;
import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDate;

@Service
public class UserService extends CrudService<User, Integer> {

    private UserRepository repository;

    public UserService(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    public User createUser(String firstName, String lasName, String phone, String email, String password,
                           LocalDate dateOfBirth, Gender gender) throws IllegalArgumentException {

        return createUser(firstName, lasName, phone, email, password, dateOfBirth, gender, Role.USER);
    }

    public User createAdmin(String firstName, String lasName, String phone, String email, String password,
                           LocalDate dateOfBirth, Gender gender) throws IllegalArgumentException {

        return createUser(firstName, lasName, phone, email, password, dateOfBirth, gender, Role.ADMIN);
    }

    public User login(String email, String password) throws IllegalArgumentException {
        User existingUser = repository.getByCredentials(email, password);
        if (existingUser == null) {
            throw new IllegalArgumentException("Invalid Username and/or Password");
        }
        return existingUser;
    }

    public void updateUserInfo(Integer id, String firstName, String lastName) {
        repository.updateUserInfoById(id, firstName, lastName);
    }

    private User createUser(String firstName, String lasName, String phone, String email, String password,
                           LocalDate dateOfBirth, Gender gender, Role role) throws IllegalArgumentException {

        User existingUser = repository.findByEmail(email);
        if (existingUser == null) {
            User user = new User(firstName,lasName, email,
                    phone, dateOfBirth,
                    password, role, gender);
            repository.save(user);
            return user;
        } else {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
