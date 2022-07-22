package com.backend.dto;

import com.backend.enumeration.RoomState;
import com.backend.enumeration.RoomType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomDTO {
    private Integer id;
    private String roomCode;
    private Integer min;
    private Integer max;
    private Double price;
    private RoomType roomType;
    private RoomState state;
    private String description;
    private String image;

}
