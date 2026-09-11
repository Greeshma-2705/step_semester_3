package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

public final class BoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException();
        }

        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {

        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException();
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        double basePenaltyPercent = 0.0;

        if (minutesLate <= 5) {
            basePenaltyPercent += minutesLate * 0.5;
        } else if (minutesLate <= 15) {
            basePenaltyPercent += 5 * 0.5;
            basePenaltyPercent += (minutesLate - 5) * 1.0;
        } else {
            basePenaltyPercent += 5 * 0.5;
            basePenaltyPercent += 10 * 1.0;
            basePenaltyPercent += (minutesLate - 15) * 2.0;
        }

        double finalPercent =
                Math.max(basePenaltyPercent, minimumPenaltyPercent);

        return (ticketFare * finalPercent) / 100.0;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double minPercent = scanner.nextDouble();
        double ticketFare = scanner.nextDouble();
        int minutesLate = scanner.nextInt();

        try {

            BoardingPenaltyCalculator calculator =
                    new BoardingPenaltyCalculator(minPercent);

            double penalty =
                    calculator.calculatePenalty(ticketFare, minutesLate);

            System.out.printf("Rs %.1f%n", penalty);

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid Input");
        }

        scanner.close();
    }
}