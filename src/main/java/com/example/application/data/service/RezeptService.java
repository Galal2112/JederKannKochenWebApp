package com.example.application.data.service;

import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.User;
import com.example.application.data.repository.RezeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class RezeptService extends CrudService<Rezept, Integer> {

    private RezeptRepository repository;

    public RezeptService(@Autowired RezeptRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Rezept, Integer> getRepository() {
        return repository;
    }

    public Rezept createRezept(User creator, String name, String inhalt) {
        Rezept rezept = new Rezept(name, inhalt);
        rezept.setCreator(creator);
        repository.save(rezept);
        return rezept;
    }

    public List<Rezept> getUserRezepte(int creatorId) {
        return repository.getUserRezepte(creatorId);
    }

    public List<Rezept> getRezeptePage(int offset, int limit) {
        return repository.getRezeptePage(offset, limit);
    }

    public List<Rezept> getAllRezepte() {
        return repository.findAll();
    }

    public Integer getUserRezepteCount(int creatorId) {
        return repository.countOfRezepte(creatorId);
    }

    public void updateRezept(Integer id, String name, String inhalt) {
        repository.setRezeptInfoById(id, name, inhalt);
    }

    public List<Rezept> findAll() {
        return repository.findAll();
    }
    public List<Rezept> findAll(String eingabe) {
        if (eingabe == null || eingabe.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(eingabe);
        }
    }
}
