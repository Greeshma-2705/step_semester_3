import java.util.Arrays;

public class Latereg {

    static class EventTicket {
        protected double basePrice;
        protected double balanceDue;
        private double[] lateFeeHistory;
        private int lateFeeCount;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
            this.lateFeeHistory = new double[10];
            this.lateFeeCount = 0;
        }

        public void pay(double amount) {
            this.balanceDue -= amount;
        }

        public double getBalanceDue() {
            return this.balanceDue;
        }

        protected void applyLateFee(double amount) {
            this.balanceDue += amount;
            if (lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
            }
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
    }

    static class WorkshopTicket extends EventTicket {
        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue()); // 200.0

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history)); // [200.0]

        // Attempting to tamper with returned array
        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory())); // [200.0]
    }
}