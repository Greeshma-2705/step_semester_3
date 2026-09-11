package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;

class ParkingSlot {
    String slotId;
    boolean isOccupied;
    String vehicleNo;

    public ParkingSlot(String slotId, boolean isOccupied) {
        this.slotId = slotId;
        this.isOccupied = isOccupied;
    }
}

public class ParkingSlotAllotment {

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot != null && !slot.isOccupied) {
                return slot;
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot availableSlot = findAvailableSlot(slots);
        if (availableSlot != null) {
            availableSlot.isOccupied = true;
            availableSlot.vehicleNo = vehicleNo;
            System.out.println(vehicleNo + " allotted to slot " + availableSlot.slotId);
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total parking slots to create: ");
        int numSlots = scanner.nextInt();
        ParkingSlot[] slots = new ParkingSlot[numSlots];

        for (int i = 0; i < numSlots; i++) {
            System.out.print("Enter Slot ID for slot " + (i + 1) + ": ");
            String id = scanner.next();
            System.out.print("Is it occupied? (true/false): ");
            boolean occupied = scanner.nextBoolean();
            slots[i] = new ParkingSlot(id, occupied);
        }

        System.out.print("Enter vehicle number to allot: ");
        String vehicleNo = scanner.next();
        
        safeAllot(slots, vehicleNo);
        scanner.close();
    }
}
