package com.example.application.data.service;

import com.example.application.data.Repos.RezeptsRepo;
import com.example.application.data.entity.Rezept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.Collection;
import java.util.List;


@Service
public class RezeptService extends CrudService<Rezept, Integer> {

    private RezeptsRepo rezeptsRepo;


    public RezeptService(@Autowired RezeptsRepo rezeptsRepo) {
        this.rezeptsRepo = rezeptsRepo;
    }

    @Override
    protected JpaRepository<Rezept, Integer> getRepository() {
        return null;
    }

    public List<Rezept> findAll() {
        return rezeptsRepo.findAll();
    }

    public List<Rezept> findAll(String eingabe) {


        if (eingabe == null || eingabe.isEmpty()) {

            return rezeptsRepo.findAll();

        } else {

            return rezeptsRepo.search(eingabe);


        }


    }
}
