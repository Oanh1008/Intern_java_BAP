package com.backend.commons;

import com.backend.enumeration.BookingState;
import com.backend.enumeration.RoomState;
import com.backend.enumeration.RoomType;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelAddAttributeCommon {
    private ModelAddAttributeCommon(){}

    public static void addRoomTypeAndRoomState(Model model){
        model.addAttribute("BASIC", RoomType.BASIC);
        model.addAttribute("VIP",RoomType.VIP);
        model.addAttribute("available", RoomState.available);
        model.addAttribute("booked",RoomState.booked);
    }
    public  static void addBookingState(Model model){
       List<BookingState> bookingStates = Arrays.stream(BookingState.values()).collect(Collectors.toList());
       model.addAttribute("bookingStates",bookingStates);
    }
    public static void addEachBookingState(Model model){
        model.addAttribute("canceled",BookingState.Canceled);
        model.addAttribute("completed",BookingState.Completed);
        model.addAttribute("processing",BookingState.Processing);
        model.addAttribute("accepted",BookingState.Accepted);
    }

}
