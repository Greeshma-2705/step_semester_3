package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

class FeeAccount {
    protected double dueAmount;

    public FeeAccount(double initialDue) {
        this.dueAmount = initialDue;
    }

    public double getDueAmount() {
        return this.dueAmount;
    }

    public void processPayment(double amount) {
        if (amount < 0) {
            System.out.println("[Rejected] Negative amount: Rs " + amount);
            return;
        }
        this.dueAmount -= amount;
    }
}

class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(double initialDue) {
        super(initialDue);
    }
}

class HostelRoom {
    private String roomNumber;

    public HostelRoom(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;
    static int totalStudents = 0;

    public SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;
        totalStudents++;
    }

    public void allotRoom(HostelRoom room) {
        this.room = room;
    }

    public String fullStatus() {
        String roomStr = (this.room == null) ? "unallotted" : this.room.getRoomNumber();
        return name + " | Due: Rs " + feeAccount.getDueAmount() + " | Room: " + roomStr;
    }
}

public class StudentFeeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SrmStudent[] students = new SrmStudent[3];

        for (int i = 0; i < 3; i++) {
            System.out.println("Enter details for Student " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Registration Number: ");
            String regNo = scanner.nextLine();
            System.out.print("Initial Fee Due: ");
            double initialDue = scanner.nextDouble();
            scanner.nextLine(); 

            students[i] = new SrmStudent(name, regNo, new HostelFeeAccount(initialDue));
            System.out.println();
        }

        System.out.print("Enter room number for " + students[0].name + ": ");
        students[0].allotRoom(new HostelRoom(scanner.nextLine()));

        System.out.print("Enter room number for " + students[1].name + ": ");
        students[1].allotRoom(new HostelRoom(scanner.nextLine()));

        System.out.println("\n[Skipping room allotment for " + students[2].name + " to leave unallotted]");

        System.out.print("\nEnter payment amount for " + students[0].name + ": ");
        double payment1 = scanner.nextDouble();
        students[0].feeAccount.processPayment(payment1);

        System.out.print("Enter payment amount for " + students[2].name + ": ");
        double payment2 = scanner.nextDouble();
        students[2].feeAccount.processPayment(payment2);

        System.out.println("\nOutput:");
        System.out.println(students[0].fullStatus());
        System.out.println(students[1].fullStatus());
        System.out.println(students[2].fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);

        scanner.close();
    }
}