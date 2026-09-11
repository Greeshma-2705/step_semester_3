package main.java.string.class_problems;
package string.class_problems;

import java.util.Scanner;

public class TransactionValidator {

    public static String normalizeReference(String raw) {
        if (raw == null) {
            return "";
        }
        
        String trimmed = raw.trim();
        
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        
        String firstThreeUpper = trimmed.substring(0, 3).toUpperCase();
        String theRest = trimmed.substring(3);
        
        return firstThreeUpper + theRest;
    }

    public static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        String bankCode = reference.substring(0, 3);
        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String sequence = reference.substring(9, 14);

        StringBuilder formatted = new StringBuilder();
        formatted.append("[")
                 .append(bankCode)
                 .append("] DATE: ")
                 .append(day)
                 .append("/")
                 .append(month)
                 .append("/")
                 .append(year)
                 .append(" | SEQ: ")
                 .append(sequence);

        return formatted.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String normalized = normalizeReference(input);
            String result = validateAndFormat(normalized);
            System.out.println(result);
        }
        
        scanner.close();
    }
}