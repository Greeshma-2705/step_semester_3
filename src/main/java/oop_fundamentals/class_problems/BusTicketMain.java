package main.java.oop_fundamentals.class_problems;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean isCheckedIn;

    public BusTicket(String passengerName, String destination) {
        if (!isValidString(passengerName)) {
            throw new IllegalArgumentException();
        }

        if (!isValidString(destination)) {
            throw new IllegalArgumentException();
        }

        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.isCheckedIn = false;
    }

    private static boolean isValidString(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }

        return str.matches("^[a-zA-Z\\s]+$");
    }

    public void markCheckedIn() {
        if (this.isCheckedIn) {
            throw new IllegalStateException();
        }

        this.isCheckedIn = true;
    }

    public static void processBatch(String[][] rawBookings) {
        if (rawBookings == null) {
            return;
        }

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        Set<String> uniqueBookings = new HashSet<>();

        for (String[] booking : rawBookings) {
            if (booking == null || booking.length < 2) {
                rejected++;
                continue;
            }

            String name = booking[0];
            String dest = booking[1];

            try {
                new BusTicket(name, dest);

                String uniqueKey =
                    (name.trim() + "|" + dest.trim()).toLowerCase();

                if (uniqueBookings.contains(uniqueKey)) {
                    duplicates++;
                } else {
                    uniqueBookings.add(uniqueKey);
                    valid++;
                }

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.printf(
            "Valid: %d | Rejected: %d | Duplicates skipped: %d%n",
            valid, rejected, duplicates
        );
    }
}

public class BusTicketMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of bookings: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        String[][] bookings = new String[n][2];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter passenger name: ");
            bookings[i][0] = scanner.nextLine();

            System.out.print("Enter destination: ");
            bookings[i][1] = scanner.nextLine();
        }

        System.out.println();

        BusTicket.processBatch(bookings);

        scanner.close();
    }
}