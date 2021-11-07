package com.home.ticket.service.WalmartTicketService.model;

public class Customer {

    Seat hold;
    Seat booked;
    long reservationTime;
    long holdTime;

    public Seat getHold() {
        return hold;
    }

    public void setHold(Seat hold) {
        this.hold = hold;
    }

    public Seat getBooked() {
        return booked;
    }

    public void setBooked(Seat booked) {
        this.booked = booked;
    }

    public long getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(long reservationTime) {
        this.reservationTime = reservationTime;
    }

    public long getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(long holdTime) {
        this.holdTime = holdTime;
    }
}
