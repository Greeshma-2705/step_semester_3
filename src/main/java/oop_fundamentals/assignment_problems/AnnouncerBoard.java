package main.java.oop_fundamentals.assignment_problems;

import java.util.Scanner;

public class AnnouncerBoard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String rBib = scanner.nextLine();
        double rFee = scanner.nextDouble();
        scanner.nextLine();
        String rCat = scanner.nextLine();
        RunnerEntry runnerEntry = new RunnerEntry(rBib, rFee, rCat);

        String tBib = scanner.nextLine();
        double tFee = scanner.nextDouble();
        scanner.nextLine();
        int tSize = scanner.nextInt();
        scanner.nextLine();
        RelayTeamEntry relayEntry = new RelayTeamEntry(tBib, tFee, tSize);

        RaceEntry[] fleet = { runnerEntry, relayEntry };
        System.out.println(RaceEntry.announceAll(fleet));

        String pBib = scanner.nextLine();
        double pFee = scanner.nextDouble();
        scanner.nextLine();

        try {
            RaceEntry plain = new RaceEntry(pBib, pFee);
            RelayTeamEntry bad = (RelayTeamEntry) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
        scanner.close();
    }
}

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + entryFee;
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder sb = new StringBuilder();
        for (RaceEntry entry : entries) {
            sb.append(entry.announce());
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry team = (RelayTeamEntry) entry;
                sb.append(" [Team size via downcast: ").append(team.getTeamSize()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + entryFee;
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return this.teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + entryFee;
    }
}