package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;

public class SettlementEngine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String code1 = scanner.nextLine();
        String code2 = scanner.nextLine();
        String code3 = scanner.nextLine();

        System.out.println(RaceEntry.isValidDiscountCode(code1));
        System.out.println(RaceEntry.isValidDiscountCode(code2));
        System.out.println(RaceEntry.isValidDiscountCode(code3));

        String eBib = scanner.nextLine();
        double eFee = scanner.nextDouble();
        scanner.nextLine();
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry(eBib, eFee);

        String tBib = scanner.nextLine();
        double tFee = scanner.nextDouble();
        scanner.nextLine();
        RelayTeamEntry relayEntry = new RelayTeamEntry(tBib, tFee);

        double paymentAmount = scanner.nextDouble();
        scanner.nextLine();
        String paymentMode = scanner.nextLine();
        eliteEntry.pay(paymentAmount, paymentMode);

        RaceEntry[] batch = { eliteEntry, null, relayEntry };
        System.out.println(RaceEntry.settleNight(batch));

        System.out.println(RaceEntry.getBibCounter());
        scanner.close();
    }
}

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected final String entryCode;
    
    private static int bibCounter = 0;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        bibCounter++;
        this.entryCode = "CODE-" + bibCounter;
    }

    public void pay(double amount) {
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2)) || !Character.isDigit(code.charAt(3))) {
            return false;
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
            } else {
                processed++;
                if (entry instanceof RelayTeamEntry) {
                    relay++;
                } else {
                    individual++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }
}

class RunnerEntry extends RaceEntry {
    public RunnerEntry(String bibNumber, double entryFee) {
        super(bibNumber, entryFee);
    }
}

class EliteRunnerEntry extends RunnerEntry {
    public EliteRunnerEntry(String bibNumber, double entryFee) {
        super(bibNumber, entryFee);
    }
}

class RelayTeamEntry extends RaceEntry {
    public RelayTeamEntry(String bibNumber, double entryFee) {
        super(bibNumber, entryFee);
    }
}