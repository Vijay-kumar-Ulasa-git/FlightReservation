package com.example.flightreservation;

public class Reservation {
    private String customerName;
    private Flight flight;
    private int seatsBooked;

    public Reservation(String customerName, Flight flight, int seatsBooked) {
        this.customerName = customerName;
        this.flight = flight;
        this.seatsBooked = seatsBooked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customerName='" + customerName + '\'' +
                ", flight=" + flight +
                ", seatsBooked=" + seatsBooked +
                '}';
    }
}
