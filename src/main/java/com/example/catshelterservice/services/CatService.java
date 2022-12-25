package com.example.catshelterservice.services;

import com.example.catshelterservice.models.Cat;
import org.springframework.data.domain.Page;

public interface CatService {
    Page<Cat> getCards(String search, int page, int limit);
    Cat createCat(Cat cat);
    Cat getCat(long id) throws Exception;
    void removeCat(long id);
    Cat updateCat(long id, Cat cat);
}
