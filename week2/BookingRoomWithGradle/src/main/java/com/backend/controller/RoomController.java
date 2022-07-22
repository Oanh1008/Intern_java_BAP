package com.backend.controller;

import com.backend.enumeration.RoomType;
import com.backend.config.EndPoints;
import com.backend.config.Form;
import com.backend.config.Message;
import com.backend.dto.RoomDTO;
import com.backend.service.RoomService;
import com.backend.utils.ModelAddAttributeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class RoomController {
    private static final int DEFAULT_PAGE_SIZE = 6;
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(EndPoints.DEFAULT_PAGE)
    public String indexPage(Model model,
                            @RequestParam(name = "roomType", defaultValue = "") RoomType roomType,
                            @RequestParam(name = "size", defaultValue = "0") Integer size,
                            @PathVariable(name = "page") Integer page) {
        // Pagination start
        page -= 1;
        Page<RoomDTO> pageRoomDTO = roomService.findPaginated(page,DEFAULT_PAGE_SIZE);
        int totalRecords = (int) pageRoomDTO.getTotalElements();
        int totalPages = pageRoomDTO.getTotalPages();
        List<RoomDTO> rooms = pageRoomDTO.toList();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        // Pagination end
        // todo search and pagination start
        if (ObjectUtils.isNotEmpty(roomType) && size == 0) {
            rooms = roomService.searchAllByRoomType(roomType);
        } else if (ObjectUtils.isEmpty(roomType) && size != 0) {
            rooms = roomService.searchAllByBetweenMinAndMax(size);
        } else if (ObjectUtils.isNotEmpty(roomType)) {
            rooms = roomService.searchAllBySizeBetweenAndRoomType(roomType, size);
        }
        // todo search and pagination end
        model.addAttribute("rooms", rooms);
        model.addAttribute("totalRecords",rooms.size());
        ModelAddAttributeUtils.addRoomTypeAndRoomState(model);
        return "index";
    }

    @GetMapping(EndPoints.NEW_ROOM)
    public String formRoom(Model model) {
        RoomDTO room = new RoomDTO();
        model.addAttribute("room", room);
        ModelAddAttributeUtils.addRoomTypeAndRoomState(model);
        return Form.FORM_ADD;
    }

    @PostMapping(EndPoints.SAVE_ROOM)
    public String saveOrUpdate(@ModelAttribute("room") RoomDTO room, RedirectAttributes re) { // DTO -> Entity
        log.info("room: {} ", room);
        String roomCode = room.getRoomCode();
        String result = "";
        if (!roomService.isExistedByRoomCode(room.getRoomCode())) {
            result = StringUtils.join(Message.ADD, roomCode, Message.SUCCESS);
        } else {
            result = StringUtils.join(Message.UPDATE, roomCode, Message.SUCCESS);
        }
        try {
            roomService.save(room);
        } catch (Exception e) {
            result = StringUtils.join(Message.ACTION, Message.NOT_SUCCESS,e.getMessage());
        }
        re.addFlashAttribute("message", result);
        return EndPoints.REDIRECT_PAGE_DEFAULT;
    }

    @GetMapping(EndPoints.DELETE_ROOM)
    public String deleteRoom(@PathVariable("id") Integer id, RedirectAttributes re) {
        String roomCode = roomService.findById(id).getRoomCode();
        roomService.deleteById(id);
        String message = StringUtils.join(Message.DELETE, roomCode, Message.SUCCESS);
        re.addFlashAttribute("message", message);
        return EndPoints.REDIRECT_PAGE_DEFAULT;
    }

    @GetMapping(EndPoints.EDIT_ROOM)
    public String formEdit(Model model, @PathVariable("id") String roomCode) {
        RoomDTO room = roomService.findByRoomCode(roomCode);
        model.addAttribute("room", room);
        ModelAddAttributeUtils.addRoomTypeAndRoomState(model);
        return Form.FORM_EDIT;
    }
}
