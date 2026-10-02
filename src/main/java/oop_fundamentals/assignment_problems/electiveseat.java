import java.util.*;

enum StudentType {
    REGULAR(24),
    HONORS(28),
    EXCHANGE(20);

    private final int maxCredits;

    StudentType(int maxCredits) {
        this.maxCredits = maxCredits;
    }

    public int getMaxCredits() {
        return maxCredits;
    }
}

class Student {
    private String name;
    private StudentType type;
    private int currentCredits;

    public Student(String name, StudentType type, int currentCredits) {
        this.name = name;
        this.type = type;
        this.currentCredits = currentCredits;
    }

    public String getName() {
        return name;
    }

    public StudentType getType() {
        return type;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public boolean canAddCredits(int credits) {
        return (currentCredits + credits) <= type.getMaxCredits();
    }

    public void addCredits(int credits) {
        this.currentCredits += credits;
    }

    public void removeCredits(int credits) {
        this.currentCredits -= credits;
    }

    public String getCreditInfo() {
        return "credits: " + currentCredits + "/" + type.getMaxCredits();
    }
}

class Elective {
    private String name;
    private int credits;
    private int capacity;
    private List<Student> enrolledStudents = new ArrayList<>();
    private Queue<Student> waitlist = new LinkedList<>();

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean enroll(Student student) {
        if (enrolledStudents.contains(student) || waitlist.contains(student)) {
            return false;
        }

        if (!student.canAddCredits(credits)) {
            System.out.println("Enrollment failed: " + student.getName() + " would exceed the " 
                + capitalize(student.getType().name()) + " credit limit (" 
                + (student.getCurrentCredits() + credits) + "/" + student.getType().getMaxCredits() + ").");
            return false;
        }

        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            student.addCredits(credits);
            System.out.println(student.getName() + " enrolled in " + name + " (" + student.getCreditInfo() + ").");
            if (enrolledStudents.size() == capacity) {
                System.out.println(name + " is full.");
            }
            return true;
        } else {
            waitlist.add(student);
            System.out.println(student.getName() + " added to waitlist (position " + waitlist.size() + ").");
            return true;
        }
    }

    public void drop(Student student) {
        if (!enrolledStudents.contains(student)) {
            return;
        }

        enrolledStudents.remove(student);
        student.removeCredits(credits);
        System.out.println(student.getName() + " dropped " + name + " (" + student.getCreditInfo() + ").");

        promoteFromWaitlist();
    }

    private void promoteFromWaitlist() {
        while (!waitlist.isEmpty() && enrolledStudents.size() < capacity) {
            Student nextStudent = waitlist.poll();
            if (nextStudent.canAddCredits(credits)) {
                enrolledStudents.add(nextStudent);
                nextStudent.addCredits(credits);
                System.out.println(nextStudent.getName() + " promoted from waitlist and enrolled in " 
                    + name + " (" + nextStudent.getCreditInfo() + ").");
                break;
            }
        }
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}

public class electiveseat {
    public static void main(String[] args) {
        Elective cloudComputing = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", StudentType.REGULAR, 20);
        Student ravi = new Student("Ravi", StudentType.HONORS, 22);
        Student neha = new Student("Neha", StudentType.EXCHANGE, 12);
        Student kiran = new Student("Kiran", StudentType.REGULAR, 22);

        cloudComputing.enroll(asha);
        cloudComputing.enroll(ravi);
        cloudComputing.enroll(neha);
        cloudComputing.enroll(kiran);

        cloudComputing.drop(asha);
    }
}