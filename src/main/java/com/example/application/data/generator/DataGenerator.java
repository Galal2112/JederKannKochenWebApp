package com.example.application.data.generator;

import com.example.application.data.Repos.UserRepo;
import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.example.application.data.service.PersonRepository;
import com.example.application.data.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PersonRepository personRepository, UserRepo userRepo) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (personRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Person entities...");
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


            userRepo.save(new User("u", "1" , Role.USER));
            userRepo.save(new User("a", "1", Role.ADMIN));
            userRepo.save(new User("Amro3", "12345", Role.USER));

            logger.info("Generated demo data");
        };
    }

}