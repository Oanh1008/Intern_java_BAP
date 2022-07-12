package com.backend.bean;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @Column(name = "customerID")
    private int customerID;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "note")
    private String note;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomID",referencedColumnName = "roomID")
    private Room room;


}


