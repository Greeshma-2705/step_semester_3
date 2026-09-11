package main.java.string.class_problems;
import java.util.Scanner;

public class PhoneNumberFormatter {

    public static String maskPhoneNumber(String phone) {
  
        if (phone == null || phone.length() != 10) {
            return "Invalid phone number";
        }

       
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

    
        StringBuilder sb = new StringBuilder("XXXXXX");
        sb.append(phone.substring(6)); 

        sb.insert(6, "-");

    
        return sb.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a 10-digit phone number: ");
        String inputPhone = scanner.nextLine();


        String result = maskPhoneNumber(inputPhone);


        System.out.println("Output: " + result);


        scanner.close();
    }
}
