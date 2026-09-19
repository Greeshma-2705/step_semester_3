package main.java.oop_fundamentals.assignment_problems;
import java.util.Arrays;
import java.util.Scanner;

public class WithdrawalPenaltyAudit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String bib = scanner.nextLine();
        double fee = scanner.nextDouble();
        scanner.nextLine();
        String cat = scanner.nextLine();
        double payment = scanner.nextDouble();
        double lateFee = scanner.nextDouble();
        scanner.nextLine();

        RunnerEntry r = new RunnerEntry(bib, fee, cat);
        r.pay(payment);
        r.applyLateFee(lateFee);
        System.out.println(r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        
        if (history.length > 0) history[0] = 999; 
        System.out.println(Arrays.toString(r.getLateFeeHistory()));
        scanner.close();
    }
}

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    
    private double[] penaltyHistory = new double[10];
    private int penaltyCount = 0;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public void pay(double amount) {
        this.amountPaid += amount;
    }

    protected void applyLateFee(double amount) {
        if (penaltyCount < 10) {
            penaltyHistory[penaltyCount++] = amount;
        }
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(this.penaltyHistory, this.penaltyCount);
    }

    public double getBalanceDue() {
        double totalPenalty = 0;
        for (int i = 0; i < penaltyCount; i++) {
            totalPenalty += penaltyHistory[i];
        }
        return this.entryFee + totalPenalty - this.amountPaid;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}