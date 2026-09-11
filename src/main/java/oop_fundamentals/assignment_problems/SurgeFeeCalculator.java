import java.util.Scanner;

public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay must be non-negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double tieredRateAccumulator;
        if (delayMinutes <= 5) {
            tieredRateAccumulator = delayMinutes * 0.005;
        } else if (delayMinutes <= 15) {
            tieredRateAccumulator = 0.025 + ((delayMinutes - 5) * 0.01);
        } else {
            tieredRateAccumulator = 0.125 + ((delayMinutes - 15) * 0.02);
        }

        double tieredFee = tieredRateAccumulator * orderValue;
        double minimumFloorFee = (this.minimumSurgePercent / 100.0) * orderValue;

        return Math.max(tieredFee, minimumFloorFee);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid Input");
            scanner.close();
            return;
        }

        double minSurgePercent = scanner.nextDouble();
        double orderValue = scanner.nextDouble();
        int delayMinutes = scanner.nextInt();

        try {
            SurgeFeeCalculator calculator = new SurgeFeeCalculator(minSurgePercent);
            double fee = calculator.calculateSurgeFee(orderValue, delayMinutes);
            System.out.printf("Rs %.1f%n", fee);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input");
        }

        scanner.close();
    }
}
