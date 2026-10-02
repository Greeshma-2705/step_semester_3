import java.time.LocalDate;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

abstract class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name);
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name);
    }
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
        System.out.println("Leave request submitted by " + employee.getName() + " for " + startDate + " to " + endDate + ". Status: " + status);
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus newStatus) {
        if (this.status != LeaveStatus.PENDING && newStatus == LeaveStatus.PENDING) {
            System.out.println("Cannot change status: " + this.status + " request cannot revert to Pending.");
            return;
        }
        this.status = newStatus;
        System.out.println("Leave request for " + employee.getName() + " " + newStatus.toString().toLowerCase() + ". Status: " + status);
    }
}

class LeaveManager {
    public void approveRequest(LeaveRequest request) {
        request.setStatus(LeaveStatus.APPROVED);
    }

    public void rejectRequest(LeaveRequest request) {
        request.setStatus(LeaveStatus.REJECTED);
    }

    public void resetToPending(LeaveRequest request) {
        request.setStatus(LeaveStatus.PENDING);
    }
}

public class EmployeeRequest {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("John Doe");
        LeaveRequest johnRequest = new LeaveRequest(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.approveRequest(johnRequest);

        Employee jane = new PartTimeEmployee("Jane Smith");
        LeaveRequest janeRequest = new LeaveRequest(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        manager.resetToPending(johnRequest);
    }
}