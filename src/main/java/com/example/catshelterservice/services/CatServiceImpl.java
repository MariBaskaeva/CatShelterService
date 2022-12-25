package com.example.catshelterservice.services;

import com.example.catshelterservice.dto.CatDTO;
import com.example.catshelterservice.exceptions.NoSuchCatException;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatServiceImpl implements CatService{
    @Autowired
    private CatRepository catRepository;

    @Override
    public Page<Cat> getCards(String search, int page, int limit) {
        return catRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, search)));
    }

    @Override
    public Cat createCat(Cat cat) {
        return catRepository.save(cat);
    }

    @Override
    public Cat getCat(long id) throws NoSuchCatException {
        Optional<Cat> catOpt = catRepository.getCatById(id);
        if(catOpt.isEmpty())
            throw new NoSuchCatException();

        return catOpt.get();
    }

    @Override
    public void removeCat(long id) {
        catRepository.deleteById(id);
    }

    @Override
    public Cat updateCat(long id, Cat cat) {
        cat.setId(id);
        return catRepository.save(cat);
    }

    public void likeCat(long catId, User user) throws NoSuchCatException {
        Optional<Cat> catOpt = catRepository.getCatById(catId);

        if(catOpt.isEmpty()){
            throw new NoSuchCatException();
        }
        Cat cat = catOpt.get();
        cat.getUsers().add(user);

        catRepository.save(cat);
    }
    public void dislikeCat(long catId, User user) throws NoSuchCatException {
        Optional<Cat> catOpt = catRepository.getCatById(catId);

        if(catOpt.isEmpty()){
            throw new NoSuchCatException();
        }
        Cat cat = catOpt.get();
        cat.getUsers().remove(user);

        catRepository.save(cat);
    }

    public long getLikesForCat(long catId) throws NoSuchCatException {
        Optional<Cat> cat = catRepository.getCatById(catId);
        if(cat.isEmpty())
            throw new NoSuchCatException();

        return cat.get().getUsers().size();
    }
}
