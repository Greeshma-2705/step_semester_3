import java.util.*;

interface IPaymentMethod {
    boolean processPayment(double amount);
}

class CreditCardPayment implements IPaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Payment via Credit Card successful.");
        return true;
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    private boolean shouldSucceed;

    public DigitalWalletPayment(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
    }

    @Override
    public boolean processPayment(double amount) {
        if (shouldSucceed) {
            System.out.println("Payment via Digital Wallet successful.");
            return true;
        } else {
            System.out.println("Payment via Digital Wallet failed.");
            return false;
        }
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class LineItem {
    private FoodItem foodItem;
    private int quantity;

    public LineItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return foodItem.getPrice() * quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Order {
    private String orderId;
    private List<LineItem> items = new ArrayList<>();
    private String status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.status = "Created";
        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        items.add(new LineItem(item, quantity));
        System.out.println("Added " + item.getName() + " (Qty " + quantity + ")");
    }

    public double calculateTotal() {
        double total = 0;
        for (LineItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void placeAndPay(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return;
        }

        System.out.println("Order placed successfully.");
        boolean paymentSuccess = paymentMethod.processPayment(calculateTotal());

        if (paymentSuccess) {
            this.status = "Paid";
            System.out.println("Order status: " + this.status);
            System.out.println("Notification: Order #" + orderId + " placed and paid.");
        } else {
            this.status = "Pending Payment";
            System.out.println("Order status: " + this.status);
            System.out.println("Notification: Order #" + orderId + " placed, awaiting payment.");
        }
    }
}

public class FoodOrder {
    public static void main(String[] args) {
        FoodItem pizza = new FoodItem("Pizza", 10.0);
        FoodItem soda = new FoodItem("Soda", 2.0);
        FoodItem burger = new FoodItem("Burger", 8.0);

        Order order1 = new Order("123");
        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        Order emptyOrder = new Order("000");
        emptyOrder.placeAndPay(new CreditCardPayment());

        order1.placeAndPay(new CreditCardPayment());

        Order order2 = new Order("124");
        order2.addItem(burger, 1);
        order2.placeAndPay(new DigitalWalletPayment(false));
    }
}