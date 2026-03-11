import java.util.*;

// Reservation class
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Price: ₹" + price;
    }
}


// Booking History class (stores confirmed bookings)
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation confirmed and stored in history.");
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}


// Booking Report Service (handles reporting only)
class BookingReportService {

    // Display all reservations
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n--- Booking History ---");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\n--- Booking Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}


// Main system class
public class BookingHistorySystem {

    public static void main(String[] args) {

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Create report service
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("B101", "Alice", "Deluxe", 3000);
        Reservation r2 = new Reservation("B102", "Bob", "Suite", 5000);
        Reservation r3 = new Reservation("B103", "Charlie", "Standard", 2000);

        // Add bookings to history (in order confirmed)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin retrieves booking history
        List<Reservation> storedReservations = history.getReservations();

        // Display all bookings
        reportService.displayAllBookings(storedReservations);

        // Generate summary report
        reportService.generateSummary(storedReservations);
    }
}