import java.util.Scanner;

class DeliveryAccount {

    private static int totalAccountsCreated = 0;

    private final String studentId;
    private final double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        if (studentId == null || orderValue < 0) {
            throw new IllegalArgumentException("Invalid account data");
        }

        this.studentId = studentId;
        this.orderValue = orderValue;

        synchronized (DeliveryAccount.class) {
            totalAccountsCreated++;
        }
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public static synchronized int getTotalAccountsCreated() {
        return totalAccountsCreated;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getOrderValue() {
        return orderValue;
    }
}

class PremiumAccount extends DeliveryAccount {
    public PremiumAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
}

class RegularAccount extends DeliveryAccount {
    public RegularAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
}

/** ✅ Added missing class */
class SurgeFeeCalculator {
    private final double multiplier;

    public SurgeFeeCalculator(double multiplier) {
        this.multiplier = multiplier;
    }

    public double calculateSurgeFee(double orderValue, int delayMinutes) {
        // Example formula: surge fee = orderValue * multiplier * (delayMinutes / 10.0)
        return orderValue * multiplier * (delayMinutes / 10.0);
    }
}

final class ReconciliationEngine {

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        if (accounts == null || amounts == null || delayMinutesArray == null) {
            throw new IllegalArgumentException("Null batch arrays");
        }

        if (accounts.length != amounts.length ||
            accounts.length != delayMinutesArray.length) {
            System.out.println("Batch length mismatch");
            return;
        }

        int processed = 0;
        int skipped = 0;
        int premium = 0;
        int regular = 0;
        double grandTotalSurgeFees = 0.0;

        SurgeFeeCalculator batchCalculator = new SurgeFeeCalculator(1.0);

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount account = accounts[i];

            if (account == null) {
                skipped++;
                continue;
            }

            double amount = amounts[i];
            int delay = delayMinutesArray[i];

            if (amount < 0 || delay < 0) {
                skipped++;
                continue;
            }

            try {
                double surgeFee = batchCalculator.calculateSurgeFee(
                        account.getOrderValue(), delay);

                grandTotalSurgeFees += surgeFee;

                if (account instanceof PremiumAccount) {
                    premium++;
                } else {
                    regular++;
                }

                processed++;

            } catch (Exception e) {
                skipped++;
            }
        }

        System.out.println(
                processed + " processed | " +
                skipped + " skipped | " +
                premium + " premium | " +
                regular + " regular | " +
                "grand total surge fees = " +
                grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();

        DeliveryAccount[] accounts = new DeliveryAccount[n];
        double[] amounts = new double[n];
        int[] delays = new int[n];

        for (int i = 0; i < n; i++) {
            String type = scanner.next();

            if (type.equals("null")) {
                accounts[i] = null;
                amounts[i] = scanner.nextDouble();
                delays[i] = scanner.nextInt();
                continue;
            }

            String id = scanner.next();
            double val = scanner.nextDouble();

            if (type.equalsIgnoreCase("Premium")) {
                accounts[i] = new PremiumAccount(id, val);
            } else if (type.equalsIgnoreCase("Regular")) {
                accounts[i] = new RegularAccount(id, val);
            } else {
                accounts[i] = new DeliveryAccount(id, val);
            }

            amounts[i] = scanner.nextDouble();
            delays[i] = scanner.nextInt();
        }

        try {
            processBatch(accounts, amounts, delays);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Batch Data");
        }

        scanner.close();
    }
}
