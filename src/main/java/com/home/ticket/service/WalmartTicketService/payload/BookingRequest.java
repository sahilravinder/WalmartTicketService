package com.home.ticket.service.WalmartTicketService.payload;

public class BookingRequest {

    int seatHoldId;
    String customerEmail;

    public BookingRequest(int seatHoldId, String customerEmail) {
        this.seatHoldId = seatHoldId;
        this.customerEmail = customerEmail;
    }

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
