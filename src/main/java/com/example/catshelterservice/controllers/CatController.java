package com.example.catshelterservice.controllers;

import com.example.catshelterservice.dto.CatCreateDTO;
import com.example.catshelterservice.dto.CatDTO;
import com.example.catshelterservice.exceptions.FailedSavingException;
import com.example.catshelterservice.exceptions.NoSuchCatException;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.services.CatServiceImpl;
import com.example.catshelterservice.services.DonateService;
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
@CrossOrigin(origins = "${frontend.endpoint}", allowCredentials = "true")
public class CatController {
    @Autowired
    private CatServiceImpl service;

    @Autowired
    private DonateService donateService;

    @GetMapping("/cats")
    public Page<CatDTO> getAllCats(@RequestParam(required = false) String search, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit, @AuthenticationPrincipal User user) {
        return service.getCards("name", 0, 20, user);
    }

    @PostMapping("/cats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CatDTO postCat(@RequestBody CatCreateDTO cat) throws FailedSavingException {
        log.info("{}", cat);
        return new CatDTO(service.createCat(cat), 0L, 0L, false, cat.getImage());
    }

    @GetMapping("/cats/{id}")
    public CatDTO getCat(@PathVariable long id, @AuthenticationPrincipal User user) throws Exception {
        Cat cat = service.getCat(id);

        return new CatDTO(cat, (long) cat.getUsers().size(),
                            donateService.getDonatesAmountByCat(id),
                            cat.getUsers().contains(user), service.getImage(id));
    }

    @DeleteMapping("/cats/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCat(@PathVariable long id){
        service.removeCat(id);
    }

    @PutMapping("/cats/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CatDTO putCat(@PathVariable long id, @RequestBody CatCreateDTO cat, @AuthenticationPrincipal User user){
        Cat updateCat = service.updateCat(id, new Cat(cat.getName(), cat.getAge(), cat.getColor(),
                                                        cat.getSex(), cat.getVaccinations(), cat.getDescription()));

        return new CatDTO(updateCat, (long) updateCat.getUsers().size(), 0L,
                            updateCat.getUsers().contains(user), cat.getImage());
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
