package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Rezept extends AbstractEntity {
    private String rezeptName;
    @Lob
    private String beschreibung;
    @ManyToOne(targetEntity=User.class, fetch = FetchType.LAZY)
    private User creator;
    @OneToMany(
            targetEntity= Zutat.class,
            mappedBy = "rezept",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Zutat> zutaten = new ArrayList<>();
    @OneToMany(
            targetEntity= Video.class,
            mappedBy = "rezept",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Video> videos = new ArrayList<>();

    public Rezept() {
    }

    public Rezept(String rezeptName, String beschreibung) {
        this.rezeptName = rezeptName;
        this.beschreibung = beschreibung;
    }

    public String getRezeptName() {
        return rezeptName;
    }

    public void setRezeptName(String rezeptName) {
        this.rezeptName = rezeptName;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String inhalt) {
        this.beschreibung = inhalt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Zutat> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
