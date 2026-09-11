import java.util.Scanner;
public class TypingSpeedAccuracyChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter original text: ");
        String original = sc.nextLine();
        System.out.print("Enter typed text: ");
         String typed = sc.nextLine();
         if (original.length() != typed.length()) {
            System.out.println("Error: Both strings must have the same length.");
             sc.close();
             return;
            }
            int matches = 0;
             int firstMismatch = -1;
             for (int i = 0; i < original.length(); i++) { 
                if (original.charAt(i) == typed.charAt(i)) {
                    matches++;
                } else if (firstMismatch == -1) {
                    firstMismatch = i;
                }
            }
            double accuracy = (matches * 100.0) / original.length();
            System.out.println("Matching Characters: " + matches);
            if (firstMismatch != -1) {
                System.out.println("First Mismatch Position: " + (firstMismatch + 1));
                System.out.println("Expected Character: "
                 + original.charAt(firstMismatch));
                 System.out.println("Typed Character: "
                 + typed.charAt(firstMismatch));
                } else {
                    System.out.println("No Mismatch Found");
                }
                sc.close();
            }}