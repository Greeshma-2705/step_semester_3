package main.java.oop_fundamentals.assignment_problems;
interface Insurable {
    String getInsuranceInfo();
}

abstract class ServiceableVehicle {
    private double mileage = 0.0;

    public abstract String performMaintenance();

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km < 0) {
            return;
        }
        this.mileage += km;
    }

    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            Insurable insurableVehicle = (Insurable) v;
            return insurableVehicle.getInsuranceInfo();
        }
        return "No insurance record exists";
    }
}

class Forklift extends ServiceableVehicle implements Insurable {
    private String assetTag;

    public Forklift(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getAssetTag() {
        return assetTag;
    }

    @Override
    public String performMaintenance() {
        return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
    }

    @Override
    public String getInsuranceInfo() {
        return "Insured under fleet policy - Asset " + assetTag;
    }
}

class HeavyDutyForklift extends Forklift {

    public HeavyDutyForklift(String assetTag) {
        super(assetTag);
    }

    @Override
    public String performMaintenance() {
        return super.performMaintenance() + " | high-pressure hydraulic check complete";
    }
}

public class FleetTracker {
    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("Forklift Mileage: " + f.getMileage());
        System.out.println("Forklift Maintenance: " + f.performMaintenance());

        System.out.println("-----------------------------------");

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        hd.addMileage(350);
        System.out.println("Heavy Duty Forklift Mileage: " + hd.getMileage());
        System.out.println("Heavy Duty Maintenance: " + hd.performMaintenance());

        System.out.println("-----------------------------------");

        System.out.println("Forklift Insurance: " + ServiceableVehicle.getInsuranceIfApplicable(f));
        System.out.println("Heavy Duty Insurance: " + ServiceableVehicle.getInsuranceIfApplicable(hd));
    }
}