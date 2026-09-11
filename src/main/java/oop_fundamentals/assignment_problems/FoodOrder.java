import java.util.HashSet;
import java.util.Scanner;

public class FoodOrder {
    private static int validCount = 0;
    private static int rejectedCount = 0;
    private static final HashSet<String> seenOrders = new HashSet<>();

    public FoodOrder(String studentName, String dishName) {
        if (isInvalid(studentName) || isInvalid(dishName)) {
            rejectedCount++;
            return;
        }

        String uniqueKey = studentName.strip() + " -> " + dishName.strip();

        if (seenOrders.contains(uniqueKey)) {
            rejectedCount++;
        } else {
            seenOrders.add(uniqueKey);
            validCount++;
        }
    }

    private boolean isInvalid(String str) {
        return str == null || str.strip().isEmpty();
    }

    public static void markAsDelivered() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;
        
        String inputLine = scanner.nextLine();
        String cleaned = inputLine.replaceAll("[\\[\\]]", "");
        
        if (cleaned.trim().isEmpty()) {
            System.out.println("Valid: 0 | Rejected: 0");
            return;
        }

        String[] pairs = cleaned.split("(?<=\\)),\\s*(?=\\()");
        
        for (String pair : pairs) {
            String sanitizedPair = pair.replace("(", "").replace(")", "");
            String[] parts = sanitizedPair.split(",", -1);
            
            String studentName = "";
            String dishName = "";
            
            if (parts.length > 0) {
                studentName = parts[0].replaceAll("\"", "").trim();
            }
            if (parts.length > 1) {
                dishName = parts[1].replaceAll("\"", "").trim();
            }
            
            new FoodOrder(studentName, dishName);
        }

        System.out.println("Valid: " + validCount + " | Rejected: " + rejectedCount);
        scanner.close();
    }
}