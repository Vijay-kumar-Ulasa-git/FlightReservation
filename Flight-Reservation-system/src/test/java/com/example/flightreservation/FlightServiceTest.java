package com.example.flightreservation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlightServiceTest {

    private FlightService flightService;
    private Flight flightNY;
    private Flight flightLondon;

    @BeforeEach
    void setUp() {
        flightService = new FlightService();

        LocalDateTime tomorrowMorning = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        LocalDateTime tomorrowEvening = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0);

        flightNY = new Flight("AA101", "New York", tomorrowMorning, 10);
        flightLondon = new Flight("BA202", "London", tomorrowEvening, 5);

        flightService.addFlight(flightNY);
        flightService.addFlight(flightLondon);
    }

    @Test
    void testSearchFlights_found() {
        LocalDateTime searchDate = flightNY.getDepartureTime();
        List<Flight> result = flightService.searchFlights("New York", searchDate);

        assertEquals(1, result.size());
        assertEquals("AA101", result.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlights_notFound() {
        LocalDateTime searchDate = flightNY.getDepartureTime();
        List<Flight> result = flightService.searchFlights("Paris", searchDate);

        assertTrue(result.isEmpty());
    }

    @Test
    void testBookFlight_success() {
        Reservation reservation = flightService.bookFlight("John Doe", flightNY, 3);

        assertNotNull(reservation);
        assertEquals(7, flightNY.getAvailableSeats());
        assertEquals(3, reservation.getSeatsBooked());
    }

    @Test
    void testBookFlight_insufficientSeats() {
        assertThrows(IllegalArgumentException.class,
                () -> flightService.bookFlight("John Doe", flightLondon, 10));
    }

    @Test
    void testGetReservationsForCustomer() {
        flightService.bookFlight("Alice", flightNY, 2);
        flightService.bookFlight("Alice", flightLondon, 1);
        flightService.bookFlight("Bob", flightNY, 1);

        List<Reservation> aliceReservations = flightService.getReservationsForCustomer("Alice");

        assertEquals(2, aliceReservations.size());
    }
}
