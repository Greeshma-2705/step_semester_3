package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;

class BookIssue {
    String title;
    String borrowerName;
    int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    public double fineAmount() {
        if (this.daysOverdue > 0) {
            return this.daysOverdue * 5.0;
        }
        return 0.0;
    }

    public boolean isSeverelyOverdue() {
        return this.daysOverdue > 14;
    }

    public static double totalFineCollected(BookIssue[] issues) {
        double totalFine = 0.0;
        for (BookIssue issue : issues) {
            totalFine += issue.fineAmount();
        }
        return totalFine;
    }
}

public class BookIssueMain{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int count = scanner.nextInt();
        scanner.nextLine();
        
        BookIssue[] issues = new BookIssue[count];
        
        for (int i = 0; i < count; i++) {
            String title = scanner.nextLine();
            String borrowerName = scanner.nextLine();
            int daysOverdue = scanner.nextInt();
            scanner.nextLine();
            issues[i] = new BookIssue(title, borrowerName, daysOverdue);
        }
        
        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issue.title + " - " + issue.daysOverdue + " days - " + status);
        }
        
        System.out.println("Total fine collected: Rs " + BookIssue.totalFineCollected(issues));
        
        scanner.close();
    }
}