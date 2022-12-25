package com.example.catshelterservice.controllers;

import com.example.catshelterservice.dto.CatDTO;
import com.example.catshelterservice.exceptions.NoSuchCatException;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.services.CatServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Slf4j
public class CatController {
    @Autowired
    private CatServiceImpl service;

    @GetMapping("/cats")
    public Page<Cat> getAllCats(@RequestParam String search, @RequestParam int page, @RequestParam int limit) {
        return service.getCards(search, page, limit);
    }

    @PostMapping("/cats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CatDTO postCat(@RequestBody Cat cat){
        return new CatDTO(service.createCat(cat), 0L, 0L, false);
    }

    @GetMapping("/cats/{id}")
    public CatDTO getCat(@PathVariable long id, @AuthenticationPrincipal User user) throws Exception {
        Cat cat = service.getCat(id);
        return new CatDTO(cat, (long) cat.getUsers().size(), 0L, cat.getUsers().contains(user));
    }

    @DeleteMapping("/cats/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCat(@PathVariable long id){
        service.removeCat(id);
    }

    @PutMapping("/cats/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CatDTO putCat(@PathVariable long id, @RequestBody Cat cat){
        Cat updateCat = service.updateCat(id, cat);
        return new CatDTO(updateCat, (long) updateCat.getUsers().size(), 0L, false);
    }

    @PostMapping("/like/{catId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void likeCat(@PathVariable long catId, @AuthenticationPrincipal User user) throws NoSuchCatException {
        service.likeCat(catId, user);
    }

    @DeleteMapping("/like/{catId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void dislikeCat(@PathVariable long catId, @AuthenticationPrincipal User user) throws NoSuchCatException {
        service.dislikeCat(catId, user);
    }

    @GetMapping("/like/{catId}")
    public long getLikesForCat(@PathVariable long catId) throws NoSuchCatException {
        return service.getLikesForCat(catId);
    }

    @GetMapping("/like")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Set<Cat> getLikedCats(@AuthenticationPrincipal User user) {
        log.info("{}", user);

        return user.getCats();
    }
}
