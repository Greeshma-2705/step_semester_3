import java.time.LocalDate;
import java.util.*;

abstract class Room {
    private String roomId;
    private double pricePerNight;

    public Room(String roomId, double pricePerNight) {
        this.roomId = roomId;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomId() {
        return roomId;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract double calculatePrice(long nights);
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String roomId, double pricePerNight) {
        super(roomId, pricePerNight);
    }

    @Override
    public double calculatePrice(long nights) {
        return getPricePerNight() * nights;
    }
}

class StandardRoom extends Room {
    public StandardRoom(String roomId, double pricePerNight) {
        super(roomId, pricePerNight);
    }

    @Override
    public double calculatePrice(long nights) {
        return getPricePerNight() * nights;
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

class Reservation {
    private String reservationId;
    private Customer customer;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;
    private boolean isCancelled;

    public Reservation(String reservationId, Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = room.calculatePrice(nights);
        this.isCancelled = false;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        if (isCancelled) return false;
        return start.isBefore(checkOut) && end.isAfter(checkIn);
    }
}

class BookingManager {
    private List<Reservation> reservations = new ArrayList<>();

    public Reservation bookRoom(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Reservation r : reservations) {
            if (r.getRoom().getRoomId().equals(room.getRoomId()) && r.overlaps(checkIn, checkOut)) {
                System.out.println("Booking failed: " + room.getRoomId() + " is not available for " + checkIn + " to " + checkOut + ".");
                return null;
            }
        }
        Reservation reservation = new Reservation(UUID.randomUUID().toString(), customer, room, checkIn, checkOut);
        reservations.add(reservation);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n", room.getRoomId(), checkIn, checkOut, reservation.getTotalPrice());
        return reservation;
    }

    public void cancelReservation(Reservation reservation) {
        if (reservation != null && !reservation.isCancelled()) {
            reservation.cancel();
            System.out.println("Reservation for " + reservation.getRoom().getRoomId() + " cancelled successfully.");
        }
    }
}

public class HostelBooking {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Customer customer = new Customer("Customer");

        Room deluxe101 = new DeluxeRoom("Deluxe Room 101", 200.0);
        Room standard205 = new StandardRoom("Standard Room 205", 150.0);

        Reservation r1 = manager.bookRoom(customer, deluxe101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        Reservation r2 = manager.bookRoom(customer, standard205, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        Reservation r3 = manager.bookRoom(customer, deluxe101, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        if (r1 != null) {
            manager.cancelReservation(r1);
        }
    }
}