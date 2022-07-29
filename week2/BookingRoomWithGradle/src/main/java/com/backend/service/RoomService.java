package com.backend.service;

import com.backend.bean.Room;
import com.backend.enumeration.RoomType;
import com.backend.dto.RoomDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface RoomService {

    Room save(RoomDTO room);

    void deleteById(Integer id);

    RoomDTO findByRoomCode(String roomCode);

    Room findByRoomCodeE(String roomCode);

    RoomDTO findById(Integer id);

    boolean isExistedByRoomCode(String roomCode);

    Page<RoomDTO> searchAllAndPagination(RoomType roomType, Integer size, int pageNo, int pageSize);

    Optional<List<String>> getAllRoomCode();
}
