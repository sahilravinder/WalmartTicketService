package com.home.ticket.service.WalmartTicketService.controllers;

import com.home.ticket.service.WalmartTicketService.impl.TicketBookingService;
import com.home.ticket.service.WalmartTicketService.model.SeatHold;
import com.home.ticket.service.WalmartTicketService.payload.BookingRequest;
import com.home.ticket.service.WalmartTicketService.payload.HoldRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketBookingController {

    @Autowired
    TicketBookingService ticketBookingService;

    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "seatMap")
    @ResponseBody
    public ResponseEntity seatMap() {
        try {
            return ResponseEntity.ok(ticketBookingService.seatMap());
        } catch (Exception e) {
            System.out.println("API ERROR " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "numSeatsAvailable")
    @ResponseBody
    public ResponseEntity numSeatsAvailable() {
        try {
            int numSeats = ticketBookingService.numSeatsAvailable();
            return ResponseEntity.ok(numSeats);
        } catch (Exception e) {
            System.out.println("API ERROR " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, value = "findAndHoldSeats", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findAndHoldSeats(@RequestBody HoldRequest userInput) {
        try {
            SeatHold seatHold = ticketBookingService.findAndHoldSeats(userInput.getNumSeats(), userInput.getCustomerEmail());
            return ResponseEntity.ok(seatHold);
        } catch (Exception e) {
            System.out.println("API ERROR " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, value = "reserveSeats", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity reserveSeats(@RequestBody BookingRequest userInput) {
        try {
            String bookingId = ticketBookingService.reserveSeats(userInput.getSeatHoldId(), userInput.getCustomerEmail());
            return ResponseEntity.ok(bookingId);
        } catch (Exception e) {
            System.out.println("API ERROR " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
