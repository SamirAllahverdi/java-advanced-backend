package com.example;

import com.example.entity.User;
import com.example.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RestApiApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public RestApiApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        userRepository.save(user);

        User user2 = new User();
        user.setName("Will");
        user.setSurname("Smith");
        userRepository.save(user2);
    }
}
