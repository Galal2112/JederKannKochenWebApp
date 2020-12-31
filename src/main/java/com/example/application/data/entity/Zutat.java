package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Zutat extends AbstractEntity {


    private String name;


    public Zutat(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Zutat{" +
                "name='" + name + '\'' +
                '}';
    }
}