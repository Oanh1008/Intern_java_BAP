package com.backend.service;

import com.backend.bean.Room;
import com.backend.bean.RoomType;
import com.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {

        return roomRepository.save(room);
    }

    @Override
    public void deleteById(Integer id) {
        roomRepository.deleteById(id);
    }
    @Override
    public Room findByRoomCode(String roomCode) {
        return roomRepository.findByRoomCode(roomCode);
    }

    @Override
    public Room findById(Integer id) {
        return roomRepository.findById(id).get();
    }

    @Override
    public Boolean isExistedById(Integer id) {
        return roomRepository.existsById(id);
    }



    @Override
    public Boolean isExistedByRoomCode(String  roomCode) {
        return roomRepository.existsRoomByRoomCode(roomCode);
    }

    @Override
    public List<Room> searchAllBySizeBetweenAndRoomType(RoomType roomType, Integer quantity) {
        System.out.println(roomRepository + " " +roomType +" "+quantity);
        Optional<List<Room>> rooms = roomRepository.searchAllBySizeBetweenAndRoomType(roomType,quantity);
        if(rooms.isPresent()){
            return rooms.get();
        }
        return null;
    }

    @Override
    public List<Room> searchAllByRoomType(RoomType roomType) {
        return roomRepository.searchAllByRoomType(roomType);
    }

    @Override
    public List<Room> searchAllByBetweenMinAndMax(Integer quantity) {
        return roomRepository.searchAllByBetweenMinAndMax(quantity);
    }

    @Override
    public List<Room> findPaginated(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo,pageSize);

        Page<Room>  pageResult = roomRepository.findAll(paging);
        return pageResult.toList();
    }

    @Override
    public long totolRecordsRoom() {
        return roomRepository.count();
    }
}
