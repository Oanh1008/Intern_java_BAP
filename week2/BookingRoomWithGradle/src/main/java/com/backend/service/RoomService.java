package com.backend.service;

import com.backend.bean.Room;
import com.backend.bean.RoomType;
import com.backend.dto.RoomDTO;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<RoomDTO> getAllRooms();

    Room save(RoomDTO room);

    void deleteById(Integer id);

    RoomDTO findByRoomCode(String roomCode);

    RoomDTO findById(Integer id);

    Boolean isExistedById(Integer id);



    boolean isExistedByRoomCode(String roomCode);

    List<RoomDTO> searchAllBySizeBetweenAndRoomType(RoomType roomType, Integer quantity);

    List<RoomDTO> searchAllByRoomType(RoomType roomType);

    List<RoomDTO> searchAllByBetweenMinAndMax(Integer quantity);

   List<RoomDTO> findPaginated(int pageNo, int pageSize);
   long totolRecordsRoom();
}
