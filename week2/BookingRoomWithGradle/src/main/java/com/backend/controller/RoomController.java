package com.backend.controller;

import com.backend.bean.Room;
import com.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("room/showRooms")
    public String indexPage(Model model){
        model.addAttribute("rooms",roomService.getAllRooms());
        return "index";
    }

    @GetMapping("/room/new")
    public String formRoom(Model model){
        Room  room = new Room();
        model.addAttribute("room",room);
        return "form_room";
    }

    @PostMapping("/room/showRooms")
    public String addNewRoom(@ModelAttribute("room") Room room){
        roomService.save(room);
        return "redirect:/room/showRooms";
    }
}
