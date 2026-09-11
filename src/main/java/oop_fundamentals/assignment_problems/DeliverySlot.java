import java.util.Scanner;
import java.util.Set;

public class DeliverySlot {
    private final String orderId;
    private final String timeslot;

    private static final Set<String> PEAK_HOURS = Set.of(
        "12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00"
    );

    public DeliverySlot(String orderId, String timeslot) {
        this.orderId = orderId;
        this.timeslot = (timeslot == null || timeslot.strip().isEmpty()) ? "ASAP" : timeslot.strip();
    }

    public DeliverySlot(String orderId) {
        this(orderId, "ASAP");
    }

    public boolean isPeakHour() {
        return PEAK_HOURS.contains(this.timeslot);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        
        String inputLine = scanner.nextLine();
        String cleaned = inputLine.replace("new DeliverySlot", "").replaceAll("[()\"]", "");
        String[] parts = cleaned.split(",", -1);
        
        DeliverySlot slot;
        if (parts.length >= 2) {
            String id = parts[0].trim();
            String time = parts[1].trim();
            slot = new DeliverySlot(id, time);
        } else if (parts.length == 1 && !parts[0].trim().isEmpty()) {
            String id = parts[0].trim();
            slot = new DeliverySlot(id);
        } else {
            System.out.println("false");
            return;
        }
        
        System.out.println(slot.isPeakHour());
        scanner.close();
    }
}