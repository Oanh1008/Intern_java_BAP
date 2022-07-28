package com.backend.repository;
import com.backend.bean.Room;
import com.backend.dto.RoomDTO;
import com.backend.enumeration.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("select r from Room r")
    @Override
    List<Room> findAll();

    @Query("select r from Room r where r.roomCode = ?1")
    Room findByRoomCode(String roomCode);

    @Query("select (count(r) > 0) from Room r where r.roomCode = ?1")
    boolean existsRoomByRoomCode(String roomCode);

    @Query("select r from Room r where r.roomType = ?1 and r.min <= ?2 and r.max >= ?2")
    Optional<List<Room>> searchAllBySizeBetweenAndRoomType(RoomType roomType, Integer quantity,Pageable pageable);

    @Query("select r from Room r where r.roomType = ?1")
    List<Room> searchAllByRoomType(RoomType roomTypem,Pageable pageable);


    @Query("select r from Room r where r.min <= ?1 and r.max >= ?1")
    List<Room> searchAllByBetweenMinAndMax(Integer quantity ,Pageable pageable);

    @Query("select r from Room r where (r.roomType = ?1 or ?1 is null) and(( r.min <=?2 and r.max >=?2 ) or ?2 =0) ")
    Page<Room> searchAllAndPagination(RoomType roomType, Integer size, Pageable pageable);



}
