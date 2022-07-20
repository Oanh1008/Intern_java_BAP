package com.backend.service;

import com.backend.bean.Room;
import com.backend.bean.RoomType;
import com.backend.dto.RoomDTO;
import com.backend.repository.RoomRepository;
import com.backend.utils.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Room save(RoomDTO room) {
        return roomRepository.save(modelMapper.map(room, Room.class));
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
    public RoomDTO findById(Integer id) {
        return modelMapper.map(roomRepository.findById(id).get(), RoomDTO.class);
    }

    @Override
    public Boolean isExistedById(Integer id) {
        return roomRepository.existsById(id);
    }


    @Override
    public boolean isExistedByRoomCode(String roomCode) {
        return roomRepository.existsRoomByRoomCode(roomCode);
    }

    @Override
    public List<RoomDTO> searchAllBySizeBetweenAndRoomType(RoomType roomType, Integer quantity) {
        System.out.println(roomRepository + " " + roomType + " " + quantity);
        Optional<List<Room>> rooms = roomRepository.searchAllBySizeBetweenAndRoomType(roomType, quantity);
        if (rooms.isPresent()) {
            return rooms.get().stream()
                    .map(r -> modelMapper.map(r, RoomDTO.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<RoomDTO> searchAllByRoomType(RoomType roomType) {
        return MapperUtils.mapperListToListDTO(roomRepository.searchAllByRoomType(roomType),RoomDTO.class,modelMapper);
    }

    @Override
    public List<RoomDTO> searchAllByBetweenMinAndMax(Integer quantity) {
        return MapperUtils.mapperListToListDTO(roomRepository.searchAllByBetweenMinAndMax(quantity), RoomDTO.class, modelMapper);
    }

    @Override
    public List<RoomDTO> findPaginated(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Room> pageResult = roomRepository.findAll(paging);
        return MapperUtils.mapperListToListDTO(pageResult.toList(),RoomDTO.class,modelMapper);
    }

    @Override
    public long totolRecordsRoom() {
        return roomRepository.count();
    }
}
