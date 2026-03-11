import java.util.*;

// Represents an optional service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return serviceName + " - ₹" + cost;
    }
}

// Represents a reservation (core booking object)
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Manages add-on services for reservations
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add a service to a reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() + " added to reservation " + reservationId);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services for reservation " + reservationId);
            return;
        }

        System.out.println("Services for reservation " + reservationId + ":");

        for (AddOnService service : services) {
            System.out.println(service);
        }
    }
}

// Main class
public class AddOnReservationSystem {

    public static void main(String[] args) {

        // Create a reservation
        Reservation reservation = new Reservation("R101", "John");

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Access", 2000);

        // Add services to reservation
        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), airportPickup);
        manager.addService(reservation.getReservationId(), spa);

        // Display selected services
        manager.displayServices(reservation.getReservationId());

        // Calculate additional cost
        double totalCost = manager.calculateTotalServiceCost(reservation.getReservationId());

        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}