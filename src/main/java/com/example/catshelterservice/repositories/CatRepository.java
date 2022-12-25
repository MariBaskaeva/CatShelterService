package com.example.catshelterservice.repositories;

import com.example.catshelterservice.models.Cat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRepository extends PagingAndSortingRepository<Cat, Long> {
    Optional<Cat> getCatById(long id);
    Cat save(Cat cat);
    void deleteById(long id);


}
