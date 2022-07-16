package com.backend.bookingroom.repository;

import com.backend.bookingroom.bean.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {
}
