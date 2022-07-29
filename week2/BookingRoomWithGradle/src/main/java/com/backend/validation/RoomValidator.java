package com.backend.validation;

import com.backend.config.Message;
import com.backend.dto.RoomDTO;
import com.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class RoomValidator implements Validator {
    private final RoomService roomService;
    @Autowired
    public RoomValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomDTO room = (RoomDTO) target;
        ValidationUtils.rejectIfEmpty(errors,"roomCode", Message.NOT_EMPTY);
        if(roomService.isExistedByRoomCode(room.getRoomCode())){
            errors.rejectValue("roomCode","Room.roomCode.duplicate");
        }
        ValidationUtils.rejectIfEmpty(errors,"min",Message.NOT_EMPTY );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("min","Room.min");
        }
        ValidationUtils.rejectIfEmpty(errors,"max",Message.NOT_EMPTY );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("max","Room.max");
        }
        ValidationUtils.rejectIfEmpty(errors,"price",Message.NOT_EMPTY );
        ValidationUtils.rejectIfEmpty(errors,"image",Message.NOT_EMPTY);
    }

    public void validateForEditRoom(Object target, Errors errors) {
        RoomDTO room = (RoomDTO) target;
        ValidationUtils.rejectIfEmpty(errors,"min",Message.NOT_EMPTY );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("min","Room.min");
        }
        ValidationUtils.rejectIfEmpty(errors,"max",Message.NOT_EMPTY );
        if( room.getMin() != null && room.getMax()!=null&& room.getMin() >= room.getMax()){
            errors.rejectValue("max","Room.max");
        }
        ValidationUtils.rejectIfEmpty(errors,"price",Message.NOT_EMPTY);
        ValidationUtils.rejectIfEmpty(errors,"image",Message.NOT_EMPTY);
    }
}
