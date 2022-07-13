package com.backend.bean;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "room")
public class Room {
    @Id
    private String roomID;

    @Column(name = "min")
    private int min;

    @Column(name = "max")
    private int max;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    @Column(name ="isBooked")
    private Boolean isBooked;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "room")
    private List<Booking> books;



}
