class AccessChecker {

    static String classifyAccess(String modifier, String context) {

        if (modifier.equals("private")) {
            if (context.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (modifier.equals("default")) {
            if (context.equals("SAME_CLASS")
                    || context.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (modifier.equals("protected")) {
            if (context.equals("SAME_CLASS")
                    || context.equals("SAME_PACKAGE")
                    || context.equals("SUBCLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (modifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {
                "private",
                "default",
                "protected",
                "public"
        };

        StringBuilder result = new StringBuilder();

        for (String modifier : modifiers) {

            int allowed = 0;
            int denied = 0;

            for (String[] attempt : attempts) {

                if (attempt[0].equals(modifier)) {

                    String answer =
                            classifyAccess(
                                    attempt[0],
                                    attempt[1]);

                    if (answer.equals("ALLOWED")) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }

            if (result.length() > 0) {
                result.append(" | ");
            }

            result.append(modifier)
                    .append(": ")
                    .append(allowed)
                    .append(" allowed / ")
                    .append(denied)
                    .append(" denied");
        }

        return result.toString();
    }
}


class LibraryMember {

    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    LibraryMember(
            String membershipId,
            String branchCode,
            double finesOwed,
            String displayName) {

        String id = membershipId.trim();

        if (id.length() < 4) {
            throw new IllegalArgumentException(
                    "Invalid membership ID");
        }

        this.membershipId = id;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}


public class Accesschecker {

    public static void main(String[] args) {

        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "DIFFERENT_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SUBCLASS"},
                {"protected", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                AccessChecker.summarizeByModifier(attempts));

        LibraryMember member =
                new LibraryMember(
                        "LIB1234",
                        "BR01",
                        20.50,
                        "John");

        System.out.println("Library member created successfully.");
    }
}