package main.java.string.assignment_problems;
import java.lang.*;
import java.util.Scanner;

public class ATMPINValidator{

    public static void checkPinLength(String pin) {
        int length = pin.length();
        
        if (length != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in != null ? System.in : null);
        String inputPin = scanner.nextLine();
        checkPinLength(inputPin);
        scanner.close();
    }
}
