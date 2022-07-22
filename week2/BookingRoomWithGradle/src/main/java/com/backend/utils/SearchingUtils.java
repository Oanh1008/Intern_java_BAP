package com.backend.utils;

import com.backend.enumeration.RoomType;
import com.backend.dto.RoomDTO;
import com.backend.service.RoomService;

import java.util.List;

public class SearchingUtils {
    private SearchingUtils() {
    }

    public static List<RoomDTO> searchAllRooms(RoomType roomType, Integer size, RoomService roomService) {
       List<RoomDTO> rooms = List.of();

        return rooms;
    }
}
