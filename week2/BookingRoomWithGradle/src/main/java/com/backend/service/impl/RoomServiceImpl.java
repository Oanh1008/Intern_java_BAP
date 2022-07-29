package com.backend.service.impl;

import com.backend.bean.Room;
import com.backend.enumeration.RoomType;
import com.backend.dto.RoomDTO;
import com.backend.repository.RoomRepository;
import com.backend.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    @Autowired
    public RoomServiceImpl(ModelMapper modelMapper, RoomRepository roomRepository) {
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
    }

    @Override
    public Room save(RoomDTO room)  {

        Room roomE = modelMapper.map(room, Room.class);

        return roomRepository.save(roomE);

    }

    @Override
    public void deleteById(Integer id) {
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDTO findByRoomCode(String roomCode) {
        return modelMapper.map(roomRepository.findByRoomCode(roomCode), RoomDTO.class);
    }
    @Override
    public Room findByRoomCodeE(String roomCode) {
        return roomRepository.findByRoomCode(roomCode);
    }

    @Override
    public RoomDTO findById(Integer id) {
        return modelMapper.map(roomRepository.findById(id).get(), RoomDTO.class);
    }

    @Override
    public boolean isExistedByRoomCode(String roomCode) {
        return roomRepository.existsRoomByRoomCode(roomCode);
    }

    @Override
    public Page<RoomDTO> searchAllAndPagination(RoomType roomType, Integer size, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return roomRepository.searchAllAndPagination(roomType,size,pageable).map(room -> modelMapper.map(room,RoomDTO.class));
    }

    @Override
    public Optional<List<String>> getAllRoomCode() {
       return Optional.ofNullable(roomRepository.getAllRoomCode().orElse(null));
    }
}
