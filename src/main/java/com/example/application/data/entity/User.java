package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {
    private String passwortSalt;
    private String passwortHash;
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String password;
    private Role role;
    private Gender gender;
    @OneToMany(
            targetEntity = Rezept.class,
            mappedBy = "creator",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Rezept> rezepten = new ArrayList<>();
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profileImage;

    public User() {
    }

    public User(String username, String passwort, Role role) {
        this.username = username;
        this.role = role;
        this.passwortSalt = RandomStringUtils.random(32);
        this.passwortHash = DigestUtils.sha1Hex(passwort + passwortSalt);
    }

    public User(String firstName, String lastName, String email, String phone, LocalDate dateOfBirth, String password, Role role, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.passwortSalt = RandomStringUtils.random(32);
        this.passwortHash = DigestUtils.sha1Hex(password + passwortSalt);
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Rezept> getRezepten() {
        return rezepten;
    }

    public void setRezepten(List<Rezept> rezepten) {
        this.rezepten = rezepten;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profilePicture) {
        this.profileImage = profilePicture;
    }

}
