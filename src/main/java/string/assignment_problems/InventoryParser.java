package main.java.string.assignment_problems;
import java.util.Scanner;
public class InventoryParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String csvLine = scanner.nextLine();
            parseInventoryRecord(csvLine);
        }
        scanner.close();
    }

    public static void parseInventoryRecord(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }
        String productName = fields[0].trim();
        String sku = fields[1].trim();
        String quantity = fields[2].trim();
        System.out.println("Product: " + productName + " | SKU: " + sku + " | Qty: " + quantity);
    }
}
