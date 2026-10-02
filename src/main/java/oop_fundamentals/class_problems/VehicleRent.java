
abstract class Vehicle {
    private String id;
    private String model;
    private boolean isAvailable;

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    private double dailyRate;

    public StandardCar(String id, String model, double dailyRate) {
        super(id, model);
        this.dailyRate = dailyRate;
    }

    @Override
    public double calculateCharge(int days) {
        return dailyRate * days;
    }
}

class LuxuryCar extends Vehicle {
    private double dailyRate;

    public LuxuryCar(String id, String model, double dailyRate) {
        super(id, model);
        this.dailyRate = dailyRate;
    }

    @Override
    public double calculateCharge(int days) {
        return dailyRate * days;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCharge;
    private boolean isActive;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
        this.isActive = true;
        vehicle.setAvailable(false);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public boolean isActive() {
        return isActive;
    }

    public void returnVehicle() {
        if (!isActive) {
            System.out.println(vehicle.getModel() + " is already returned.");
            return;
        }
        isActive = false;
        vehicle.setAvailable(true);
        System.out.println(vehicle.getModel() + " returned. Now available.");
    }
}

class RentalService {
    public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println("Rental failed: " + vehicle.getModel() + " is currently not available.");
            return null;
        }
        Rental rental = new Rental(customer, vehicle, days);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n", 
            vehicle.getModel(), days, rental.getTotalCharge());
        return rental;
    }
}

public class VehicleRent {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        Vehicle luxuryCarA = new LuxuryCar("V001", "Luxury Car A", 100.0);
        Vehicle standardCarB = new StandardCar("V002", "Standard Car B", 50.0);

        Customer customer = new Customer("Customer");

        Rental rental1 = service.rentVehicle(customer, luxuryCarA, 3);
        Rental rental2 = service.rentVehicle(customer, standardCarB, 5);

        if (rental1 != null) {
            rental1.returnVehicle();
        }
    }
}