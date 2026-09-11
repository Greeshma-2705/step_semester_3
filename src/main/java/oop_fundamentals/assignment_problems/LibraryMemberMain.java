package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;

class LibraryMember {
    String name;
    String memberId;
    int booksIssued;

    static String libraryName = "City Library";
    static int memberCount = 0;

    public LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-100" + memberCount;
    }

    public void printMemberCard() {
        System.out.println(this.name + " | " + this.memberId);
    }

    public static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class LibraryMemberMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of library members to add: ");
        int numMembers = scanner.nextInt();

        for (int i = 0; i < numMembers; i++) {
            System.out.print("Enter member " + (i + 1) + " name: ");
            String name = scanner.next();
            System.out.print("Enter books issued: ");
            int books = scanner.nextInt();

            LibraryMember member = new LibraryMember(name, books);
            member.printMemberCard();
        }

        LibraryMember.printTotalMembers();
        scanner.close();
    }
}