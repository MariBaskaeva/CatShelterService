package com.example.catshelterservice.services;

import com.example.catshelterservice.exceptions.BookingsListException;
import com.example.catshelterservice.models.Booking;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.repositories.BookingRepository;
import com.example.catshelterservice.repositories.CatRepository;
import com.example.catshelterservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final int LIMIT = 10;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Booking createBooking(String email, Long catId) throws BookingsListException {
        Optional<User> user = userRepository.getUserByEmail(email);
        Optional<Cat> cat = catRepository.getCatById(catId);

        if (user.isEmpty() || cat.isEmpty())
            throw new BookingsListException();

        Booking booking = new Booking(null, LocalDateTime.now(), Booking.Status.WAITING, cat.get(), user.get());
        return bookingRepository.save(booking);
    }

    public Booking.Status getStatus(Long catId, User user){
        Optional<Booking> booking = bookingRepository.getBookingByUserAndCat_Id(user, catId);
        if (booking.isEmpty())
            return null;

        return booking.get().getStatus();
    }

    @Override
    public Page<Booking> getBookings(int page) {
        return bookingRepository.findAll(PageRequest.of(page, LIMIT));
    }

    @Override
    public List<Booking> getBookingsByCat(int page, Long catId) {
        return bookingRepository.findAllByCat_Id(catId, PageRequest.of(page, LIMIT));
    }

    @Override
    public List<Booking> getBookingsByUser(String email, int page, Long userId) throws BookingsListException {
        return bookingRepository.findAllByUser_Email(email, PageRequest.of(page, LIMIT));
    }

    @Override
    public Booking denyBooking(Long bookingId) throws BookingsListException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        Booking booked = null;

        if(booking.isPresent()) {
            booked = booking.get();
            booked.setStatus(Booking.Status.DENIED);
            bookingRepository.save(booked);
        }

        return booked;
    }

    @Override
    public Booking approveBooking(Long bookingId) throws BookingsListException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        Booking booked = null;

        if(booking.isPresent()) {
            booked = booking.get();
            booked.setStatus(Booking.Status.APPROVED);
            bookingRepository.save(booked);
        }

        return booked;
    }

    @Override
    public void deleteBooking(Long bookingId) throws InternalError {
        bookingRepository.deleteById(bookingId);
    }
}
