package com.backend.service;

import com.backend.bean.Room;
import com.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoomByID(String id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room getRoomByID(String id) {
        return roomRepository.findById(id).get();
    }
}
