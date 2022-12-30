package com.example.catshelterservice.services;

import com.example.catshelterservice.dto.UserAmount;
import com.example.catshelterservice.models.Donate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DonateService {
    Donate createDonate(String username, long catId, long value);
    Page<Donate> getDonates(String username, int page);
    long getDonatesAmount(String username);

    long getDonatesAmountByCat(Long catId);

    List<UserAmount> getRating(int page);
    List<UserAmount> getRatingByCat(long catId, int page);
}
