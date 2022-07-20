package com.backend.bean;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "room")
public class Room extends BaseEntity{
    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "min_size")
    private Integer min;

    @Column(name = "max_size")
    private Integer max;

    @Column(name = "price")
    private Double price;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Column(name = "state")
    private String state;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

}
