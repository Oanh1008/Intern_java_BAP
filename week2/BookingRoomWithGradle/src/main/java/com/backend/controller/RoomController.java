package com.backend.controller;

import com.backend.bean.Room;
import com.backend.bean.RoomType;
import com.backend.config.EndPoints;
import com.backend.config.Form;
import com.backend.config.Message;
import com.backend.dto.RoomDTO;
import com.backend.service.RoomService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class RoomController {
    private static Logger log = LoggerFactory.getLogger(RoomController.class);
    @Autowired
    private RoomService roomService;

    @GetMapping(EndPoints.DEFAULT_PAGE)
    public String indexPage(Model model,
                            @RequestParam(name = "roomType", defaultValue = "") RoomType roomType,
                            @RequestParam(name = "size", defaultValue = "0") Integer size,
                            @PathVariable(name = "page") Integer page) {
        // Pagination
        int totalRecords = (int) roomService.totolRecordsRoom();
        int totalPages = totalRecords / 6;
        if (totalRecords % 6 > 0) {
            totalPages = (totalRecords / 6) + 1;
        }
        page -= 1;
        List<RoomDTO> rooms = roomService.findPaginated(page, 6);
        // Pagination
        // Search
        // todo
        if (roomType != null && size == 0) {
            rooms = roomService.searchAllByRoomType(roomType);
            totalPages = rooms.size() / 6;
            if (rooms.size() % 6 > 0) {
                totalPages = (rooms.size() / 6) + 1;
            }
        } else if (roomType == null && size != 0) {
            rooms = roomService.searchAllByBetweenMinAndMax(size);
            totalPages = rooms.size() / 6;
            if (rooms.size() % 6 > 0) {
                totalPages = (rooms.size() / 6) + 1;
            }
        } else if (roomType != null) {
            rooms = roomService.searchAllBySizeBetweenAndRoomType(roomType, size);
            totalPages = rooms.size() / 6;
            if (rooms.size() % 6 > 0) {
                totalPages = (rooms.size() / 6) + 1;
            }
        }
        // Search
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("rooms", rooms);
        return "index";
    }
    @GetMapping(EndPoints.NEW_ROOM)
    public String formRoom(Model model) {
        RoomDTO room = new RoomDTO();
        model.addAttribute("room", room);
        return Form.FORM_ADD;
    }
    @PostMapping(EndPoints.SAVE_ROOM)
    public String addNewRoom(@ModelAttribute("room") RoomDTO room, RedirectAttributes re) { // DTO -> Entity
        log.info("room: {} ", room);
        String result = "";
        if (!roomService.isExistedByRoomCode(room.getRoomCode())) {
            result = StringUtils.join(Message.ADD, room.getRoomCode(), Message.SUCCESS);
        } else {
            result = StringUtils.join(Message.UPDATE, room.getRoomCode(), Message.SUCCESS);
        }
        roomService.save(room);
        re.addFlashAttribute("message", result);
        return EndPoints.REDIRECT_PAGE_DEFAULT;
    }
    @GetMapping(EndPoints.DELETE_ROOM)
    public String deleteRoom(@PathVariable("id") Integer id, RedirectAttributes re) {
        RoomDTO room = roomService.findById(id);
        roomService.deleteById(id);
        String message = StringUtils.join(Message.DELETE, room.getRoomCode() , Message.SUCCESS);
        re.addFlashAttribute("message", message);
        return EndPoints.REDIRECT_PAGE_DEFAULT;
    }
    @GetMapping(EndPoints.EDIT_ROOM)
    public String formEdit(Model model, @PathVariable("id") String roomCode) {
        RoomDTO room = roomService.findByRoomCode(roomCode);
        model.addAttribute("room", room);
        return Form.FORM_EDIT;
    }
}
