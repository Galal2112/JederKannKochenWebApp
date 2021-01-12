package com.example.application.data.Repos;

import com.example.application.data.entity.Rezept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RezeptsRepo extends JpaRepository<Rezept, Integer> {

    Rezept getRezeptByRezeptname(String name);

    @Query("select c from Rezept c " +
            "where lower(c.rezeptname) like lower(concat('%', :searchTerm, '%')) ")
    List<Rezept> search(@Param("searchTerm") String searchTerm);

}
