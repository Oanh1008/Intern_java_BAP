package com.backend.dto;

import com.backend.enumeration.BookingState;
import com.backend.bean.Room;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingDTO {
    private Integer id;
    private Room room;
    private Integer userCode;
    private Integer quantity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String note;
    private BookingState bookingState;
    public LocalDateTime toLocalDateTime(String localDatetime){

        return LocalDateTime.parse(localDatetime);
    }
    public void setStartTime(String startTime) {
        this.startTime = toLocalDateTime(startTime);
    }
    public void setEndTime(String endTime) {
        this.endTime = toLocalDateTime(endTime);
    }

    public String getStartTime() {
        return startTime.toString();
    }

    public String getEndTime() {
        return endTime.toString();
    }
}
