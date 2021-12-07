package com.upgrad.booking.controller;

import com.upgrad.booking.dto.BookingDTO;
import com.upgrad.booking.entities.Booking;
import com.upgrad.booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(path = "/bookings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO newBooking = bookingService.CreateBooking(bookingDTO);

        return new ResponseEntity(newBooking, HttpStatus.CREATED);
    }
}
