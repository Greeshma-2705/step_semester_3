package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

class SrmStudent {
    private static int admissionCount = 0;
    private static String university = "SRM University";

    private String name;
    private String regNo;
    private int attendance;

    public SrmStudent(String name, int attendance) {
        admissionCount++;
        this.name = name;
        this.attendance = attendance;
        this.regNo = "RA23110030101" + admissionCount;
    }

    public void printIdCard() {
        System.out.println(this.name + " | " + this.regNo);
    }

    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class StudentMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String name1 = scanner.next();
        int attendance1 = scanner.nextInt();
        SrmStudent student1 = new SrmStudent(name1, attendance1);

        String name2 = scanner.next();
        int attendance2 = scanner.nextInt();
        SrmStudent student2 = new SrmStudent(name2, attendance2);

        student1.printIdCard();
        student2.printIdCard();
        SrmStudent.printTotalAdmissions();

        scanner.close();
    }
}
