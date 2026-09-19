package main.java.oop_fundamentals.assignment_problems;
import java.util.Scanner;
public class RaceFamilyClassifier {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String rBib = scanner.nextLine();
        double rFee = scanner.nextDouble();
        scanner.nextLine();
        String rCat = scanner.nextLine();
        RunnerEntry runner = new RunnerEntry(rBib, rFee, rCat);

        String eBib = scanner.nextLine();
        double eFee = scanner.nextDouble();
        scanner.nextLine();
        String eCat = scanner.nextLine();
        double eBonus = scanner.nextDouble();
        scanner.nextLine();
        EliteRunnerEntry elite = new EliteRunnerEntry(eBib, eFee, eCat, eBonus);

        String tBib = scanner.nextLine();
        double tFee = scanner.nextDouble();
        scanner.nextLine();
        int tSize = scanner.nextInt();
        scanner.nextLine();
        RelayTeamEntry relay = new RelayTeamEntry(tBib, tFee, tSize);

        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());

        System.out.println(RaceEntry.classifyGeneration(elite));
        System.out.println(RaceEntry.classifyGeneration(relay));

        RaceEntry[] mixedField = { runner, elite, relay };
        System.out.println(RaceEntry.getTotalBalanceDue(mixedField));
        scanner.close();
    }
}

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public double getBalanceDue() {
        return this.entryFee - this.amountPaid;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Unknown Shape";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }
        return total;
    }
}

class RunnerEntry extends RaceEntry {
    protected String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + getBalanceDue();
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public String announce() {
        return "Elite Runner | Bib: " + bibNumber + " | Category: " + category + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
    }
}
