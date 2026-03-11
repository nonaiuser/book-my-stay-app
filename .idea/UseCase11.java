import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Room Inventory
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
    }

    // Critical section
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            System.out.println("Room allocated to " + guestName + " (" + roomType + ")");
            return true;
        } else {
            System.out.println("No room available for " + guestName + " (" + roomType + ")");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Concurrent Booking Processor
class BookingProcessor implements Runnable {

    private Queue<BookingRequest> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            BookingRequest request = null;

            // synchronized queue access
            synchronized (queue) {

                if (queue.isEmpty()) {
                    return;
                }

                request = queue.poll();
            }

            if (request != null) {
                inventory.allocateRoom(request.roomType, request.guestName);
            }
        }
    }
}

// Main System
public class ConcurrentBookingSystem {

    public static void main(String[] args) {

        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        RoomInventory inventory = new RoomInventory();

        // Multiple guest requests
        bookingQueue.add(new BookingRequest("Alice", "Standard"));
        bookingQueue.add(new BookingRequest("Bob", "Standard"));
        bookingQueue.add(new BookingRequest("Charlie", "Standard"));
        bookingQueue.add(new BookingRequest("David", "Deluxe"));
        bookingQueue.add(new BookingRequest("Eve", "Deluxe"));

        // Multiple processing threads
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory));

        // Start concurrent execution
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Show final inventory
        inventory.displayInventory();
    }
}