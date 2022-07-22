package com.backend.service;

import com.backend.bean.Booking;
import com.backend.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    List<BookingDTO> findAllBookings();

    Booking save (BookingDTO bookingDTO);
    void deleteById(Integer id);
    boolean isExistedByBookingId(Integer id);

    BookingDTO findById(Integer id);
}
