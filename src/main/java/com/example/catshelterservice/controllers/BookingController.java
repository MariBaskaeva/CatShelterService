package com.example.catshelterservice.controllers;

import com.example.catshelterservice.dto.StatusDTO;
import com.example.catshelterservice.exceptions.BookingsListException;
import com.example.catshelterservice.exceptions.StatusException;
import com.example.catshelterservice.services.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingServiceImpl service;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> bookingPost(@RequestBody Long catId, Principal principal) throws BookingsListException {
        return ResponseEntity.ok(service.createBooking(principal.getName(), catId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> bookingGet(@RequestParam int page) {
        return ResponseEntity.ok(service.getBookings(page));
    }

    @GetMapping("/cat/{catId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> bookingGetByCatId(@RequestParam int page, @PathVariable Long catId) {
        return ResponseEntity.ok(service.getBookingsByCat(page, catId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> bookingGetByUserId(@RequestParam int page, @PathVariable Long userId, Principal principal) throws BookingsListException {
        return ResponseEntity.ok(service.getBookingsByUser(principal.getName(), page, userId));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> bookingPut(@PathVariable long id, @RequestBody StatusDTO statusDTO) throws BookingsListException, StatusException {
        String status = statusDTO.getStatus();

        if (status.equals("Approved"))
            return ResponseEntity.ok(service.approveBooking(id));
        else if (status.equals("Denied"))
            return ResponseEntity.ok(service.denyBooking(id));
        else
            throw new StatusException();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void bookingDelete(@PathVariable long id) throws InternalError {
        service.deleteBooking(id);
    }
}
