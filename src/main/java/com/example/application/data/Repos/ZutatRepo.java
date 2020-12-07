package com.example.application.data.Repos;


import com.example.application.data.entity.Zutat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZutatRepo extends JpaRepository<Zutat, Integer> {

    Zutat getZutatByname(String name);


}
