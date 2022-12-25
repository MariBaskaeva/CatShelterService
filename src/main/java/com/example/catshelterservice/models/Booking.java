package com.example.catshelterservice.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;
    private Status status;
    @ManyToOne
    private Cat cat;
    @ManyToOne
    private User user;

    public enum Status{
        WAITING,
        APPROVED,
        DENIED
    }
}
