package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

class SrmStudent {
    String name;
    String regNo;
    int attendance;

    public SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    public boolean isEligible() {
        return this.attendance >= 75;
    }

    public static double classAverage(SrmStudent[] students) {
        if (students == null || students.length == 0) {
            return 0.0;
        }
        int totalAttendance = 0;
        for (SrmStudent student : students) {
            totalAttendance += student.attendance;
        }
        return (double) totalAttendance / students.length;
    }
}

public class AttendanceMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SrmStudent[] students = new SrmStudent[5];

        for (int i = 0; i < 5; i++) {
            String name = scanner.next();
            int attendance = scanner.nextInt();
            students[i] = new SrmStudent(name, "REG" + (i + 1), attendance);
        }

        for (SrmStudent student : students) {
            String status = student.isEligible() ? "Eligible" : "Detained";
            System.out.println(student.name + " - " + student.attendance + "% - " + status);
        }

        double average = SrmStudent.classAverage(students);
        System.out.printf("Class average: %.1f%%\n", average);
        
        scanner.close();
    }
}