import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3 - Centralized Room Inventory Management
 *
 * <p>
 * This use case introduces centralized inventory management using a HashMap.
 * It replaces scattered availability variables with a single data structure
 * that acts as the system's single source of truth.
 * </p>
 *
 * <p>
 * The RoomInventory class encapsulates all inventory-related logic,
 * ensuring separation of concerns between room domain modeling
 * and availability state management.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */

// Inventory component responsible for managing availability
class RoomInventory {

    // Single source of truth
    private Map<String, Integer> availabilityMap;

    /**
     * Constructor initializes room availability.
     */
    public RoomInventory() {
        availabilityMap = new HashMap<>();

        // Register room types with initial availability
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }

    /**
     * Retrieve availability for a given room type.
     *
     * @param roomType the type of room
     * @return available count
     */
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    /**
     * Update availability in a controlled manner.
     *
     * @param roomType the type of room
     * @param newCount updated room count
     */
    public void updateAvailability(String roomType, int newCount) {
        if (availabilityMap.containsKey(roomType)) {
            availabilityMap.put(roomType, newCount);
        } else {
            System.out.println("Room type does not exist in inventory.");
        }
    }

    /**
     * Display current inventory state.
     */
    public void displayInventory() {
        System.out.println("=== Current Room Inventory ===");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}


// Application entry point for Use Case 3
public class UseCase3 {

    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial state
        inventory.displayInventory();

        System.out.println("\nUpdating Suite Room availability...\n");

        // Controlled update
        inventory.updateAvailability("Suite Room", 1);

        // Display updated state
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}