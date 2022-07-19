package com.backend.service;

import com.backend.bean.Room;
import com.backend.bean.RoomType;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> getAllRooms();

    Room save(Room room);

    void deleteById(Integer id);

    Room findByRoomCode(String roomCode);

    Room findById(Integer id);

    Boolean isExistedById(Integer id);



    Boolean isExistedByRoomCode(String roomCode);

    List<Room> searchAllBySizeBetweenAndRoomType(RoomType roomType, Integer quantity);

    List<Room> searchAllByRoomType(RoomType roomType);

    List<Room> searchAllByBetweenMinAndMax(Integer quantity);

   List<Room> findPaginated(int pageNo, int pageSize);
   long totolRecordsRoom();
}
