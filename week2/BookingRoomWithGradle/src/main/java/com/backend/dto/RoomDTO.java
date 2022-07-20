package com.backend.dto;

import com.backend.bean.RoomType;
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
    private String state;
    private String description;
    private String image;
}
