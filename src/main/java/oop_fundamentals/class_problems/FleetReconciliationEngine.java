package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

class BusTicketAccount {

    protected final String bookingId;
    protected final double ticketFare;

    protected static double flatRatePenalty;

    static {
        flatRatePenalty = 15.0;
    }

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        if (minutesLate <= 0) {
            return 0.0;
        }

        return minutesLate * flatRatePenalty;
    }

    public double getTicketFare() {
        return ticketFare;
    }
}

class Sleeper extends BusTicketAccount {

    public Sleeper(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
}

public class FleetReconciliationEngine {

    private static int processedCount = 0;
    private static int nullSkippedCount = 0;
    private static int sleeperCount = 0;
    private static int regularCount = 0;
    private static double grandTotalPenalties = 0.0;

    public static void processAccount(
            BusTicketAccount account,
            double amount,
            int minutesLate) {

        if (account == null) {
            nullSkippedCount++;
            return;
        }

        if (account instanceof Sleeper) {
            sleeperCount++;
        } else {
            regularCount++;
        }

        double penalty = account.calculatePenalty(minutesLate);

        grandTotalPenalties += penalty;
        processedCount++;
    }

    public static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        processedCount = 0;
        nullSkippedCount = 0;
        sleeperCount = 0;
        regularCount = 0;
        grandTotalPenalties = 0.0;

        int minLength = Math.min(
                accounts.length,
                Math.min(amounts.length, minutesLateArray.length)
        );

        for (int i = 0; i < minLength; i++) {
            processAccount(
                    accounts[i],
                    amounts[i],
                    minutesLateArray[i]
            );
        }

        if (accounts.length > minLength) {
            nullSkippedCount += accounts.length - minLength;
        }

        printSummary();
    }

    private static void printSummary() {

        System.out.printf(
                "%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = Rs %.2f%n",
                processedCount,
                nullSkippedCount,
                sleeperCount,
                regularCount,
                grandTotalPenalties
        );
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of accounts: ");
        int n = sc.nextInt();
        sc.nextLine();

        BusTicketAccount[] accounts =
                new BusTicketAccount[n];

        double[] amounts = new double[n];
        int[] minutesLateArray = new int[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nAccount " + (i + 1));

            System.out.print("Enter account type (Sleeper/Regular): ");
            String type = sc.nextLine();

            System.out.print("Enter booking ID: ");
            String bookingId = sc.nextLine();

            System.out.print("Enter ticket fare: ");
            double fare = sc.nextDouble();

            System.out.print("Enter amount: ");
            amounts[i] = sc.nextDouble();

            System.out.print("Enter minutes late: ");
            minutesLateArray[i] = sc.nextInt();

            sc.nextLine();

            if (type.equalsIgnoreCase("Sleeper")) {
                accounts[i] =
                        new Sleeper(bookingId, fare);
            } else {
                accounts[i] =
                        new BusTicketAccount(bookingId, fare);
            }
        }

        System.out.println(
                "\n--- Running Depot Fleet Reconciliation ---"
        );

        processBatch(
                accounts,
                amounts,
                minutesLateArray
        );

        sc.close();
    }
}