import java.util.Scanner;

public class TrafficSignalStreakAnalyzer {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter traffic signal log (R/Y/G): ");
        String signals = sc.nextLine();

        if (signals.length() == 0) {
            System.out.println("No signal data entered.");
            sc.close();
            return;
        }

        char longestColor = signals.charAt(0);
        int longestStreak = 1;

        char currentColor = signals.charAt(0);
        int currentStreak = 1;

        for (int i = 1; i < signals.length(); i++) {

            if (signals.charAt(i) == currentColor) {
                currentStreak++;
            } else {
                currentColor = signals.charAt(i);
                currentStreak = 1;
            }

            if (currentStreak > longestStreak) {
                longestStreak = currentStreak;
                longestColor = currentColor;
            }
        }

        System.out.println("Longest Streak Color: " + longestColor);
        System.out.println("Longest Streak Length: " + longestStreak);

        sc.close();
    }
}