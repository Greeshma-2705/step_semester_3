package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;

class Employee {
    private String empid;
    private String empName;
    private double salary;

    public Employee(String empid, String empName, double salary) {
        this.empid = empid;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    // Default effective salary = base salary
    public double effectiveSalary() {
        return salary;
    }

    public String getEmpName() {
        return empName;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(String empid, String empName, double salary, double teamBonus) {
        super(empid, empName, salary);
        this.teamBonus = teamBonus;
    }

    @Override
    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(String empid, String empName, double salary, double stipendCap) {
        super(empid, empName, salary);
        this.stipendCap = stipendCap;
    }

    @Override
    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class EmployeeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double empSalary = scanner.nextDouble();

        double mgrSalary = scanner.nextDouble();
        double teamBonus = scanner.nextDouble();

        double internSalary = scanner.nextDouble();
        double stipendCap = scanner.nextDouble();

        Employee emp = new Employee("E01", "Plain Employee", empSalary);
        Employee mgr = new ManagerEmployee("M01", "Manager", mgrSalary, teamBonus);
        Employee intern = new InternEmployee("I01", "Intern", internSalary, stipendCap);

        Employee[] employees = {emp, mgr, intern};

        for (Employee e : employees) {
            System.out.println(e.getEmpName() + " effective pay: Rs " + e.effectiveSalary());
        }

        scanner.close();
    }
}
