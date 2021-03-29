package com.example.application.data.repository;

import com.example.application.data.entity.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodProductRepository extends JpaRepository<FoodProduct, Integer> {

}
