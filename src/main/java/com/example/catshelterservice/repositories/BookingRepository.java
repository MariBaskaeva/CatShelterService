package com.example.catshelterservice.repositories;

import com.example.catshelterservice.models.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
    List<Booking> findAllByUser_Email(String email, Pageable pageable);
    List<Booking> findAllByCat_Id(Long catId, Pageable pageable);
    Optional<Booking> findById(Long id);
    Booking save(Booking booking);
    void deleteById(Long bookingId);
}
