package com.example.flightreservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    private final List<Flight> flights = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    /**
     * Search flights by destination and date (date part of LocalDateTime).
     */
    public List<Flight> searchFlights(String destination, LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        return flights.stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(destination)
                        && f.getDepartureTime().toLocalDate().equals(date)
                        && f.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Book seats on a given flight. Throws IllegalArgumentException if invalid.
     */
    public Reservation bookFlight(String customerName, Flight flight, int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Number of seats must be positive.");
        }

        if (flight.getAvailableSeats() < seats) {
            throw new IllegalArgumentException("Not enough available seats for this booking.");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - seats);
        Reservation reservation = new Reservation(customerName, flight, seats);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservationsForCustomer(String customerName) {
        return reservations.stream()
                .filter(r -> r.getCustomerName().equalsIgnoreCase(customerName))
                .collect(Collectors.toList());
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }
}
