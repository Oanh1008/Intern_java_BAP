package com.backend.service;

import com.backend.bean.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();

    Room save(Room room);
}
