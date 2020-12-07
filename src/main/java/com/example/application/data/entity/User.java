package com.example.application.data.entity;


import com.example.application.data.AbstractEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;


import javax.persistence.Entity;

import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@Entity
public class User extends AbstractEntity {

    private String username;

    private String passwortSalt;

    private String passwortHash;

    private Role role;

    private ArrayList<Rezept> rezepts;

    public User() {

    }


    public User(String username, String passwort, Role role ) {
        this.username = username;
        this.role = role;
        this.passwortSalt = RandomStringUtils.random(32);

        this.passwortHash = sha1Hex(passwort + passwortSalt);
    }


    public boolean checkueberEinStimmung(String pass) {


        return DigestUtils.sha1Hex(pass + passwortSalt).equals(passwortHash);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswortSalt() {
        return passwortSalt;
    }

    public void setPasswortSalt(String passwortSalt) {
        this.passwortSalt = passwortSalt;
    }

    public String getPasswortHash() {
        return passwortHash;
    }

    public void setPasswortHash(String passwortHash) {
        this.passwortHash = passwortHash;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
