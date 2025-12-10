package com.example.flightreservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class FlightReservationApp {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FlightService flightService = new FlightService();
        seedFlights(flightService);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleSearchFlights(flightService);
                    break;
                case "2":
                    handleBookFlight(flightService);
                    break;
                case "3":
                    handleViewReservations(flightService);
                    break;
                case "4":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== Flight Reservation System =====");
        System.out.println("1. Search Flights");
        System.out.println("2. Book a Flight");
        System.out.println("3. View My Reservations");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void seedFlights(FlightService flightService) {
        // Add some sample flights
        LocalDateTime now = LocalDateTime.now();
        flightService.addFlight(new Flight("AA101", "New York", now.plusDays(1).withHour(9).withMinute(0), 10));
        flightService.addFlight(new Flight("BA202", "New York", now.plusDays(1).withHour(15).withMinute(30), 5));
        flightService.addFlight(new Flight("CA303", "London", now.plusDays(2).withHour(11).withMinute(15), 8));
        flightService.addFlight(new Flight("DA404", "Paris", now.plusDays(1).withHour(18).withMinute(45), 2));
    }

    private static void handleSearchFlights(FlightService flightService) {
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Enter departure date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DATE_FORMAT);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.\n");
            return;
        }

        LocalDateTime dateTime = date.atStartOfDay();
        List<Flight> results = flightService.searchFlights(destination, dateTime);

        if (results.isEmpty()) {
            System.out.println("No flights found for " + destination + " on " + date + ".\n");
        } else {
            System.out.println("Available flights:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
            System.out.println();
        }
    }

    private static void handleBookFlight(FlightService flightService) {
        List<Flight> allFlights = flightService.getAllFlights();
        if (allFlights.isEmpty()) {
            System.out.println("No flights available to book.\n");
            return;
        }

        System.out.println("All available flights:");
        for (int i = 0; i < allFlights.size(); i++) {
            System.out.println((i + 1) + ". " + allFlights.get(i));
        }

        System.out.print("Select flight number (1-" + allFlights.size() + "): ");
        String indexInput = scanner.nextLine().trim();
        int index;
        try {
            index = Integer.parseInt(indexInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.\n");
            return;
        }

        if (index < 1 || index > allFlights.size()) {
            System.out.println("Invalid flight selection.\n");
            return;
        }

        Flight selectedFlight = allFlights.get(index - 1);

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine().trim();

        System.out.print("Enter number of seats to book: ");
        String seatsInput = scanner.nextLine().trim();
        int seats;
        try {
            seats = Integer.parseInt(seatsInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of seats.\n");
            return;
        }

        try {
            Reservation reservation = flightService.bookFlight(customerName, selectedFlight, seats);
            System.out.println("Booking successful! Reservation details:");
            System.out.println(reservation + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Booking failed: " + e.getMessage() + "\n");
        }
    }

    private static void handleViewReservations(FlightService flightService) {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine().trim();

        List<Reservation> reservations = flightService.getReservationsForCustomer(customerName);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for " + customerName + ".\n");
        } else {
            System.out.println("Reservations for " + customerName + ":");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
            System.out.println();
        }
    }
}
