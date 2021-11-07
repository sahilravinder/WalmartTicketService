# WalmartTicketService
Wal-Mart Labs coding challenge

Implementation of a Simple Ticket Service that facilitates the discovery, temporary hold and final reservation of seats within a high-demand performance venue

The functions provided by the service are as follows - 

1. Find the number of available seats within the venue.
2. Find and hold the best available seats on behalf of a customer.
3. Reserve and commit a specific group of held seats for a customer.

## Assumptions made 
---
* Seat Hold will expire in 10 seconds and held seats will be released.
* There are 5 Rows, each containing 20 Seats. 
---

## Implementation Notes

* The data model consists of - 
  1. Venue - The Venue with an array of Seat object (resembling a venue with NxN seating arrangement)
  2. Seat - Seat attributes: row, num and reserved/hold status 
  3. SeatHold - Hold attributes: Id, heldSeats, email and bookingTime. 
  
## Build
``` 
$ mvn clean install
```

## Run
```
mvn spring-boot:run
```

## Endpoints    

```
/seatMap
/numSeatsAvailable
/findAndHoldSeats
/reserveSeats
```
 

Lack of License
==
This software is not licensed. Do not distribute.
