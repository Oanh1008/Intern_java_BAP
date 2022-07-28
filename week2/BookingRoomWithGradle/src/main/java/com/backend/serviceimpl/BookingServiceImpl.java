package com.backend.serviceimpl;

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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private ModelMapper modelMapper;

    private BookingRepository booking;

    private RoomRepository roomRepository;


    @Autowired
    public BookingServiceImpl(BookingRepository booking, ModelMapper modelMapper, RoomRepository roomRepository) {

        this.booking = booking;

        this.modelMapper = modelMapper;

        this.roomRepository = roomRepository;

    }


    @Override
    public List<BookingDTO> findAllBookings() {
        return MapperCommon.mapperListToListDTO(booking.findAll(), BookingDTO.class, modelMapper);
    }

    @Override
    public Booking save(BookingDTO bookingDTO) {

        LocalDateTime startTime = bookingDTO.getStartTime();

        LocalDateTime endTime = bookingDTO.getEndTime();

        String roomCode = bookingDTO.getRoom().getRoomCode();

        if(isExistedBookingByTime(roomCode,startTime,endTime)){

            throw new DuplicateBookingException("This room has been booked by someone at the same period ! " +
                    "Please choose another period time");
        }

        Room room = roomRepository.findByRoomCode(roomCode);

        if(ObjectUtils.nullSafeEquals(RoomState.available,room.getState())){

            room.setState(RoomState.booked);

            roomRepository.save(room);

        }

        Booking bookingE = modelMapper.map(bookingDTO, Booking.class);

        return booking.save(bookingE);

    }

    @Override
    public Booking update(BookingDTO bookingDTO) {



        Booking bookingReturn = booking.save(modelMapper.map(bookingDTO,Booking.class));

        if(isExistedBookingByRoomCodeWithStatusAccepted(bookingDTO.getRoom().getRoomCode())){

            String roomCode = bookingDTO.getRoom().getRoomCode();

            Room room =  roomRepository.findByRoomCode(roomCode);

            room.setState(RoomState.available);

            roomRepository.save(room);

        }

        return bookingReturn;
    }

    @Override
    public void deleteById(Integer id) {

        Optional<Booking> bookingOptional = booking.findById(id);

        if(bookingOptional.isPresent()) {

            bookingOptional.get().setBookingState(BookingState.Canceled);

            Room room = bookingOptional.get().getRoom();

            room.setState(RoomState.available);

            roomRepository.save(room);

            booking.save(bookingOptional.get());
        }
    }

    @Override
    public boolean isExistedByBookingId(Integer id){
        return booking.existsById(id);
    }

    @Override
    public BookingDTO findById(Integer id) {
        Optional<Booking> bookingOptional = booking.findById(id);
        if( bookingOptional.isPresent())
          return modelMapper.map( bookingOptional.get(), BookingDTO.class);
        return null;
    }

    @Override
    public Page<BookingDTO> findAllAndSortingByStartTimeDescending(int pageNo,int pageSize,BookingState bookingState) {

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("startTime").descending());
        return booking.findAll(bookingState,pageable).map(bookingConvert -> modelMapper.map(bookingConvert,BookingDTO.class));
    }

    @Override
    public Page<BookingDTO> findAllAndSortingByStartTimeDescending(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("startTime").descending());
        return booking.findAll(pageable).map(bookingConvert ->modelMapper.map(bookingConvert,BookingDTO.class));
    }

    @Override
    public boolean isExistedBookingByTime(String roomCode, LocalDateTime startTime,LocalDateTime endTime) {
        return booking.isExistedBookingByTime(roomCode,startTime,endTime);
    }

    @Override
    public boolean isExistedBookingByRoomCodeWithStatusAccepted(String roomCode) {
        return booking.isExistedBookingByRoomCodeWithStatusAccepted(roomCode);
    }

    @Override
    public Page<BookingDTO> findAllbyStartTimeAndEndtimeOrBookingStateOrNote(LocalDateTime startTime,
                                                                             LocalDateTime endTime,
                                                                             BookingState bookingState,
                                                                             String note,
                                                                             int pageNo,
                                                                             int pageSize) {

        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by("startTime").descending());

        Page<Booking> bookingPage = booking
                .findAllByStartTimeAndEndTimeOrBookingStateOrNote(startTime,endTime,bookingState,note,pageable);

        return bookingPage.map(pageBooking ->modelMapper.map(pageBooking,BookingDTO.class));

    }
}
