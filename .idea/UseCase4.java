import java.util.ArrayList;
import java.util.List;

/**
 * UseCase4 - Search Available Rooms without Modifying State
 *
 * <p>
 * This use case demonstrates how guests can safely view available room types
 * without altering system state, ensuring separation of concerns.
 * </p>
 *
 * <p>
 * The SearchService provides read-only access to inventory and room details,
 * filtering out unavailable rooms and displaying only actionable options.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */

// Reusing the Room abstract class and its concrete subclasses from UseCase2
abstract class Room {

    private int numberOfBeds;
    private double sizeInSquareMeters;
    private double pricePerNight;

    public Room(int numberOfBeds, double sizeInSquareMeters, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.sizeInSquareMeters = sizeInSquareMeters;
        this.pricePerNight = pricePerNight;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSizeInSquareMeters() {
        return sizeInSquareMeters;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + sizeInSquareMeters + " sqm");
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 20.0, 80.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 30.0, 120.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 50.0, 250.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

// Reusing RoomInventory from UseCase3 (centralized inventory)
import java.util.Map;
import java.util.HashMap;

class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 0);  // Mark Double Room as unavailable
        availabilityMap.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }
}

// SearchService providing read-only access
class SearchService {

    private RoomInventory inventory;
    private List<Room> rooms;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
        this.rooms = new ArrayList<>();

        // Initialize room objects (domain model)
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());
    }

    // Perform search and display available rooms
    public void searchAvailableRooms() {
        System.out.println("=== Search Results: Available Rooms ===");

        for (Room room : rooms) {
            int availability = inventory.getAvailability(room.getRoomType());

            if (availability > 0) {  // Only show available rooms
                room.displayRoomDetails();
                System.out.println("Available: " + availability);
                System.out.println("-----------------------------");
            }
        }
    }
}

// Application entry point
public class UseCase4 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        SearchService searchService = new SearchService(inventory);

        // Guest initiates search
        searchService.searchAvailableRooms();

        System.out.println("Search completed. System state unchanged.");
    }
}