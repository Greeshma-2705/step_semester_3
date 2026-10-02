import java.util.*;

interface PricingPlan {
    double calculatePrice(double basePrice);
}

class DayScholarPlan implements PricingPlan {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice;
    }
}

class HostellerPlan implements PricingPlan {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.80;
    }
}

class Transaction {
    private String description;
    private double amount;
    private boolean isRefunded;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.isRefunded = false;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isRefunded() {
        return isRefunded;
    }

    public void setRefunded(boolean refunded) {
        isRefunded = refunded;
    }
}

class SmartCard {
    private String cardId;
    private PricingPlan plan;
    private boolean isBlocked;
    private List<Transaction> transactions;

    public SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
        this.isBlocked = false;
        this.transactions = new ArrayList<>();
    }

    public String getCardId() {
        return cardId;
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void block() {
        this.isBlocked = true;
    }

    public void unblock() {
        this.isBlocked = false;
    }

    public boolean topUp(double amount) {
        if (isBlocked) {
            System.out.println("Top-up failed: Card " + cardId + " is blocked.");
            return false;
        }
        if (amount < 100) {
            System.out.println("Top-up failed: Minimum top-up amount is ₹100.");
            return false;
        }
        if (getBalance() + amount > 5000) {
            System.out.println("Top-up failed: Balance cannot exceed ₹5,000.");
            return false;
        }

        transactions.add(new Transaction("Top-up", amount));
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, getBalance());
        return true;
    }

    public boolean purchase(String item, double basePrice) {
        if (isBlocked) {
            System.out.println("Purchase failed: Card " + cardId + " is blocked.");
            return false;
        }

        double finalPrice = plan.calculatePrice(basePrice);
        if (getBalance() < finalPrice) {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n", 
                finalPrice, getBalance());
            return false;
        }

        transactions.add(new Transaction(item, -finalPrice));
        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n", item, finalPrice, getBalance());
        return true;
    }

    public boolean refund(String item) {
        if (isBlocked) {
            System.out.println("Refund failed: Card " + cardId + " is blocked.");
            return false;
        }

        Transaction targetTx = null;
        for (Transaction t : transactions) {
            if (t.getDescription().equals(item) && t.getAmount() < 0) {
                targetTx = t;
                break;
            }
        }

        if (targetTx == null) {
            System.out.println("Refund rejected: Item not found.");
            return false;
        }

        if (targetTx.isRefunded()) {
            System.out.println("Refund rejected: " + item + " has already been refunded.");
            return false;
        }

        double refundAmount = Math.abs(targetTx.getAmount());
        targetTx.setRefunded(true);
        transactions.add(new Transaction("Refund: " + item, refundAmount));

        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n", 
            refundAmount, item, getBalance());
        return true;
    }

    public void printMiniStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mini-statement for ").append(cardId).append(": ");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            double amt = t.getAmount();
            if (amt > 0) {
                sb.append(String.format("+%.2f", amt));
            } else {
                sb.append(String.format("-%.2f", Math.abs(amt)));
            }
            if (i < transactions.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(String.format(" = ₹%.2f.", getBalance()));
        System.out.println(sb.toString());
    }
}

public class CampusCanteen {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());

        card.topUp(500);
        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Large Meal", 400);

        card.refund("Veg Thali");
        card.refund("Veg Thali");

        card.printMiniStatement();
    }
}