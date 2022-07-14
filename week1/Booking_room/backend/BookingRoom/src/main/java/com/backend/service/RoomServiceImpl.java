package com.backend.service;

import com.backend.bean.Room;
import com.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            return room.get();
        }
        return null;
    }
}
