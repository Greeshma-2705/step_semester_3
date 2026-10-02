import java.util.*;

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED
}

interface ShippingType {
    double calculateCharge(double weight);
}

class StandardShipping implements ShippingType {
    @Override
    public double calculateCharge(double weight) {
        return 40.0 + (10.0 * weight);
    }
}

class ExpressShipping implements ShippingType {
    @Override
    public double calculateCharge(double weight) {
        return 80.0 + (15.0 * weight);
    }
}

class FragileShipping implements ShippingType {
    private StandardShipping standard = new StandardShipping();

    @Override
    public double calculateCharge(double weight) {
        return standard.calculateCharge(weight) + 50.0;
    }
}

interface NotificationChannel {
    void notify(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void notify(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void notify(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Parcel {
    private String parcelId;
    private double weight;
    private ShippingType shippingType;
    private ParcelStatus status;
    private List<NotificationChannel> channels = new ArrayList<>();

    public Parcel(String parcelId, double weight, ShippingType shippingType) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.shippingType = shippingType;
        this.status = ParcelStatus.BOOKED;
    }

    public String getParcelId() {
        return parcelId;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public double calculateCharge() {
        return shippingType.calculateCharge(weight);
    }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    public void updateStatus(ParcelStatus newStatus) {
        if (newStatus.ordinal() != status.ordinal() + 1) {
            System.out.println("Invalid transition: " + status + " -> " + newStatus + " is not allowed.");
            return;
        }
        this.status = newStatus;
        notifyChannels();
    }

    public boolean cancel() {
        if (this.status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + parcelId + " can be cancelled only while BOOKED.");
            return false;
        }
        System.out.println("Parcel " + parcelId + " cancelled successfully.");
        return true;
    }

    public void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(parcelId, status);
        }
    }
}

public class Swift {
    public static void main(String[] args) {
        Customer customer = new Customer("Customer");
        Parcel p101 = new Parcel("P101", 2.0, new ExpressShipping());

        p101.subscribe(new SmsChannel());
        p101.subscribe(new EmailChannel());

        System.out.printf("Parcel %s booked (Express, %.0f kg). Charge: ₹%.2f.%n", 
            p101.getParcelId(), 2.0, p101.calculateCharge());
        
        p101.notifyChannels();

        p101.updateStatus(ParcelStatus.PICKED_UP);
        p101.cancel();
        p101.updateStatus(ParcelStatus.IN_TRANSIT);
        p101.updateStatus(ParcelStatus.DELIVERED);
    }
}