package com.example.application.data.service;

import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.Zutat;
import com.example.application.data.repository.ZutatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class ZutatService extends CrudService<Zutat, Integer> {

    private ZutatRepository repository;

    public ZutatService(@Autowired ZutatRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Zutat, Integer> getRepository() {
        return repository;
    }

    public List<Zutat> getRezeptZutaten(int rezeptId) {
        return repository.getRezeptZutaten(rezeptId);
    }

    public Zutat addZutatToRezept(Rezept rezept, double menge, String item) {
        Zutat zutat = new Zutat(menge, item);
        zutat.setRezept(rezept);
        repository.save(zutat);
        return zutat;
    }
}
