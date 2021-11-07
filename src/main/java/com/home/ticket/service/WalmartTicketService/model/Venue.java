package com.home.ticket.service.WalmartTicketService.model;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    List<Seat> seats;
    int holdDuration;

    /**
     * Initialize venue with defaults: 5 rows and 20 seats
     */
    public Venue() {
        this.seats = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 20; j++) {
                this.seats.add(new Seat(i, j));
            }
        }
        this.holdDuration = 10;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getHoldDuration() {
        return holdDuration;
    }

    public void setHoldDuration(int holdDuration) {
        this.holdDuration = holdDuration;
    }
}
