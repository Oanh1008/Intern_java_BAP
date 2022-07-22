package com.backend.service;

import com.backend.bean.Booking;
import com.backend.bean.Room;
import com.backend.dto.BookingDTO;
import com.backend.enumeration.RoomState;
import com.backend.repository.BookingRepository;
import com.backend.repository.RoomRepository;
import com.backend.utils.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return MapperUtils.mapperListToListDTO(booking.findAll(), BookingDTO.class, modelMapper);
    }

    @Override
    public Booking save(BookingDTO bookingDTO) {
        String roomCode = bookingDTO.getRoom().getRoomCode();
        Room room = roomRepository.findByRoomCode(roomCode);
        room.setState(RoomState.booked);
        roomRepository.save(room);
        Booking bookingE = modelMapper.map(bookingDTO, Booking.class);
        return booking.save(bookingE);
    }
    @Override
    public void deleteById(Integer id) {
        booking.deleteById(id);
    }
    @Override
    public boolean isExistedByBookingId(Integer id){
        return booking.existsById(id);
    }

    @Override
    public BookingDTO findById(Integer id) {
       return modelMapper.map(  booking.findById(id).get(), BookingDTO.class);
    }
}
