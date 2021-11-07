package com.home.ticket.service.WalmartTicketService.model;

public class Seat {
    int row;
    int number;
    boolean hold;
    boolean reserved;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        StringBuilder seatMapBuilder = new StringBuilder();
        if (this.isReserved()) {
            seatMapBuilder.append("R");
        } else if (this.isHold()) {
            seatMapBuilder.append("H");
        } else {
            seatMapBuilder.append("A");
        }
        return seatMapBuilder.toString();
    }
}
