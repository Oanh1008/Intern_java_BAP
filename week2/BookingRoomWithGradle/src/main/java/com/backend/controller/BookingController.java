package com.backend.controller;

import com.backend.bean.Room;
import com.backend.dto.BookingDTO;
import com.backend.service.BookingService;
import com.backend.service.RoomService;
import com.backend.utils.ModelAddAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;


@Controller
public class BookingController {
    private final BookingService bookingService;

    private final RoomService roomService;

    @Autowired
    public BookingController(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;

        this.roomService = roomService;
    }

    @GetMapping("/booking")
    public String bookingPage(Model model) {
        model.addAttribute("bookings", bookingService.findAllBookings());
        return "booking_index";
    }

    @GetMapping("/booking/{roomCode}")
    public String bookingForm(@PathVariable("roomCode") String roomCode, Model model) {
        Room room = roomService.findByRoomCodeE(roomCode);
        BookingDTO booking = new BookingDTO();
        booking.setRoom(room);
        model.addAttribute("booking", booking);
        ModelAddAttributeUtils.addBookingState(model);
        return "booking_add";
    }

    @PostMapping("/booking/save")
    public String saveBooking(@ModelAttribute("booking") BookingDTO booking) {
        bookingService.save(booking);
        return "redirect:/booking";
    }

    @GetMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable("id") Integer id) {
        bookingService.deleteById(id);
        return "redirect:/booking";
    }
    @GetMapping("/booking/edit/{id}")
    public String editBooking(@PathVariable("id") Integer id,Model model){
        BookingDTO booking = bookingService.findById(id);
        model.addAttribute("booking",booking);
        ModelAddAttributeUtils.addBookingState(model);
        return "booking_edit";
    }
}
