package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import java.util.ArrayList;


@Entity
public class Rezept extends AbstractEntity {


    private String name;

    private String[] inhalt;

    private String video;

    private ArrayList<Zutat> zutaten;

    private ArrayList<User> users;


    public Rezept(String video, ArrayList<User> users, String name, ArrayList<Zutat> zutaten, String[] inhalt) {
        this.video = video;
        this.users = users;
        this.name = name;
        this.zutaten = zutaten;
        this.inhalt = inhalt;
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
}
