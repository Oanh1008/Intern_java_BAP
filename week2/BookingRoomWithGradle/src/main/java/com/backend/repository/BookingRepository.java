package com.backend.repository;

import com.backend.bean.Booking;
import com.backend.enumeration.BookingState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("select b from Booking b where b.bookingState = ?1")
    Page<Booking> findAll(BookingState bookingState, Pageable pageable);

    @Query("select (count(b )> 0) from Booking  b where b.room.roomCode = ?1 " +
                         "and b.endTime > ?2 and b.startTime < ?3 and (b.bookingState = 'Accepted') ")
    boolean isExistedBookingByTime (String roomCode,LocalDateTime startTime, LocalDateTime endTime);

    @Query("select (count(b )> 0) from Booking  b where b.room.roomCode = ?1 " +
            "and b.endTime > ?2 and b.startTime < ?3 and (b.bookingState = 'Accepted') and b.id <> ?4 ")
    boolean isExistedBooking (String roomCode,LocalDateTime startTime, LocalDateTime endTime, Integer bookingId);

    @Query("select (count (b) = 0) from Booking b where b.room.roomCode = ?1 and b.bookingState = 'Accepted'")
    boolean isExistedBookingByRoomCode(String roomCode);



    @Query("select b from Booking b where ((:startTime is null and :endTime is null)"
                                            +" or ((b.startTime >= :startTime and b.endTime > :startTime) "
                                            +"and (b.endTime <= :endTime and b.startTime < :endTime)))"
                                            +"and (:bookingState is null or b.bookingState = :bookingState)"
                                            +"and (:note is null or b.note like concat('%',:note,'%') )"
                                            +"and (:roomCode is null or b.room.roomCode like concat('%',:roomCode,'%') )")
    Page<Booking> findAllByStartTimeAndEndTimeOrBookingStateOrNote(@Param("startTime") LocalDateTime startTime,
                                                                   @Param("endTime") LocalDateTime endTime,
                                                                   @Param("bookingState")  BookingState bookingState,
                                                                   @Param("note") String note,
                                                                   @Param("roomCode") String roomCode,
                                                                   Pageable pageable);

    @Query("select b from Booking b where b.userCode = :userCode"
                             +" and (:bookingState is null or b.bookingState = :bookingState)")
    Page<Booking> findBookingByUserCode(@Param("userCode") Integer userCode,
                                        @Param("bookingState") BookingState bookingState, Pageable pageable);



}
