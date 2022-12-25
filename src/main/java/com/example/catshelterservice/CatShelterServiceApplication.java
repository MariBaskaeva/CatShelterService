package com.example.catshelterservice;

import com.example.catshelterservice.models.Role;
import com.example.catshelterservice.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CatShelterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatShelterServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner addRoles(RoleRepository repository) throws Exception {
        return args -> {
            repository.save(new Role(2L, "ROLE_ADMIN"));
            repository.save(new Role(1L, "ROLE_USER"));
        };
    }
}
