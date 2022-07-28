package com.backend.validation;

import com.backend.dto.RoomDTO;
import com.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class RoomValidator implements Validator {

    @Autowired
    private RoomService roomService;
    @Override
    public boolean supports(Class<?> clazz) {
        return RoomDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RoomDTO room = (RoomDTO) target;

        ValidationUtils.rejectIfEmpty(errors,"roomCode","NotEmpty" );
        if(roomService.isExistedByRoomCode(room.getRoomCode())){
            errors.rejectValue("roomCode","Room.roomCode.duplicate");
        }

        ValidationUtils.rejectIfEmpty(errors,"min","NotEmpty" );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("min","Room.min");
        }

        ValidationUtils.rejectIfEmpty(errors,"max","NotEmpty" );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("max","Room.max");
        }

        ValidationUtils.rejectIfEmpty(errors,"price","NotEmpty" );

        ValidationUtils.rejectIfEmpty(errors,"image","NotEmpty" );

    }
    public void validateForEditRoom(Object target, Errors errors) {

        RoomDTO room = (RoomDTO) target;

        ValidationUtils.rejectIfEmpty(errors,"min","NotEmpty" );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("min","Room.min");
        }

        ValidationUtils.rejectIfEmpty(errors,"max","NotEmpty" );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("max","Room.max");
        }

        ValidationUtils.rejectIfEmpty(errors,"price","NotEmpty" );

        ValidationUtils.rejectIfEmpty(errors,"image","NotEmpty" );

    }
}
