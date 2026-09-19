interface Auditable {
    String auditRecord();
}
abstract class StaffMember {
    private double baseSalary;
    protected double bonusRate;
    public StaffMember(double baseSalary, double bonusRate) {
        setSalary(baseSalary);
        this.bonusRate = bonusRate;
    }
    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }
    public double getSalary() {
        return baseSalary;
    }
    public void setSalary(double baseSalary) {
        if (baseSalary >= 0) {
            this.baseSalary = baseSalary;
        }
    }
    public abstract double calculateBonus();
}
class TeamLead extends StaffMember implements Auditable {
    private int teamSize;
    public TeamLead(double baseSalary, int teamSize) {
        super(baseSalary);
        this.teamSize = teamSize;
    }
    public TeamLead(double baseSalary, double bonusRate, int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }
    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }
    @Override
    public String auditRecord() {
        return "Audit Passed: TeamLead managing " + teamSize + " members.";
    }
}
class Developer extends StaffMember {
    public Developer(double baseSalary) {
        super(baseSalary);
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }
}
public class Bonus {
    public static String getAuditIfApplicable(StaffMember s) {
        if (s instanceof Auditable) {
            Auditable auditable = (Auditable) s; 
            return auditable.auditRecord();
        }
        return "No audit required";
    }
        public static void main(String[] args) {
        TeamLead t1 = new TeamLead(60000, 5);
        System.out.println("t1 Bonus: " + t1.calculateBonus());
        TeamLead t2 = new TeamLead(60000, 0.20, 5);
        System.out.println("t2 Bonus: " + t2.calculateBonus());
        System.out.println(getAuditIfApplicable(t1));
        Developer dev = new Developer(50000);
        System.out.println(getAuditIfApplicable(dev));
    }
}