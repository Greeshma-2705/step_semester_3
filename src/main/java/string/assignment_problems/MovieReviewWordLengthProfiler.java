import java.util.Scanner;

public class MovieReviewWordLengthProfiler {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie review: ");
        String review = sc.nextLine();

        String[] words = review.trim().split("\\s+");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {

            int length = word.length();

            if (length >= 1 && length <= 4) {
                shortCount++;
            } else if (length >= 5 && length <= 8) {
                mediumCount++;
            } else if (length >= 9) {
                longCount++;
            }
        }

        System.out.println("\nWord Length Profile");
        System.out.println("Short words (1-4): " + shortCount);
        System.out.println("Medium words (5-8): " + mediumCount);
        System.out.println("Long words (9+): " + longCount);

        sc.close();
    }
}