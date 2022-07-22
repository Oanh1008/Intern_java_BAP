package com.backend.utils;

import com.backend.enumeration.BookingState;
import com.backend.enumeration.RoomState;
import com.backend.enumeration.RoomType;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelAddAttributeUtils {
    private ModelAddAttributeUtils(){}

    public static void addRoomTypeAndRoomState(Model model){
        model.addAttribute("BASIC", RoomType.BASIC);
        model.addAttribute("VIP",RoomType.VIP);
        model.addAttribute("available", RoomState.available);
        model.addAttribute("booked",RoomState.booked);
    }
    public  static void addBookingState(Model model){
       List<String> bookingStates = Arrays.stream(BookingState.values()).map(b -> b.name()).collect(Collectors.toList());
       model.addAttribute("bookingStates",bookingStates);
    }

}
