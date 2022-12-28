package com.example.catshelterservice.repositories;

import com.example.catshelterservice.models.Donate;
import com.example.catshelterservice.dto.UserAmount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateRepository extends PagingAndSortingRepository<Donate, Long> {
    Page<Donate> findAllByUser_Email(String email, Pageable pageable);

    @Query("SELECT new com.example.catshelterservice.dto.UserAmount(d.user, sum(d.value)) from Donate d group by d.user order by d.value")
    List<UserAmount> getRatingList(Pageable pageable);

    @Query("SELECT new com.example.catshelterservice.dto.UserAmount(d.user, sum(d.value)) from Donate d where d.cat.id = :catId group by d.user order by d.value asc")
    List<UserAmount> getRatingListByCat(@Param("catId")long catId, Pageable pageable);

    Donate save(Donate donate);

    @Query("select sum(d.value) as s from Donate d where d.user.email = :email")
    Long sumByUser_Email(String email);
}
