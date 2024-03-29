package com.example.application.data.service;

import com.example.application.data.entity.FoodProduct;

import com.example.application.data.repository.FoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class FoodProductService extends CrudService<FoodProduct, Integer> {

    private FoodProductRepository repository;

    public FoodProductService(@Autowired FoodProductRepository repository) {
        this.repository = repository;
    }

    @Override
    protected FoodProductRepository getRepository() {
        return repository;
    }

}
