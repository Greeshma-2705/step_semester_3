package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;

public class RaceEntryValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            String singleBib = scanner.nextLine();
            double singleFee = scanner.nextDouble();
            scanner.nextLine();
            new RaceEntry(singleBib, singleFee);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        String runnerBib = scanner.nextLine();
        double runnerFee = scanner.nextDouble();
        scanner.nextLine();
        String runnerCategory = scanner.nextLine();
        double payment = scanner.nextDouble();
        scanner.nextLine();

        RunnerEntry r = new RunnerEntry(runnerBib, runnerFee, runnerCategory);
        r.pay(payment);
        System.out.println(r.getBalanceDue());

        int batchSize = scanner.nextInt();
        scanner.nextLine();
        String[] batch = new String[batchSize];
        for (int i = 0; i < batchSize; i++) {
            batch[i] = scanner.nextLine();
        }
        double batchFee = scanner.nextDouble();
        scanner.nextLine();

        System.out.println(RaceEntry.registerBatch(batch, batchFee));
        scanner.close();
    }
}

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid bib number.");
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public void pay(double amount) {
        this.amountPaid += amount;
    }

    public double getBalanceDue() {
        return this.entryFee - this.amountPaid;
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}
