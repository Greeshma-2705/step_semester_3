package main.java.string.class_problems;
import java.util.Scanner;

public class FileExtensionValidator {

    public static String validateFileExtension(String filename) {
       
        int lastDotIndex = filename.lastIndexOf('.');
        
        
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "Rejected — invalid file type";
        }
 
        String extension = filename.substring(lastDotIndex + 1);
        
        if (extension.equalsIgnoreCase("pdf") || 
            extension.equalsIgnoreCase("docx") || 
            extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        } else {
            return "Rejected — invalid file type";
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the filename: ");
        String userInput = scanner.nextLine();
        
        String result = validateFileExtension(userInput);
        System.out.println(result);
        

        scanner.close();
    }
}