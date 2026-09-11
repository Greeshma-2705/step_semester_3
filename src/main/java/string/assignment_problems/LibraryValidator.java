package main.java.string.assignment_problems;
import java.util.Scanner;

public class LibraryValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String rawInput = scanner.nextLine();
            String normalized = normalizeCode(rawInput);
            String result = validateAndFormat(normalized);
            System.out.println(result);
        }
        scanner.close();
    }

    public static String normalizeCode(String raw) {
        if (raw == null) return "";
        String trimmed = raw.trim();
        if (trimmed.length() <= 3) {
            return trimmed.toUpperCase();
        }
        String prefix = trimmed.substring(0, 3).toUpperCase();
        String remainder = trimmed.substring(3);
        return prefix + remainder;
    }

    public static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: wrong length";
        }
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }
        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }
        String pubCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);
        
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(pubCode).append("] ")
          .append("YEAR: ").append(year).append(" | ")
          .append("CATALOG: ").append(catalog);
        return sb.toString();
    }
}