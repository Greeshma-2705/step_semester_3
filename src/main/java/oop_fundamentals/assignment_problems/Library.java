package assignment_problems;
class LibraryMember {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    // Constructor 1
    public LibraryMember() {
        this(null, null);
    }

    // Constructor 2
    public LibraryMember(String name) {
        this(null, name);
    }

    // Constructor 3
    public LibraryMember(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
    }

    // Getter for membership ID
    public String getMembershipId() {
        return membershipId;
    }

    // Write-once membership ID
    public void setMembershipId(String id) {
        if (membershipId == null) {
            membershipId = id;
        }
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Boolean getter
    public boolean isPremiumMember() {
        return premiumMember;
    }

    // Boolean setter
    public void setPremiumMember(boolean premiumMember) {
        this.premiumMember = premiumMember;
    }

    // Write-only security answer
    public void setSecurityAnswer(String answer) {
        if (answer == null) {
            securityAnswer = null;
        } else {
            securityAnswer = Integer.toHexString(answer.hashCode());
        }
    }
}

public class Library {
    public static void main(String[] args) {
        LibraryMember member = new LibraryMember();
        member.setName("Alice");
        member.setMembershipId("LIB1001");
        member.setMembershipId("LIB9999"); // ignored
        member.setPremiumMember(true);
        member.setSecurityAnswer("myAnswer123");

        System.out.println("Name: " + member.getName());
        System.out.println("Membership ID: " + member.getMembershipId());
        System.out.println("Premium Member: " + member.isPremiumMember());
        System.out.println("Security answer is write-only.");

        // Constructor chaining test
        LibraryMember member2 = new LibraryMember("Bob");
        System.out.println("Second member: " + member2.getName());

        LibraryMember member3 = new LibraryMember("LIB3001", "Charlie");
        System.out.println("Third member ID: " + member3.getMembershipId());
    }
}
