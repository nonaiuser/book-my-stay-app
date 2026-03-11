

// Abstract class representing a generalized Room concept
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

    // Abstract method to enforce specialization
    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + sizeInSquareMeters + " sqm");
        System.out.println("Price per Night: $" + pricePerNight);
    }
}


// Concrete Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 20.0, 80.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}


// Concrete Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 30.0, 120.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}


// Concrete Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 50.0, 250.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}


// Application Entry Point for Use Case 2
public class UseCase2 {

    public static void main(String[] args) {

        System.out.println("=== Available Room Types ===");

        // Polymorphism: using Room reference type
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability representation
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailability);
        System.out.println("--------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailability);
        System.out.println("--------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailability);
        System.out.println("--------------------------------");

        System.out.println("Application terminated.");
    }
}