package com.example.catshelterservice.services;

import com.example.catshelterservice.exceptions.BookingsListException;
import com.example.catshelterservice.exceptions.UnauthorizedException;
import com.example.catshelterservice.models.Booking;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {
    Booking createBooking(String username, Long catId)throws UnauthorizedException, BookingsListException;
    Page<Booking> getBookings(int page) throws UnauthorizedException, BookingsListException;
    List<Booking> getBookingsByCat(int page, Long catId) throws UnauthorizedException, BookingsListException;
    List<Booking> getBookingsByUser(String username, int page, Long userId) throws UnauthorizedException, BookingsListException;
    Booking denyBooking(Long bookingId) throws UnauthorizedException, BookingsListException;
    Booking approveBooking(Long bookingId) throws UnauthorizedException, BookingsListException;
    void deleteBooking(Long bookingId)  throws UnauthorizedException, InternalError;
}