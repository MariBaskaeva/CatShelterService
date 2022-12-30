package com.example.catshelterservice.controllers;

import com.example.catshelterservice.dto.DonateToCatDTO;
import com.example.catshelterservice.services.DonateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Validated
@CrossOrigin(origins = "${frontend.endpoint}", allowCredentials = "true")
public class DonateController {
    @Autowired
    private DonateServiceImpl service;

    @PostMapping("/donate")
    private ResponseEntity<?> donatePost(@RequestBody DonateToCatDTO donateToCatDTO, Principal principal){
        return ResponseEntity.ok(service.createDonate(principal == null ? null : principal.getName(), donateToCatDTO.getId(), donateToCatDTO.getAmount()));
    }

    @GetMapping("/donate")
    private ResponseEntity<?> donateGet(@RequestParam(required = false) Integer page, Principal principal) {
        return ResponseEntity.ok(service.getDonates(principal.getName(), 0));
    }

    @GetMapping("/donate/amount")
    private ResponseEntity<?> donatesAmountGet(Principal principal) {
        return ResponseEntity.ok(service.getDonatesAmount(principal.getName()));
    }

    @GetMapping("/rating")
    private ResponseEntity<?> ratingGet(@RequestParam(required = false) Integer page){
        return ResponseEntity.ok(service.getRating(0));
    }
    @GetMapping("/rating/{catId}")
    private ResponseEntity<?> ratingGetByCat(@RequestParam(required = false) Integer page, @PathVariable long catId){
        return ResponseEntity.ok(service.getRatingByCat(catId, 0));
    }
}
