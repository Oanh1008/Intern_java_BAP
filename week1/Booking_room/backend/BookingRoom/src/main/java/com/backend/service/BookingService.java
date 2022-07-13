package com.backend.service;

import com.backend.bean.Booking;
import com.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;


public interface BookingService {
    Booking save(Booking booking);

}
