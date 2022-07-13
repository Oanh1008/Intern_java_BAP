package com.backend.controller;


import com.backend.bean.Booking;
import com.backend.bean.Room;
import com.backend.service.BookingService;
import com.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@org.springframework.stereotype.Controller

public class RoomController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @GetMapping("room/showRooms")
    public String homePage(Model model) {
        List<Room> rooms = roomService.getAllRoom();

        model.addAttribute("rooms", rooms);
        return "index";
    }

    @GetMapping("room/new")
    public String formRooms(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "form_room";
    }

    @PostMapping("/room/showRooms")
    public String addNewRoom(@ModelAttribute("room") Room room,
                             @ModelAttribute("booking") Booking booking) {
        roomService.save(room);

        bookingService.save(booking);
        return "redirect:/room/showRooms";
    }

    @GetMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteRoomByID(id);
        return "redirect:/room/showRooms";

    }

    @GetMapping("/room/edit/{id}")
    public String editRoom(@PathVariable("id") String roomID, Model model) {
        Room room = roomService.getRoomByID(roomID);
        model.addAttribute("room", room);
        return "edit_room";
    }


}
