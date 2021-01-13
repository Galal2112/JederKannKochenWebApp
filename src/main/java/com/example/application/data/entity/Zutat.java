package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Zutat extends AbstractEntity {

    private double menge;
    private String item;
    @ManyToOne(targetEntity=Rezept.class)
    private Rezept rezept;

    public Zutat() {
    }

    public Zutat(double menge, String item) {
        this.menge = menge;
        this.item = item;
    }

    public double getMenge() {
        return menge;
    }

    public void setMenge(double menge) {
        this.menge = menge;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Rezept getRezept() {
        return rezept;
    }

    public void setRezept(Rezept rezept) {
        this.rezept = rezept;
    }
}
