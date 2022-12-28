package com.example.catshelterservice.services;

import com.example.catshelterservice.dto.CatDTO;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatToCatDTOMapper {
    @Autowired
    private DonateService donateService;

    public CatDTO catToDTO(Cat cat, User user, byte[] image){
        return   new CatDTO(cat, (long) cat.getUsers().size(),
                user == null ? null : donateService.getDonatesAmount(user.getEmail()),
                cat.getUsers().contains(user), image);
    }
}
