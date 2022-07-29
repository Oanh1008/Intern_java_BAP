package com.backend.bean;

import com.backend.enumeration.BookingState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "room_code",referencedColumnName = "room_code")
    private Room room;

    @Column(name = "user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCode;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private BookingState bookingState;

    @Column(name ="note")
    private String note;



}
