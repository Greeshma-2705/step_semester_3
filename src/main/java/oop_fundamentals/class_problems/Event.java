class EventTicket {
    private String attendeeId;
    private double basePrice;
    private double balanceDue;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId");
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public void pay(double amount) {
        this.balanceDue -= amount;
    }

    public double getBalanceDue() {
        return this.balanceDue;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

// Derived Class: WorkshopTicket
class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }
}

// Main Class to execute and test the code
public class Event {
    public static void main(String[] args) {
        // Test Case 1: Rejection demonstration
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected: ST1 (ID length < 4)");
        }

        // Test Case 2: WorkshopTicket creation and payment
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println("Balance due for " + w.getAttendeeId() + " (" + w.getTrack() + "): " + w.getBalanceDue());

        // Test Case 3: Batch Registration
        String[] batchIds = {"STU1", "ST1", "STU2", " ", "STU3"};
        String batchResult = EventTicket.registerBatch(batchIds, 500);
        System.out.println(batchResult);
    }
}