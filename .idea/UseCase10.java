import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    public String toString() {
        return "ReservationID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (active ? "Confirmed" : "Cancelled");
    }
}


// Inventory management
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void allocate(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void restore(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nRoom Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}


// Booking history
class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void displayBookings() {
        System.out.println("\nBooking Records:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}


// Cancellation service with rollback
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack to track released room IDs
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelReservation(String reservationId) {

        Reservation reservation = history.getReservation(reservationId);

        // Validate existence
        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        // Prevent duplicate cancellation
        if (!reservation.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Record allocated room ID in rollback stack
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.restore(reservation.getRoomType());

        // Update reservation state
        reservation.cancel();

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Released Rooms): " + rollbackStack);
    }
}


// Main System
public class BookingCancellationSystem {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService(inventory, history);

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("B101", "Alice", "Standard", "S1");
        Reservation r2 = new Reservation("B102", "Bob", "Deluxe", "D1");

        inventory.allocate("Standard");
        inventory.allocate("Deluxe");

        history.addReservation(r1);
        history.addReservation(r2);

        // Display initial state
        history.displayBookings();
        inventory.displayInventory();

        // Guest cancels booking
        cancelService.cancelReservation("B101");

        // Try cancelling again (invalid case)
        cancelService.cancelReservation("B101");

        // Try cancelling non-existing booking
        cancelService.cancelReservation("B999");

        // Display final state
        history.displayBookings();
        inventory.displayInventory();
        cancelService.showRollbackStack();
    }
}