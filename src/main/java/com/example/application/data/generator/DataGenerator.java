package com.example.application.data.generator;

import com.example.application.data.Repos.PersonRepository;
import com.example.application.data.Repos.UserRepo;
import com.example.application.data.entity.*;
import com.example.application.data.repository.RezeptRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.repository.VideoRepository;
import com.example.application.data.repository.ZutatRepository;
import com.example.application.data.service.FoodProductRepository;
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
                                      PersonRepository personRepository, UserRepo userRepo,
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

            // Person
            int seed = 123;
            ExampleDataGenerator<Person> personRepositoryGenerator = new ExampleDataGenerator<>(Person.class);
            personRepositoryGenerator.setData(Person::setId, DataType.ID);
            personRepositoryGenerator.setData(Person::setFirstName, DataType.FIRST_NAME);
            personRepositoryGenerator.setData(Person::setLastName, DataType.LAST_NAME);
            personRepositoryGenerator.setData(Person::setEmail, DataType.EMAIL);
            personRepositoryGenerator.setData(Person::setPhone, DataType.PHONE_NUMBER);
            personRepositoryGenerator.setData(Person::setDateOfBirth, DataType.DATE_OF_BIRTH);
            personRepositoryGenerator.setData(Person::setOccupation, DataType.OCCUPATION);
            personRepositoryGenerator.setData(Person::setImportant, DataType.BOOLEAN_10_90);
            personRepository.saveAll(personRepositoryGenerator.create(100, seed));

            userRepo.save(new User("user", "user12345", Role.USER));//Nur Zum Testen

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
