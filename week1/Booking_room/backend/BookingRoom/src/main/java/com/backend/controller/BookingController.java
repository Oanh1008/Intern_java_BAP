package com.backend.controller;

import com.backend.bean.Booking;
import com.backend.service.BookingService;
import com.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @GetMapping("room/booking/{id}")
    public String booking(@PathVariable("id") String roomID, Model model){
        Booking booking = new Booking();
        booking.setRoom(roomService.getRoomByID(roomID));
        model.addAttribute("booking",booking);
        return "booking";
    }

}
