package com.example.application.data.generator;

import com.example.application.data.entity.*;
import com.example.application.data.repository.RezeptRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.repository.VideoRepository;
import com.example.application.data.repository.ZutatRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, RezeptRepository rezeptRepository,
                                      ZutatRepository zutatRepository, VideoRepository videoRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("... generating entities...");
            Video video1 = new Video("https//jederkannkochen.com/1");
            Video video2 = new Video("https//jederkannkochen.com/2");
            Zutat zutat1 = new Zutat(2, "Kaese");
            Zutat zutat2 = new Zutat(3, "Brot");
            Rezept rezept1 = new Rezept("Kaesebroetchen", "Pizza");
            User user = new User("Galal","Ahmed", "test@google.com",
                    "+49 17827646545", LocalDate.of(1900, 12, 12),
                    "123456", Role.ADMIN, Gender.MALE);
            userRepository.save(user);
            rezept1.setCreator(user);
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

            logger.info("Generated demo data");
        };
    }
}
