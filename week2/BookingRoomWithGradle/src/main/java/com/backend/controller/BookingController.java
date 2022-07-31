package com.backend.controller;

import com.backend.bean.Room;
import com.backend.dto.BookingDTO;
import com.backend.enumeration.BookingState;
import com.backend.exception.DuplicateBookingException;
import com.backend.service.BookingService;
import com.backend.service.RoomService;
import com.backend.commons.ModelAddAttributeCommon;
import com.backend.validation.BookingValidator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final BookingValidator bookingValidator;

    @Autowired
    public BookingController(BookingService bookingService,
                             RoomService roomService,
                             BookingValidator bookingValidator) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.bookingValidator = bookingValidator;
    }

    @GetMapping("/booking")
    public String bookingPage(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            @RequestParam(name = "startTime", defaultValue = "") LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            @RequestParam(name = "endTime", defaultValue = "") LocalDateTime endTime,
            @RequestParam(name = "bookingState", defaultValue = "") BookingState bookingState,
            @RequestParam(name = "note", defaultValue = "") String note,
            @RequestParam(name = "roomCode", defaultValue = "") String roomCode,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            Model model,
            RedirectAttributes reAtt) {
        String message = "";
        boolean flag = false;
        if (ObjectUtils.isEmpty(startTime) && ObjectUtils.isNotEmpty(endTime)) {
            message = "Must fill Start Time";
            flag = true;
        } else if (ObjectUtils.isEmpty(endTime) && ObjectUtils.isNotEmpty(startTime)) {
            message = "Must fill End Time";
            flag = true;
        }
        if (flag) {
            reAtt.addFlashAttribute("message", message);
            return "redirect:/booking";
        }
        page -= 1;
        Page<BookingDTO> pageBookingDTO = bookingService.findAllByCondition(startTime, endTime, bookingState, note,
                roomCode, page, pageSize);
        List<BookingDTO> bookingDTOs = pageBookingDTO.toList();
        int totalPages = pageBookingDTO.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("bookingState", bookingState);
        model.addAttribute("note", note);
        model.addAttribute("roomCode", roomCode);
        model.addAttribute("bookings", bookingDTOs);
        model.addAttribute("pageSize", pageSize);
        ModelAddAttributeCommon.addBookingState(model);
        ModelAddAttributeCommon.addEachBookingState(model);
        return "booking_index";
    }

    @GetMapping("/booking/cancel")
    public String bookingCancel(Model model) {
        Page<BookingDTO> pageBookingDTO = bookingService.findAllAndSortingByStartTimeDescending(0, 10,
                BookingState.Canceled);
        int totalPages = pageBookingDTO.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("bookings", pageBookingDTO.toList());
        return "booking_cancel";
    }

    @GetMapping("/booking/complete")
    public String bookingComplete(Model model) {
        Page<BookingDTO> pageBookingDTO = bookingService.findAllAndSortingByStartTimeDescending(0, 10,
                BookingState.Completed);
        List<BookingDTO> bookingDTOs = pageBookingDTO.toList();
        model.addAttribute("bookings", bookingDTOs);
        return "booking_complete";
    }

    @GetMapping("/booking/{roomCode}")
    public String bookingForm(@PathVariable("roomCode") String roomCode, Model model) {
        Room room = roomService.findByRoomCodeE(roomCode);
        BookingDTO booking = new BookingDTO();
        booking.setRoom(room);
        booking.setId(null);
        model.addAttribute("booking", booking);
        ModelAddAttributeCommon.addBookingState(model);
        ModelAddAttributeCommon.addEachBookingState(model);
        return "booking_add";
    }

    @PostMapping("/booking/save")
    public String saveBooking(@ModelAttribute("booking") BookingDTO booking, RedirectAttributes re,
                              BindingResult bindingResult) {
        bookingValidator.validate(booking, bindingResult);
        if (bindingResult.hasErrors()) {
            return "booking_add";
        }
        String message = "";
        try {
            bookingService.save(booking);
        } catch (DuplicateBookingException e) {
            message = e.getMessage();
        }
        re.addFlashAttribute("message", message);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if("user".equals(name)){
            return "redirect:/booking/showBooking/0";
        }
        return "redirect:/booking";
    }

    @PostMapping("/booking/update")
    public String updateBooking(@ModelAttribute("booking") BookingDTO booking, RedirectAttributes re,
                                BindingResult bindingResult, Model model) {
        bookingValidator.validate(booking, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAddAttributeCommon.addBookingState(model);
            return "booking_edit";
        }
        String message = "Update successful";

        try {
            bookingService.update(booking);
        }catch (DuplicateBookingException d){
            message = d.getMessage();
        }
         re.addFlashAttribute("message", message);
        return "redirect:/booking";
    }

    @GetMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable("id") Integer id) {
        bookingService.deleteById(id);
        return "redirect:/booking";
    }

    @GetMapping("/booking/edit/{id}")
    public String editBooking(@PathVariable("id") Integer id, Model model) {
        BookingDTO booking = bookingService.findById(id);
        model.addAttribute("booking", booking);
        ModelAddAttributeCommon.addBookingState(model);
        model.addAttribute("roomCodes",roomService.getAllRoomCode().get());
        return "booking_edit";
    }

    @GetMapping("/booking/showBooking/{userCode}")
    public String getAllBookingByUserCode(@PathVariable("userCode") Integer userCode,
                                          @RequestParam(name = "bookingState", defaultValue = "") BookingState bookingState,
                                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                          Model model) {
        page-=1;
        Page<BookingDTO> pageBookingDTO = bookingService.findBookingByUserCode(userCode, bookingState, page, pageSize);
        int totalPage = pageBookingDTO.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("bookings", pageBookingDTO.toList());
        model.addAttribute(userCode);
        ModelAddAttributeCommon.addEachBookingState(model);
        return "my_booking";
    }

}

