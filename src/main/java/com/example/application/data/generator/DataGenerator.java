package com.example.application.data.generator;


import com.example.application.data.entity.*;
import com.example.application.data.repository.RezeptRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.repository.VideoRepository;
import com.example.application.data.repository.ZutatRepository;
import com.example.application.data.repository.FoodProductRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, RezeptRepository rezeptRepository,
                                      ZutatRepository zutatRepository, VideoRepository videoRepository,
                                      FoodProductRepository foodProductRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("... generating entities...");
            Video video1 = new Video("https//jederkannkochen.com/1");
            Video video2 = new Video("https//jederkannkochen.com/2");
            Zutat zutat1 = new Zutat(2, "Kaese");
            Zutat zutat2 = new Zutat(3, "Brot");
            Rezept rezept1 = new Rezept("Kaesebroetchen", "Pizza");
            User adminUser = new User("Galal","Ahmed", "test@google.com",
                    "+49 17827646545", LocalDate.of(1900, 12, 12),
                    "123456", Role.ADMIN, Gender.MALE);
            userRepository.save(adminUser);
            rezept1.setCreator(adminUser);
            rezeptRepository.save(rezept1);
            video1.setRezept(rezept1);
            video2.setRezept(rezept1);
            videoRepository.save(video1);
            videoRepository.save(video2);
            zutat1.setRezept(rezept1);
            zutat2.setRezept(rezept1);
            zutatRepository.save(zutat1);
            zutatRepository.save(zutat2);
            rezept1.setZutaten(new ArrayList<>(Arrays.asList(zutat1, zutat2)));

            userRepository.save(new User("user", "user12345", Role.USER));//Nur Zum Testen

            userRepository.save(new User("admin", "admin000", Role.ADMIN));

            String inhalt = "Zwiebel, Knolauch";

            // Koshary
            Zutat[] kosharyZutat = new Zutat[3];
            kosharyZutat[0] = new Zutat(500, "Tomaten");
            kosharyZutat[1] = new Zutat(1000, "Reis");
            kosharyZutat[2] = new Zutat(300, "Pasta");
            Rezept kosharyRezept = new Rezept("koshary", inhalt);
            kosharyRezept.setCreator(adminUser);
            rezeptRepository.save(kosharyRezept);
            for (Zutat zutat : kosharyZutat) {
                zutat.setRezept(kosharyRezept);
                zutatRepository.save(zutat);
            }
            Video kosharyVideo = new Video("https://www.youtube.com/watch");
            kosharyVideo.setRezept(kosharyRezept);
            videoRepository.save(kosharyVideo);


            Rezept pastaRezept = new Rezept("pasta", inhalt);
            pastaRezept.setCreator(adminUser);
            rezeptRepository.save(pastaRezept);
            Zutat[] pastaZutat = new Zutat[2];
            pastaZutat[0] = new Zutat(500, "Tomaten");
            pastaZutat[1] = new Zutat(300, "Pasta");
            for (Zutat zutat : pastaZutat) {
                zutat.setRezept(pastaRezept);
                zutatRepository.save(zutat);
            }
            Video pastaVideo = new Video("https://www.youtube.com/watch");
            pastaVideo.setRezept(pastaRezept);
            videoRepository.save(pastaVideo);

            // food products
            int seed = 123;
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
