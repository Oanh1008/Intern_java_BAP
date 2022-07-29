package com.backend.service;

import com.backend.bean.Booking;
import com.backend.dto.BookingDTO;
import com.backend.enumeration.BookingState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking save (BookingDTO bookingDTO);

    Booking update (BookingDTO bookingDTO);

    void deleteById(Integer id);

    BookingDTO findById(Integer id);

    Page<BookingDTO> findAllAndSortingByStartTimeDescending(int pageNo, int pageSize, BookingState bookingState);

    Page<BookingDTO> findAllByCondition(LocalDateTime startTime, LocalDateTime endTime, BookingState bookingState,
                                        String note,String roomCode, int pageNo,int pageSize);

    boolean isExistedBookingByTime (String roomCode,LocalDateTime startTime,LocalDateTime localDateTime);

    boolean isExistedBookingByRoomCode(String roomCode);

    List<BookingDTO> bookingsOfRoom(String roomCode);

    Page<BookingDTO> findBookingByUserCode(Integer userCode, BookingState bookingState,int pageNo, int pageSize);
}
