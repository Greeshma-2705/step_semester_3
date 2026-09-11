import java.util.Scanner;

public class WarehouseInventoryBalancer {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of sections: ");
        int n = sc.nextInt();

        int[] sectionA = new int[n];
        int[] sectionB = new int[n];

        System.out.println("Enter quantities for Section A:");
        for (int i = 0; i < n; i++) {
            System.out.print("Section " + (i + 1) + ": ");
            sectionA[i] = sc.nextInt();
        }

        System.out.println("Enter quantities for Section B:");
        for (int i = 0; i < n; i++) {
            System.out.print("Section " + (i + 1) + ": ");
            sectionB[i] = sc.nextInt();
        }

        int totalA = 0;
        int totalB = 0;

        int maxQuantity = sectionA[0];
        int maxSection = 1;
        String maxArray = "Section A";

        for (int i = 0; i < n; i++) {

            totalA += sectionA[i];
            totalB += sectionB[i];

            if (sectionA[i] > maxQuantity) {
                maxQuantity = sectionA[i];
                maxSection = i + 1;
                maxArray = "Section A";
            }

            if (sectionB[i] > maxQuantity) {
                maxQuantity = sectionB[i];
                maxSection = i + 1;
                maxArray = "Section B";
            }
        }

        System.out.println("\nTotal Quantity in Section A: " + totalA);
        System.out.println("Total Quantity in Section B: " + totalB);

        if (totalA == totalB) {
            System.out.println("Inventory Status: Balanced");
        } else {
            System.out.println("Inventory Status: Not Balanced");
        }

        System.out.println("Maximum Quantity: " + maxQuantity);
        System.out.println("Found in: " + maxArray);
        System.out.println("Section/Index: " + maxSection);

        sc.close();
    }
}