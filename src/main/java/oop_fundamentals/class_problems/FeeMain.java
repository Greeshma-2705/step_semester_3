package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.amountPaid += amount;
        }
    }

    public double getDue() {
        return this.totalFee - this.amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    public void payInTwoInstallments(double amount) {
        pay(amount);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    public ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    public double doubleEffectiveDue() {
        double currentDue = getDue();
        double scholarshipAmount = currentDue * (scholarshipPercent / 100.0);
        return currentDue - scholarshipAmount;
    }
}

public class FeeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double plainTotal = scanner.nextDouble();
        double plainPaid = scanner.nextDouble();
        FeeAccount plainAcc = new FeeAccount("REG01", plainTotal, plainPaid);

        double hostelTotal = scanner.nextDouble();
        double hostelPaid = scanner.nextDouble();
        double hostelInstallment = scanner.nextDouble();
        HostelFeeAccount hostelAcc = new HostelFeeAccount("REG02", hostelTotal, hostelPaid);
        hostelAcc.payInTwoInstallments(hostelInstallment);

        double scholarTotal = scanner.nextDouble();
        double scholarPaid = scanner.nextDouble();
        double scholarPercent = scanner.nextDouble();
        ScholarshipFeeAccount scholarAcc = new ScholarshipFeeAccount("REG03", scholarTotal, scholarPaid, scholarPercent);

        FeeAccount[] accounts = { plainAcc, hostelAcc, scholarAcc };

        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                System.out.println("Scholarship account effective due: Rs " + ((ScholarshipFeeAccount) acc).doubleEffectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + acc.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }

        scanner.close();
    }
}
