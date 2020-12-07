package com.example.application.data.Repos;

import com.example.application.data.entity.Rezept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RezeptsRepo extends JpaRepository<Rezept, Integer> {

    Rezept getRezeptByname(String name);

}
