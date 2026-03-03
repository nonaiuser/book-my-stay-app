import java.util.LinkedList;
import java.util.Queue;

// Represents a guest's intent to book a room
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// Booking Request Queue manages incoming reservations
class BookingQueue {

    private Queue<Reservation> reservationQueue;

    public BookingQueue() {
        reservationQueue = new LinkedList<>();
    }

    // Accepts a new booking request
    public void addRequest(Reservation reservation) {
        reservationQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Peek at the next request without removing it
    public Reservation peekNextRequest() {
        return reservationQueue.peek();
    }

    // Display all pending requests
    public void displayAllRequests() {
        System.out.println("\n=== Pending Booking Requests ===");
        for (Reservation res : reservationQueue) {
            res.displayReservation();
        }
    }

    // Returns the number of pending requests
    public int getQueueSize() {
        return reservationQueue.size();
    }
}

// Application entry point
public class UseCase5 {

    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Sample guest booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display all requests in arrival order
        bookingQueue.displayAllRequests();

        System.out.println("\nTotal pending requests: " + bookingQueue.getQueueSize());
        System.out.println("Application terminated. No inventory updates occurred.");
    }
}