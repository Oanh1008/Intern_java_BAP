package com.backend.validation;

import com.backend.dto.BookingDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BookingValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookingDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) target;

        ValidationUtils.rejectIfEmpty(errors,"startTime","NotEmpty");

        if(ObjectUtils.isNotEmpty(bookingDTO.getStartTime())
                && ObjectUtils.isNotEmpty(bookingDTO.getEndTime())
                && bookingDTO.getStartTime().isAfter(bookingDTO.getEndTime())){

            errors.rejectValue("startTime", "Booking.startTime");
        }

        ValidationUtils.rejectIfEmpty(errors,"endTime","NotEmpty");

        if(ObjectUtils.isNotEmpty(bookingDTO.getStartTime())
                && ObjectUtils.isNotEmpty(bookingDTO.getEndTime())
                && bookingDTO.getEndTime().isBefore(bookingDTO.getStartTime())){

            errors.rejectValue("endTime", "Booking.endTime");
        }

        ValidationUtils.rejectIfEmpty(errors,"quantity","NotEmpty");
        if(ObjectUtils.isNotEmpty(bookingDTO.getQuantity())
                && !(bookingDTO.getQuantity() >= bookingDTO.getRoom().getMin()
                && bookingDTO.getQuantity() <= bookingDTO.getRoom().getMax())
        ){
            errors.rejectValue("quantity","Booking.quantity");
        }
    }
}
