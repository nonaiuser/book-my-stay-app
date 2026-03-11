import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("STANDARD", 3);
        inventory.put("DELUXE", 2);
        inventory.put("SUITE", 1);
    }

    public synchronized boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public synchronized void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

class BookingService {

    private Queue<BookingRequest> requestQueue = new LinkedList<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private Set<String> allRoomIds = new HashSet<>();
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addBookingRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 6);
    }

    public void processBookings() {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();

            synchronized (this) {

                if (!inventoryService.isAvailable(request.roomType)) {
                    System.out.println("Booking failed for " + request.guestName + " (No rooms available)");
                    continue;
                }

                String roomId;

                do {
                    roomId = generateRoomId(request.roomType);
                } while (allRoomIds.contains(roomId));

                allRoomIds.add(roomId);

                allocatedRooms
                        .computeIfAbsent(request.roomType, k -> new HashSet<>())
                        .add(roomId);

                inventoryService.decrement(request.roomType);

                System.out.println("Reservation confirmed for "
                        + request.guestName
                        + " | Room Type: "
                        + request.roomType
                        + " | Room ID: "
                        + roomId);
            }
        }
    }

    public void printAllocatedRooms() {
        System.out.println("Allocated Rooms: " + allocatedRooms);
    }
}

public class UseCase6 {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        bookingService.addBookingRequest(new BookingRequest("Alice", "STANDARD"));
        bookingService.addBookingRequest(new BookingRequest("Bob", "DELUXE"));
        bookingService.addBookingRequest(new BookingRequest("Charlie", "STANDARD"));
        bookingService.addBookingRequest(new BookingRequest("David", "SUITE"));
        bookingService.addBookingRequest(new BookingRequest("Eve", "SUITE"));

        bookingService.processBookings();

        System.out.println();
        bookingService.printAllocatedRooms();
        inventoryService.printInventory();
    }
}