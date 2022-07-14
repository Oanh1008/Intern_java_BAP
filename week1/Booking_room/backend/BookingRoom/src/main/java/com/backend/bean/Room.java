package com.backend.bean;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "price")
    private Integer price;

    @Column(name = "image")
    private String image;

    @Column(name ="state")
    private String state;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "room")
    private List<Booking> books;



}
