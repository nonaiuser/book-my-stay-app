import java.io.*;
import java.util.*;

// Reservation class
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "ReservationID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType;
    }
}

// System State class (stores inventory + booking history)
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public static void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");
            return (SystemState) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading saved state. Starting with clean system.");
        }

        return null;
    }
}

// Main system
public class PersistenceBookingSystem {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        // Load saved state
        SystemState state = PersistenceService.loadState();

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Initialize new system state
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);

            bookings = new ArrayList<>();
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("\nHotel Booking System");

        System.out.print("Enter guest name: ");
        String guest = sc.nextLine();

        System.out.print("Enter room type (Standard/Deluxe): ");
        String roomType = sc.nextLine();

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type.");
        } else if (inventory.get(roomType) <= 0) {
            System.out.println("No rooms available.");
        } else {

            String reservationId = "B" + (bookings.size() + 1);

            Reservation reservation =
                    new Reservation(reservationId, guest, roomType);

            bookings.add(reservation);

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking confirmed: " + reservation);
        }

        // Show current bookings
        System.out.println("\nBooking History:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        // Show inventory
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.saveState(newState);
    }
}