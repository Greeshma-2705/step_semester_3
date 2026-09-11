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

class Employee {
    String name;
    double pay;

    public Employee(String name, double pay) {
        this.name = name;
        this.pay = pay;
    }
}

class CompanyEmployeeRecord {
    Employee employee;
    ParkingSlot parkingSlot;
    static int totalRecords = 0;

    public CompanyEmployeeRecord(String name, double pay, ParkingSlot parkingSlot) {
        this.employee = new Employee(name, pay);
        this.parkingSlot = parkingSlot;
        totalRecords++;
    }

    public String fullProfile() {
        String slotInfo = (parkingSlot != null && parkingSlot.isOccupied) ? parkingSlot.slotId : "no parking assigned";
        return employee.name + " | Pay: Rs " + employee.pay + " | Slot: " + slotInfo;
    }
}

public class CompanyEmployeeRecordMain {

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot != null && !slot.isOccupied) {
                return slot;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total available system parking slots: ");
        int numSysSlots = scanner.nextInt();
        ParkingSlot[] companyLot = new ParkingSlot[numSysSlots];

        for (int i = 0; i < numSysSlots; i++) {
            System.out.print("Enter System Slot ID " + (i + 1) + ": ");
            String id = scanner.next();
            companyLot[i] = new ParkingSlot(id, false);
        }

        System.out.print("Enter number of employees to register: ");
        int numEmployees = scanner.nextInt();
        CompanyEmployeeRecord[] records = new CompanyEmployeeRecord[numEmployees];

        for (int i = 0; i < numEmployees; i++) {
            System.out.print("Enter employee " + (i + 1) + " name: ");
            String empName = scanner.next();
            System.out.print("Enter monthly pay: ");
            double pay = scanner.nextDouble();

            ParkingSlot assignedSlot = findAvailableSlot(companyLot);
            if (assignedSlot != null) {
                assignedSlot.isOccupied = true;
                assignedSlot.vehicleNo = empName + "_Vehicle";
            }
            records[i] = new CompanyEmployeeRecord(empName, pay, assignedSlot);
        }

        System.out.println("\n--- Company Employee Records Summary ---");
        for (CompanyEmployeeRecord record : records) {
            System.out.println(record.fullProfile());
        }
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);

        scanner.close();
    }
}