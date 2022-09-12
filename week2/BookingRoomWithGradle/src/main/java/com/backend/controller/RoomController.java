package com.backend.controller;

import com.backend.dto.BookingDTO;
import com.backend.enumeration.BookingState;
import com.backend.enumeration.RoomType;
import com.backend.config.EndPoints;
import com.backend.config.Form;
import com.backend.config.Message;
import com.backend.dto.RoomDTO;
import com.backend.service.BookingService;
import com.backend.service.RoomService;
import com.backend.commons.ModelAddAttributeCommon;
import com.backend.validation.RoomValidator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    private final BookingService bookingService;
    private final RoomValidator roomValidator;

    @Autowired
    public RoomController(RoomService roomService, RoomValidator roomValidator, BookingService bookingService) {
        this.roomService = roomService;
        this.roomValidator = roomValidator;
        this.bookingService = bookingService;
    }

    @GetMapping(EndPoints.DEFAULT_PAGE)

    public String indexPage(Model model,
                            @RequestParam(name = "roomType", defaultValue = "") RoomType roomType,
                            @RequestParam(name = "size", defaultValue = "0") Integer size,
                            @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
                            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        // Pagination start
        if (pageSize < 0) {
            pageSize = 6;
        }
        page -= 1;
        Page<RoomDTO> pageRoomDTO = roomService.searchAllAndPagination(roomType, size, page, pageSize);
        int totalPages = pageRoomDTO.getTotalPages();
        List<RoomDTO> rooms = pageRoomDTO.toList();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int isAdmin = 1;
        if ("admin".equals(name)) {
            model.addAttribute("userCode", isAdmin);
        } else {
            isAdmin = 0;
            model.addAttribute("userCode", isAdmin);
        }
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("roomType", roomType);
        model.addAttribute("size", size);
        model.addAttribute("rooms", rooms);
        model.addAttribute("totalRecords", rooms.size());
        ModelAddAttributeCommon.addRoomTypeAndRoomState(model);
        return "index";
    }

    @GetMapping(EndPoints.NEW_ROOM)
    public String formRoom(Model model) {
        RoomDTO room = new RoomDTO();
        room.setId(null);
        model.addAttribute("room", room);
        ModelAddAttributeCommon.addRoomTypeAndRoomState(model);
        return Form.FORM_ADD;
    }

    @PostMapping(EndPoints.SAVE_ROOM)
    public String save(@Valid @ModelAttribute("room") RoomDTO room, RedirectAttributes re, BindingResult bindingResult, Model model) {
        roomValidator.validate(room, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAddAttributeCommon.addRoomTypeAndRoomState(model);
            return Form.FORM_ADD;
        }
        log.info("room: {} ", room);
        String roomCode = room.getRoomCode();
        roomService.save(room);
        String message = StringUtils.join(Message.ADD, roomCode, Message.SUCCESS);
        re.addFlashAttribute("message", message);
        return EndPoints.REDIRECT_PAGE_DEFAULT;
    }

    @PostMapping("room/edit")
    public String edit(@Valid @ModelAttribute("room") RoomDTO room, RedirectAttributes re, BindingResult bindingResult, Model model) {
        roomValidator.validateForEditRoom(room, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAddAttributeCommon.addRoomTypeAndRoomState(model);
            return Form.FORM_EDIT;
        }
        String roomCode = room.getRoomCode();
        roomService.save(room);
        String message = StringUtils.join(Message.UPDATE, roomCode, Message.SUCCESS);
        re.addFlashAttribute("message", message);
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
        ModelAddAttributeCommon.addRoomTypeAndRoomState(model);
        return Form.FORM_EDIT;
    }

    @GetMapping("room/booked/{roomCode}")
    public String bookingOfRoom(@PathVariable("roomCode") String roomCode, Model model) {
        List<BookingDTO> bookings = bookingService
                .bookingsOfRoom(roomCode)
                .stream()
                .filter(bookingDTO -> bookingDTO.getBookingState().equals(BookingState.Accepted))
                .collect(Collectors.toList());
        model.addAttribute("bookings", bookings);
        model.addAttribute("roomCode", roomCode);
        return "booking_of_room";
    }
}
