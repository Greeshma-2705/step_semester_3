package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

public class FareSplitter {

    private final String tripId;
    private final double totalFare;
    private final int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (tripId == null || tripId.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (totalFare <= 0 || passengerCount <= 0) {
            throw new IllegalArgumentException();
        }

        this.tripId = tripId.trim();
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public double[] fareBreakdown() {

        double[] breakdown = new double[passengerCount];

        long totalCents = Math.round(totalFare * 100);
        long baseCents = totalCents / passengerCount;
        long remainder = totalCents % passengerCount;

        for (int i = 0; i < passengerCount; i++) {

            long amount = baseCents;

            if (i >= passengerCount - remainder) {
                amount++;
            }

            breakdown[i] = amount / 100.0;
        }

        return breakdown;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {

        if (expected <= 0 || confirmed < 0) {
            return false;
        }

        return confirmed < expected;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Trip ID: ");
        String tripId = sc.nextLine();

        System.out.print("Enter Total Fare: ");
        double totalFare = sc.nextDouble();

        System.out.print("Enter Number of Passengers: ");
        int passengerCount = sc.nextInt();

        System.out.print("Enter Confirmed Passengers: ");
        int confirmed = sc.nextInt();

        try {

            FareSplitter fare =
                    new FareSplitter(tripId, totalFare, passengerCount);

            double[] result = fare.fareBreakdown();

            System.out.println("\nFare Breakdown:");

            for (int i = 0; i < result.length; i++) {
                System.out.printf(
                        "Passenger %d: Rs %.2f%n",
                        i + 1,
                        result[i]
                );
            }

            System.out.println(
                    "Confirmation overdue: " +
                    fare.isConfirmationOverdue(confirmed, passengerCount)
            );

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid input.");

        }

        sc.close();
    }
}