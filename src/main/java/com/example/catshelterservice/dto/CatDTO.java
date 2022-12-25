package com.example.catshelterservice.dto;

import com.example.catshelterservice.models.Cat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatDTO {
    private Long id;
    private String name;
    private Integer age;
    private String color;
    private String sex;
    private String vaccinations;
    private String description;
    private String imagePath;
    private Long likes;
    private Long donatesAmount;
    private Boolean isLiked;

    public CatDTO(Cat cat, Long likes, Long donatesAmount, Boolean isLiked){
        this.id = cat.getId();
        this.name = cat.getName();
        this.age = cat.getAge();
        this.color = cat.getColor();
        this.sex = cat.getSex();
        this.vaccinations = cat.getVaccinations();
        this.description = cat.getDescription();
        this.imagePath = cat.getImagePath();
        this.likes = likes;
        this.donatesAmount = donatesAmount;
        this.isLiked = isLiked;
    }
}
