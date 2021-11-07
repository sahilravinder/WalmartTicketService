package com.home.ticket.service.WalmartTicketService;

import com.home.ticket.service.WalmartTicketService.impl.TicketBookingService;
import com.home.ticket.service.WalmartTicketService.model.Seat;
import com.home.ticket.service.WalmartTicketService.model.SeatHold;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WalmartTicketServiceApplicationTests {

	@Autowired
	TicketBookingService ticketService;

	@Test
	public void getAvailableSeatsTest() {
		int availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(100, availableSeats);
	}

	@Test
	public void holdSeatsTest() {
		ticketService.findAndHoldSeats(22, "s@abc.com");
		int availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(78, availableSeats);
	}

	@Test
	public void holdSeatsExpiredTest() throws InterruptedException {
		ticketService.findAndHoldSeats(22, "s@abc.com");
		int availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(78, availableSeats);
		Thread.sleep(12000); //Introduce delay so tickets are released
		availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(100, availableSeats);
	}

	@Test
	public void holdSeatsExpiredMultiTransactionTest() throws InterruptedException {
		ticketService.findAndHoldSeats(10, "s@abc.com");

		SeatHold customer = ticketService.findAndHoldSeats(11, "a@xyz.com");
		ticketService.reserveSeats(customer.getId(), "a@xyz.com");

		int availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(79, availableSeats);

		Thread.sleep(12000); //Introduce delay so tickets are released

		availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(89, availableSeats);
	}

	@Test
	public void holdMoreThanAvailableTest() {
		SeatHold hold = ticketService.findAndHoldSeats(70, "s@abc.com");
		ticketService.reserveSeats(hold.getId(), "s@abc.com");

		hold = ticketService.findAndHoldSeats(50, "a@xyz.com");
		int availableSeats = ticketService.numSeatsAvailable();
		Assert.assertEquals(30, availableSeats);
		Assert.assertNull(hold);
	}

	@Test
	public void reserveWithInvalidIdTest() {
		String message = ticketService.reserveSeats(10021319, "s@abc.com");
		Assert.assertEquals("SeatHold ID: 10021319 doesn't exist, please try again", message);
	}

	@Test
	public void reserveWithInvalidEmail() {
		SeatHold hold = ticketService.findAndHoldSeats(22, "s@abc.com");
		String message = ticketService.reserveSeats(hold.getId(), "a@xyz.com");
		Assert.assertEquals("Cannot find Customer with provided email. Please try again", message);
	}

	@Test
	public void getBestSeatsTest() {
		SeatHold hold = ticketService.findAndHoldSeats(5, "s@abc.com");

		Object[] heldSeatRows = hold.getHoldSeats().stream().map(Seat::getRow).distinct().toArray();
		Object[] heldSeats = hold.getHoldSeats().stream().map(Seat::getNumber).distinct().toArray();

		List<Object> seatRows = new ArrayList<>() {{
			add(1);
		}};
		List<Object> seats = new ArrayList<>() {{
			add(1);
			add(2);
			add(3);
			add(4);
			add(5);
		}};

		Assert.assertArrayEquals(seatRows.toArray(), heldSeatRows);
		Assert.assertArrayEquals(seats.toArray(), heldSeats);

		hold = ticketService.findAndHoldSeats(3, "a@xyz.com");

		heldSeatRows = hold.getHoldSeats().stream().map(Seat::getRow).distinct().toArray();
		heldSeats = hold.getHoldSeats().stream().map(Seat::getNumber).distinct().toArray();

		seatRows = new ArrayList<>() {{
			add(1);
		}};
		seats = new ArrayList<>() {{
			add(6);
			add(7);
			add(8);
		}};

		Assert.assertArrayEquals(seatRows.toArray(), heldSeatRows);
		Assert.assertArrayEquals(seats.toArray(), heldSeats);
	}

}
