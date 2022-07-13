package com.backend.service;

import com.backend.bean.Room;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoomService {
    List<Room> getAllRoom();

    Room save(Room room);

    void deleteRoomByID(String id);

    Room getRoomByID(String id);
}
