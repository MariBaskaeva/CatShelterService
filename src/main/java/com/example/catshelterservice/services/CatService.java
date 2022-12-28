package com.example.catshelterservice.services;

import com.example.catshelterservice.dto.CatCreateDTO;
import com.example.catshelterservice.dto.CatDTO;
import com.example.catshelterservice.exceptions.FailedDownloadingException;
import com.example.catshelterservice.exceptions.FailedSavingException;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import org.springframework.data.domain.Page;

public interface CatService {
    Page<CatDTO> getCards(String search, int page, int limit, User user);
    Cat createCat(CatCreateDTO cat) throws FailedSavingException;
    Cat getCat(long id) throws Exception;
    void removeCat(long id);
    Cat updateCat(long id, Cat cat);
    byte[] getImage(Long id) throws FailedDownloadingException;
}
