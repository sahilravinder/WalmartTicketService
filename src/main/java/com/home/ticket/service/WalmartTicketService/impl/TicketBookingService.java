package com.home.ticket.service.WalmartTicketService.impl;

import com.home.ticket.service.WalmartTicketService.interfaces.TicketService;
import com.home.ticket.service.WalmartTicketService.model.Seat;
import com.home.ticket.service.WalmartTicketService.model.SeatHold;
import com.home.ticket.service.WalmartTicketService.model.Venue;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TicketBookingService implements TicketService {
    private Venue venue = new Venue();
    private AtomicInteger seatHoldId = new AtomicInteger(1001);
    private Map<Integer, SeatHold> heldSeats = new HashMap<>();

    /**
     * @return
     */
    @Override
    public int numSeatsAvailable() {
        expireSeats();
        return (int) this.venue.getSeats().stream().filter(x -> !x.isReserved() && !x.isHold()).count();
    }

    /**
     * @return
     */
    @Override
    public String seatMap() {
        expireSeats();
        StringBuilder seatMapBuilder = new StringBuilder();
        seatMapBuilder.append(String.format("Seats Available: %d \n", numSeatsAvailable()));
        int row = 1;
        for (Seat seat : this.venue.getSeats()) {
            if (seat.getRow() > row) {
                seatMapBuilder.append("\n");
                row = seat.getRow();
            }
            seatMapBuilder.append(seat.toString());
        }
        return seatMapBuilder.toString();
    }

    /**
     * @param numSeats      the number of seats to find and hold
     * @param customerEmail unique identifier for the customer * @return a SeatHold object identifying the specific seats and related information
     * @return
     */
    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        expireSeats(); //Release older held seats
        if(numSeats > this.numSeatsAvailable())
            return null;

        long bookingTime = Instant.now().getEpochSecond();
        int holdId = seatHoldId.incrementAndGet();
        List<Seat> holdSeats = new ArrayList<>();
        for (Seat seat : this.venue.getSeats()) {
            if (!seat.isHold() && !seat.isReserved() && numSeats > 0) {
                seat.setHold(true);
                holdSeats.add(seat);
                numSeats--;
            }
        }
        SeatHold seatHold = new SeatHold(holdId, customerEmail, bookingTime, holdSeats);
        heldSeats.put(holdId, seatHold);
        return seatHold;
    }

    /**
     * @param seatHoldId    the seat hold identifier
     * @param customerEmail the email address of the customer to which the seat hold is assigned
     * @return
     */
    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        if (!seatsExpired(seatHoldId)) {
            SeatHold seatHold = heldSeats.getOrDefault(seatHoldId, null);
            if (seatHold != null) { //handle null exception
                //Release hold on seats
                for (Seat seat : seatHold.getHoldSeats()) {
                    Optional<Seat> any = this.venue.getSeats().stream().filter(x -> x.getNumber() == seat.getNumber() && x.getRow() == seat.getRow()).findAny();
                    if (any.isPresent() && any.get().isHold()) {
                        any.get().setHold(false);
                        any.get().setReserved(true);
                    }
                }
                heldSeats.remove(seatHoldId);
                return "Booking confirmed for Reservation# " + seatHoldId;
            }
            return String.format("SeatHold ID: %d doesn't exist, please try again", seatHoldId);
        }
        return String.format("SeatHold %d expired after duration of 10 seconds", seatHoldId);
    }

    /**
     *
     */
    private void expireSeats() {
        for (Map.Entry<Integer, SeatHold> hold : heldSeats.entrySet()) {
            verifySeatsExpired(hold.getKey(), hold.getValue());
        }
    }

    /**
     * @param seatHoldId
     * @return
     */
    private boolean seatsExpired(int seatHoldId) {
        SeatHold seatHold = heldSeats.getOrDefault(seatHoldId, null);
        if (seatHold != null) { //handle null exception
            return verifySeatsExpired(seatHoldId, seatHold);
        }
        return false;
    }

    private boolean verifySeatsExpired(int seatHoldId, SeatHold seatHold) {
        long now = Instant.now().getEpochSecond();
        long duration = now - seatHold.getBookingTime();
        if (duration > 10) {
            //Release hold on seats
            for (Seat seat : seatHold.getHoldSeats()) {
                Optional<Seat> any = this.venue.getSeats().stream().filter(x -> x.getNumber() == seat.getNumber() && x.getRow() == seat.getRow()).findAny();
                if (any.isPresent() && any.get().isHold()) {
                    any.get().setHold(false);
                }
            }
            heldSeats.remove(seatHoldId); //Remove from map
            return true;
        }
        return false;
    }
}
