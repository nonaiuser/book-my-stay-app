import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
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

    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Inventory class (tracks room availability)
class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    // Validate room type
    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Check availability
    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (rooms.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    // Allocate room safely
    public void allocateRoom(String roomType) throws InvalidBookingException {

        validateRoomType(roomType);
        checkAvailability(roomType);

        int remaining = rooms.get(roomType) - 1;

        if (remaining < 0) {
            throw new InvalidBookingException("Inventory cannot become negative.");
        }

        rooms.put(roomType, remaining);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public Reservation createBooking(String guestName, String roomType) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Fail-fast validation
        inventory.validateRoomType(roomType);

        inventory.allocateRoom(roomType);

        return new Reservation(guestName, roomType);
    }
}

// Main System
public class BookingValidationSystem {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        Scanner sc = new Scanner(System.in);

        System.out.println("Hotel Booking System");

        try {

            System.out.print("Enter Guest Name: ");
            String guest = sc.nextLine();

            System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
            String room = sc.nextLine();

            Reservation reservation = service.createBooking(guest, room);

            System.out.println("\nBooking Successful!");
            System.out.println(reservation);

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println("\nBooking Failed: " + e.getMessage());

        }

        // System continues running safely
        inventory.displayInventory();
    }
}