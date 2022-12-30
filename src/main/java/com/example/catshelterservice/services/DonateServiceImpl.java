package com.example.catshelterservice.services;

import com.example.catshelterservice.dto.UserAmount;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.Donate;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.repositories.CatRepository;
import com.example.catshelterservice.repositories.DonateRepository;
import com.example.catshelterservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonateServiceImpl implements DonateService{
    private final int LIMIT = 10;

    @Autowired
    private DonateRepository donateRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CatRepository catRepository;

    @Override
    public Donate createDonate(String email, long catId, long amount) {
        Optional<User> user = userRepository.getUserByEmail(email);
        Optional<Cat> cat = catRepository.getCatById(catId);

        return donateRepository.save(new Donate(null, LocalDateTime.now(), amount, cat.orElse(null), user.orElse(null)));
    }

    @Override
    public Page<Donate> getDonates(String email, int page) {
        return donateRepository.findAllByUser_Email(email, PageRequest.of(page, LIMIT));
    }

    @Override
    public long getDonatesAmount(String email) {
        if(email == null){
            return 0L;
        } else {
            Long sum = donateRepository.sumByUser_Email(email);
            return sum == null ? 0L : sum;
        }
    }

    @Override
    public long getDonatesAmountByCat(Long catId) {
        Long sum = donateRepository.sumByCat(catId);
        return sum == null ? 0L : sum;
    }

    @Override
    public List<UserAmount> getRating(int page) {
        return donateRepository.getRatingList(PageRequest.of(page, LIMIT));
    }

    @Override
    public List<UserAmount> getRatingByCat(long catId, int page) {
        return donateRepository.getRatingListByCat(catId, PageRequest.of(page, LIMIT));
    }
}
