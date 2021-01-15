package com.example.application.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import com.example.application.data.service.FoodProductRepository;
import com.example.application.data.entity.FoodProduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(FoodProductRepository foodProductRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (foodProductRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Food Product entities...");
            ExampleDataGenerator<FoodProduct> foodProductRepositoryGenerator = new ExampleDataGenerator<>(
                    FoodProduct.class);
            foodProductRepositoryGenerator.setData(FoodProduct::setId, DataType.ID);
            foodProductRepositoryGenerator.setData(FoodProduct::setImage, DataType.FOOD_PRODUCT_IMAGE);
            foodProductRepositoryGenerator.setData(FoodProduct::setName, DataType.FOOD_PRODUCT_NAME);
            foodProductRepositoryGenerator.setData(FoodProduct::setEanCode, DataType.FOOD_PRODUCT_EAN);
            foodProductRepository.saveAll(foodProductRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}