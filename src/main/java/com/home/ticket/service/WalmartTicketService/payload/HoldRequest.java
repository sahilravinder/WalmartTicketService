package com.home.ticket.service.WalmartTicketService.payload;

public class HoldRequest {
    int numSeats;
    String customerEmail;

    public HoldRequest(int numSeats, String customerEmail) {
        this.numSeats = numSeats;
        this.customerEmail = customerEmail;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
