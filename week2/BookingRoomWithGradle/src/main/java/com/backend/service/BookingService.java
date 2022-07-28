package com.backend.service;

import com.backend.bean.Booking;
import com.backend.dto.BookingDTO;
import com.backend.enumeration.BookingState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    List<BookingDTO> findAllBookings();

    Booking save (BookingDTO bookingDTO);

    Booking update (BookingDTO bookingDTO);
    void deleteById(Integer id);
    boolean isExistedByBookingId(Integer id);

    BookingDTO findById(Integer id);

    Page<BookingDTO> findAllAndSortingByStartTimeDescending(int pageNo, int pageSize, BookingState bookingState);

    Page<BookingDTO> findAllAndSortingByStartTimeDescending(int pageNo, int pageSize);

    Page<BookingDTO> findAllbyStartTimeAndEndtimeOrBookingStateOrNote(LocalDateTime startTime, LocalDateTime endTime, BookingState bookingState, String note, int pageNo,int pageSize);
    boolean isExistedBookingByTime (String roomCode,LocalDateTime startTime,LocalDateTime localDateTime);

    boolean isExistedBookingByRoomCodeWithStatusAccepted(String roomCode);
}
