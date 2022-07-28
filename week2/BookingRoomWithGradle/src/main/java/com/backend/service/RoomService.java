package com.backend.service;

import com.backend.bean.Room;
import com.backend.enumeration.RoomType;
import com.backend.dto.RoomDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoomService {

    List<RoomDTO> getAllRooms();

    Room save(RoomDTO room);

    void deleteById(Integer id);

    RoomDTO findByRoomCode(String roomCode);
    Room findByRoomCodeE(String roomCode);

    RoomDTO findById(Integer id);

    Boolean isExistedById(Integer id);



    boolean isExistedByRoomCode(String roomCode);

    List<RoomDTO> searchAllBySizeBetweenAndRoomType(RoomType roomType, Integer quantity, Integer page, Integer pageSize);

    List<RoomDTO> searchAllByRoomType(RoomType roomType, Integer page, Integer pageSize);

    List<RoomDTO> searchAllByBetweenMinAndMax(Integer quantity,int page,int pageSize);

   Page<RoomDTO> findPaginated(int pageNo, int pageSize);
   long totolRecordsRoom();

    Page<RoomDTO> searchAllAndPagination(RoomType roomType, Integer size, int pageNo, int pageSize);
}
