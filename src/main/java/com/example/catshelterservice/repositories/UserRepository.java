package com.example.catshelterservice.repositories;

import com.example.catshelterservice.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    User findByEmail(String email);

    List<User> findAll();
}
