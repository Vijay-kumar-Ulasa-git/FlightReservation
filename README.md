
# **Flight Reservation System**

A console-based flight reservation system built in Java using Maven. This application demonstrates core Java concepts, clean architecture, and testable business logic through a simple airline booking workflow.

---

## **How to Run**

1. Import the project into Eclipse as an **Existing Maven Project**.
2. Ensure **JDK 17+** is configured in the IDE.
3. Locate and run the main application class:

   ```
   src/main/java/com/example/flightreservation/FlightReservationApp.java
   ```
4. Use the console menu to search flights, book seats, and view reservations.

---

## **Features**

* **Flight Search:** Users can search for flights by destination and departure date.
* **Booking Functionality:** Validates seat availability before confirming bookings.
* **Reservation Lookup:** Users can retrieve all reservations associated with their name.
* **Unit Testing:** Includes JUnit 5 tests for `FlightService` to ensure business logic reliability.
* **Sample Data:** Several sample flights are preloaded for quick testing and demonstration.

---

## **Design Considerations**

* **Layered Structure:**

  * `Flight` and `Reservation` act as domain models.
  * `FlightService` contains all business logic (searching, booking, validation).
  * `FlightReservationApp` handles only user interaction.
* **In-Memory Storage:** Lists are used instead of a database to keep the application simple and easy to execute.
* **Data Integrity:** Bookings are only allowed when available seats meet the requested amount, preventing invalid reservations.
* **Testability:** The service layer is isolated, allowing thorough unit testing without dependency on UI or external systems.

---

## **Technologies Used**

* Java 17+
* Maven
* JUnit 5
* Eclipse IDE (recommended)

---




