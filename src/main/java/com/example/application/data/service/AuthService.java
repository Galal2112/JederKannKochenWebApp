package com.example.application.data.service;

import com.example.application.data.Repos.UserRepo;
import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;


@Service
public class AuthService {


    private final UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void authen(String username, String passwort) throws AuthenticationException, AuthException {

        User user = userRepo.getByUsername(username);
        if (user != null && user.checkueberEinStimmung(passwort)) {
            VaadinSession.getCurrent().setAttribute(User.class, user);

        } else {
            throw new AuthException();
        }

    }

    public void registrieren(String username, String pass1) {

        userRepo.save(new User(username, pass1, Role.USER));
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }
}