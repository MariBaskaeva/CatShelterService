package com.example.catshelterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatCreateDTO {
    private String name;
    private Integer age;
    private String color;
    private String sex;
    private String vaccinations;
    private String description;
    private byte[] image;
}
