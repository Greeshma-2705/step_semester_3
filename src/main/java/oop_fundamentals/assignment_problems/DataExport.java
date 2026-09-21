package main.java.oop_fundamentals.assignment_problems;
interface Exportable {
    String exportData();
}

class ExporterManager {
    private static int totalExports = 0;

    public static synchronized void incrementExports() {
        totalExports++;
    }

    public static int getTotalExports() {
        return totalExports;
    }

    public static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }
}

class ReportGenerator implements Exportable {
    private String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String exportData() {
        ExporterManager.incrementExports();
        return "Exported report: " + reportName;
    }
}

class UserProfile implements Exportable {
    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String exportData() {
        ExporterManager.incrementExports();
        return "Exported profile: " + username;
    }
}

public class DataExport {
    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println(r.exportData());

        UserProfile u = new UserProfile("jane_doe");
        System.out.println(u.exportData());

        System.out.println("-----------------------------------");

        Exportable ref = r;
        Exportable[] items = new Exportable[]{ref, u};

        ExporterManager.exportAll(items);

        System.out.println("Total Exports: " + ExporterManager.getTotalExports());
    }
}