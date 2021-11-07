package com.home.ticket.service.WalmartTicketService.model;

import java.util.List;

public class SeatHold {
    int id;
    String email;
    long bookingTime;
    List<Seat> holdSeats;


    public SeatHold(int id, String email, long bookingTime, List<Seat> holdSeats) {
        this.id = id;
        this.email = email;
        this.bookingTime = bookingTime;
        this.holdSeats = holdSeats;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(long bookingTime) {
        this.bookingTime = bookingTime;
    }

    public List<Seat> getHoldSeats() {
        return holdSeats;
    }

    public void setHoldSeats(List<Seat> holdSeats) {
        this.holdSeats = holdSeats;
    }

//    @Override
//    public String toString() {
//        StringBuilder messageBuilder = new StringBuilder();
//        messageBuilder.append(String.format("Seat HOLD for [%s]", email));
//        messageBuilder.append(String.format(" [ID:%d]", id));
//        messageBuilder.append(" Seats[ ");
//        for (Seat seat : holdSeats) {
//            messageBuilder.append(String.format("{%d, %d}", seat.getRow(), seat.getNumber()));
//            messageBuilder.append(" ");
//        }
//        messageBuilder.append("]");
//        return messageBuilder.toString();
//    }
}
