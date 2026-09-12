public class Nightly {

    static class EventTicket {
        protected double basePrice;
        protected double balanceDue;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public String printTicket() {
            return "Standard | Balance: " + balanceDue;
        }
    }

    static class WorkshopTicket extends EventTicket {
        private String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + balanceDue;
        }
    }

    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tickets.length; i++) {
            EventTicket ticket = tickets[i];
            sb.append(ticket.printTicket());
            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket wt = (WorkshopTicket) ticket;
                sb.append(" [Track via downcast: ").append(wt.getTrack()).append("]");
            }
            if (i < tickets.length - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };

        System.out.println(batchPrint(tickets));

        // Unsafe downcast demonstration
        EventTicket plain = new EventTicket(500);
        try {
            WorkshopTicket bad = (WorkshopTicket) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException caught as expected when casting plain ticket.");
        }
    }
}