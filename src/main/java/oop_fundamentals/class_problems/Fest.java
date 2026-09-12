public class Fest {

    static class EventTicket {
        private static int counter = 1000;
        private static int ticketsIssued = 0;

        public final String ticketId;
        protected double basePrice;
        protected double balanceDue;

        public EventTicket(double basePrice) {
            counter++;
            this.ticketId = "TCK-" + counter;
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
            ticketsIssued++;
        }

        public static int getTicketsIssued() {
            return ticketsIssued;
        }

        public void pay(double amount) {
            this.balanceDue -= amount;
        }

        public void pay(double amount, String mode) {
            System.out.println("Payment mode: " + mode);
            pay(amount);
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }
    }

    static class GroupTicket extends EventTicket {
        private int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (ticket instanceof GroupTicket) {
                groupCount++;
            } else {
                individualCount++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + groupCount + " group | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        // Auto-increment ticket ID and tickets issued count
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.ticketId); // TCK-1001
        System.out.println(EventTicket.getTicketsIssued()); // 1

        // Promo code checks
        System.out.println(EventTicket.isValidPromoCode("F123A")); // true
        System.out.println(EventTicket.isValidPromoCode("F12A"));  // false
        System.out.println(EventTicket.isValidPromoCode("X123A")); // false

        // Overloaded payment methods
        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue()); // 100.0

        // Nightly settlement processing
        EventTicket[] settlementBatch = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };
        System.out.println(processNightlySettlement(settlementBatch));
        // 2 processed | 1 null skipped | 1 group | 1 individual
    }
}