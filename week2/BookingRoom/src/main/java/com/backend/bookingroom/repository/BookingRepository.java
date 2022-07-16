package com.backend.bookingroom.repository;

import com.backend.bookingroom.bean.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
