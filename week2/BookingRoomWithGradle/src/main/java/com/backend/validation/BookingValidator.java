package com.backend.validation;

import com.backend.config.Message;
import com.backend.dto.BookingDTO;
import com.backend.enumeration.RoomState;
import com.backend.service.RoomService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BookingValidator implements Validator {

    @Autowired
    private RoomService roomService;
    @Override
    public boolean supports(Class<?> clazz) {
        return BookingDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) target;

        ValidationUtils.rejectIfEmpty(errors,"startTime", Message.NOT_EMPTY);

        if(ObjectUtils.isNotEmpty(bookingDTO.getStartTime())
                && ObjectUtils.isNotEmpty(bookingDTO.getEndTime())
                && bookingDTO.getStartTime().isAfter(bookingDTO.getEndTime())){

            errors.rejectValue("startTime", "Booking.startTime");
        }

        ValidationUtils.rejectIfEmpty(errors,"endTime",Message.NOT_EMPTY);

        if(ObjectUtils.isNotEmpty(bookingDTO.getStartTime())
                && ObjectUtils.isNotEmpty(bookingDTO.getEndTime())
                && bookingDTO.getEndTime().isBefore(bookingDTO.getStartTime())){

            errors.rejectValue("endTime", "Booking.endTime");
        }

        ValidationUtils.rejectIfEmpty(errors,"quantity",Message.NOT_EMPTY);
        if(ObjectUtils.isNotEmpty(bookingDTO.getQuantity())
                && !(bookingDTO.getQuantity() >= bookingDTO.getRoom().getMin()
                && bookingDTO.getQuantity() <= bookingDTO.getRoom().getMax())
        ){
            errors.rejectValue("quantity","Booking.quantity");
        }
        ValidationUtils.rejectIfEmpty(errors,"room.roomCode",Message.NOT_EMPTY);
        if(ObjectUtils.isNotEmpty(bookingDTO.getRoom().getRoomCode())
                && !    roomService.isExistedByRoomCode(bookingDTO.getRoom().getRoomCode())){
            errors.rejectValue("room.roomCode","Booking.room.roomCode");
        }
    }
}
