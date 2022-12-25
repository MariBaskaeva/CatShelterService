package com.example.catshelterservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum FilterSort {
    ID_ASC(Sort.by(Sort.Order.asc("title"),
            Sort.Order.desc("createOn")));

    private final Sort sortValue;
}
