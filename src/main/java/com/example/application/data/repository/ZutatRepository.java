package com.example.application.data.repository;

import com.example.application.data.entity.Zutat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZutatRepository extends JpaRepository<Zutat, Integer> {

    @Query("select z from Zutat z where z.rezept.id=:rezeptId")
    List<Zutat> getRezeptZutaten(Integer rezeptId);
}
