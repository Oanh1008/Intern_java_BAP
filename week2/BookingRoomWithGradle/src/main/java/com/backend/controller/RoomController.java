package com.backend.controller;

import com.backend.bean.Room;
import com.backend.bean.RoomType;
import com.backend.service.RoomService;
import com.backend.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("room/showRooms/{page}")
    public String indexPage(Model model,
                            @RequestParam(name = "roomType",defaultValue = "") RoomType roomType,
                            @RequestParam(name = "size",defaultValue = "0") Integer size,
                            @RequestParam(name = "page",defaultValue = "0") Integer page
    ) {
        // Pagination
        int totalRecords = (int) roomService.totolRecordsRoom();
        int totalPages = totalRecords / 6 ;
        if( totalRecords % 6 >0){
            totalPages = (totalRecords / 6 ) + 1;
        }
        if( totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);
        }
        // Pagination
        // Search
        List<Room> rooms = roomService.findPaginated(page,6);
        if(roomType != null && size == 0){
            rooms = roomService.searchAllByRoomType(roomType);
        }else if(roomType == null && size != 0 ){
            rooms = roomService.searchAllByBetweenMinAndMax(size);
        }else if(roomType != null && size != 0){
            rooms = roomService.searchAllBySizeBetweenAndRoomType(roomType,size);
        }
        // Search



        model.addAttribute("rooms",rooms);

        return "index";
    }

    @GetMapping("/room/new")
    public String formRoom(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "room_add";
    }

    @PostMapping("/room/showRooms/{page}")
    public String addNewRoom(@ModelAttribute("room") Room room, RedirectAttributes re) {
        System.out.println(room);
        String rs = "";
        if (!roomService.isExistedByRoomCode(room.getRoomCode())) {
            rs = " Added the room " + room.getRoomCode() + " successful";
            re.addFlashAttribute("message", rs);
            roomService.save(room);
            return "redirect:/room/showRooms";
        }
        rs = "Updated the room " + room.getRoomCode() + " sucessful";
        re.addFlashAttribute("message", rs);
        roomService.save(room);
        return "redirect:/room/showRooms/{page}";
    }

    @GetMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable("id") Integer id, RedirectAttributes re) {
        Room room = roomService.findById(id);
        roomService.deleteById(id);
        String message = "delete " + room.getRoomCode() + " successful";
        re.addFlashAttribute("message", message);
        return "redirect:/room/showRooms";
    }

    @GetMapping("/room/edit/{id}")
    public String formEdit(Model model, @PathVariable("id") String roomCode) {
        Room room = roomService.findByRoomCode(roomCode);
        model.addAttribute("room", room);

        return "room_edit";
    }


}
