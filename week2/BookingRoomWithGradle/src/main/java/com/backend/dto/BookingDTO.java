package com.backend.dto;

import com.backend.enumeration.BookingState;
import com.backend.bean.Room;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    private String note;
    private BookingState bookingState;

}
