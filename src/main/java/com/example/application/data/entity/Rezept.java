package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import java.util.ArrayList;


@Entity
public class Rezept extends AbstractEntity {


    private String rezeptname;

    private String[] inhalt;

    private String video;

    private String[] zutaten;

    private ArrayList<User> users;


    public Rezept(String video, ArrayList<User> users, String name, Zutat[] zutaten, String[] inhalt) {
        this.video = video;
        this.users = users;
        this.rezeptname = name;
        this.inhalt = inhalt;

        this.zutaten = new String[zutaten.length];
        for (int i = 0; i < zutaten.length; i++) {

            this.zutaten[i] = zutaten[i].getName();

        }

    }

    public Rezept() {

    }

    public String getRezeptname() {
        return rezeptname;
    }

    public void setRezeptname(String rezeptname) {
        this.rezeptname = rezeptname;
    }

    public String[] getInhalt() {
        return inhalt;
    }

    public void setInhalt(String[] inhalt) {
        this.inhalt = inhalt;
    }

    public String[] getZutaten() {
        return zutaten;
    }

    public void setZutaten(String[] zutaten) {
        this.zutaten = zutaten;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public static Object isImportant(Rezept rezept) {

        return null;
    }
}
