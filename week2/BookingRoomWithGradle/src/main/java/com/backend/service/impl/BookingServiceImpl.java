package com.backend.service.impl;

import com.backend.bean.Booking;
import com.backend.bean.Room;
import com.backend.dto.BookingDTO;
import com.backend.enumeration.BookingState;
import com.backend.enumeration.RoomState;
import com.backend.exception.DuplicateBookingException;
import com.backend.repository.BookingRepository;
import com.backend.repository.RoomRepository;
import com.backend.commons.MapperCommon;
import com.backend.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository booking;
    private final RoomRepository roomRepository;
    @Autowired
    public BookingServiceImpl(BookingRepository booking, ModelMapper modelMapper, RoomRepository roomRepository) {
        this.booking = booking;
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
    }

    @Override
    public Booking save(BookingDTO bookingDTO) {
        checkDuplicateBookingException(bookingDTO);
        Booking bookingE = modelMapper.map(bookingDTO, Booking.class);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("admin".equals(name)) {
            bookingE.setBookingState(BookingState.Accepted);
            bookingE.setUserCode(1);
        } else {
            bookingE.setBookingState(BookingState.Processing);
            bookingE.setUserCode(0);
        }
        Room room = roomRepository.findByRoomCode(bookingDTO.getRoom().getRoomCode());
        if (ObjectUtils.nullSafeEquals(RoomState.available, room.getState())
                && ObjectUtils.nullSafeEquals(BookingState.Accepted, bookingE.getBookingState())) {
            room.setState(RoomState.booked);
            roomRepository.save(room);
        }
        return booking.save(bookingE);
    }

    @Override
    public Booking update(BookingDTO bookingDTO) {
        LocalDateTime startTime = bookingDTO.getStartTime();
        LocalDateTime endTime = bookingDTO.getEndTime();
        String roomCode = bookingDTO.getRoom().getRoomCode();
        if (booking.isExistedBooking(roomCode, startTime, endTime,bookingDTO.getId())) {
            throw new DuplicateBookingException("This room has been booked by someone at the same period ! " +
                    "Please choose another period time");
        }
        Room room = roomRepository.findByRoomCode(roomCode);
        bookingDTO.setRoom(room);
        Booking bookingReturn = booking.save(modelMapper.map(bookingDTO, Booking.class));

        if(ObjectUtils.nullSafeEquals(BookingState.Accepted,bookingDTO.getBookingState())){
            room.setState(RoomState.booked);
        }
        if (isExistedBookingByRoomCode(roomCode)) {
            room.setState(RoomState.available);
        }
        roomRepository.save(room);
        return bookingReturn;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Booking> bookingOptional = booking.findById(id);
        if (bookingOptional.isPresent()) {
            bookingOptional.get().setBookingState(BookingState.Canceled);
            Room room = bookingOptional.get().getRoom();
            room.setState(RoomState.available);
            roomRepository.save(room);
            booking.save(bookingOptional.get());
        }
    }

    @Override
    public BookingDTO findById(Integer id) {
        Optional<Booking> bookingOptional = booking.findById(id);
        return bookingOptional.map(value -> modelMapper.map(value, BookingDTO.class)).orElse(null);
    }

    @Override
    public Page<BookingDTO> findAllAndSortingByStartTimeDescending(int pageNo, int pageSize, BookingState bookingState) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("startTime").descending());
        return booking.findAll(bookingState, pageable)
                      .map(bookingConvert -> modelMapper.map(bookingConvert, BookingDTO.class));
    }

    @Override
    public boolean isExistedBookingByTime(String roomCode, LocalDateTime startTime, LocalDateTime endTime) {
        return booking.isExistedBookingByTime(roomCode, startTime, endTime);
    }

    @Override
    public boolean isExistedBookingByRoomCode(String roomCode) {
        return booking.isExistedBookingByRoomCode(roomCode);
    }

    @Override
    public Page<BookingDTO> findAllByCondition(LocalDateTime startTime, LocalDateTime endTime, BookingState bookingState,
                                               String note, String roomCode, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("startTime").descending());
        Page<Booking> bookingPage = booking
                .findAllByStartTimeAndEndTimeOrBookingStateOrNote(startTime, endTime, bookingState, note, roomCode, pageable);

        return bookingPage.map(pageBooking -> modelMapper.map(pageBooking, BookingDTO.class));
    }

    @Override
    public List<BookingDTO> bookingsOfRoom(String roomCode) {
        Room room = roomRepository.findByRoomCode(roomCode);
        return MapperCommon.mapperListToListDTO(room.getBookings(), BookingDTO.class, modelMapper);
    }

    @Override
    public Page<BookingDTO> findBookingByUserCode(Integer userCode, BookingState bookingState, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return booking.findBookingByUserCode(userCode, bookingState, pageable)
                .map(bookingConvert -> modelMapper.map(bookingConvert, BookingDTO.class));
    }

    private void checkDuplicateBookingException(BookingDTO bookingDTO) {
        LocalDateTime startTime = bookingDTO.getStartTime();
        LocalDateTime endTime = bookingDTO.getEndTime();
        String roomCode = bookingDTO.getRoom().getRoomCode();
        if (isExistedBookingByTime(roomCode, startTime, endTime)) {
            throw new DuplicateBookingException("This room has been booked by someone at the same period ! " +
                    "Please choose another period time");
        }
    }
}
