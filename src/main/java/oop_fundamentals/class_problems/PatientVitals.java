package class_problems;
import java.util.LinkedHashMap;
import java.util.Map;

class PatientRecord {
    private String patientId;
    String wardCode;            // package-private / default
    protected double vitalsScore;
    public String facilityName;

    // Parameterized constructor with validation
    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("Construction rejected: Invalid patientId");
        }
        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }

    public String getPatientId() {
        return patientId;
    }
}

class AccessRuleEngine {

    /**
     * Classifies field access as ALLOWED or DENIED across all accessor contexts.
     */
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "public":
                return "ALLOWED";

            case "protected":
                if (accessorContext.equals("SAME_CLASS") || 
                    accessorContext.equals("SAME_PACKAGE") || 
                    accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "default":
                if (accessorContext.equals("SAME_CLASS") || 
                    accessorContext.equals("SAME_PACKAGE")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "private":
                if (accessorContext.equals("SAME_CLASS")) {
                    return "ALLOWED";
                }
                return "DENIED";

            default:
                return "DENIED";
        }
    }

    /**
     * Converts an underscore-separated uppercase context code into Title Case.
     */
    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) {
            return "";
        }

        String[] parts = accessorContext.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String word = parts[i].toLowerCase();
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1));
                if (i < parts.length - 1) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Summarizes access permissions grouped by field modifier.
     */
    public static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        Map<String, int[]> counts = new LinkedHashMap<>();

        for (String mod : modifiers) {
            counts.put(mod, new int[2]); // [0] = allowed, [1] = denied
        }

        for (String[] attempt : attempts) {
            String mod = attempt[0];
            String context = attempt[1];

            if (counts.containsKey(mod)) {
                String result = classifyAccess(mod, context);
                if ("ALLOWED".equals(result)) {
                    counts.get(mod)[0]++;
                } else {
                    counts.get(mod)[1]++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < modifiers.length; i++) {
            String mod = modifiers[i];
            int[] res = counts.get(mod);
            sb.append(mod).append(": ").append(res[0]).append(" allowed / ").append(res[1]).append(" denied");
            if (i < modifiers.length - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    /**
     * Summarizes total allowed vs denied attempts for a batch.
     */
    public static String summarizeBatch(String[][] attempts) {
        int allowedCount = 0;
        int deniedCount = 0;

        for (String[] attempt : attempts) {
            String mod = attempt[0];
            String context = attempt[1];

            String result = classifyAccess(mod, context);
            if ("ALLOWED".equals(result)) {
                allowedCount++;
            } else {
                deniedCount++;
            }
        }

        return "Allowed: " + allowedCount + " | Denied: " + deniedCount;
    }
}

public class PatientVitals {
    public static void main(String[] args) {
        // --- Test 1: classifyAccess with Cross-Package Inheritance ---
        System.out.println("--- Test 1: classifyAccess ---");
        System.out.println(AccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));    // ALLOWED
        System.out.println(AccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")); // DENIED
        System.out.println(AccessRuleEngine.classifyAccess("default", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));     // DENIED
        System.out.println(AccessRuleEngine.classifyAccess("public", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));   // ALLOWED
        System.out.println();

        // --- Test 2: describeContext Formatting ---
        System.out.println("--- Test 2: describeContext ---");
        System.out.println(AccessRuleEngine.describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(AccessRuleEngine.describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(AccessRuleEngine.describeContext("SAME_PACKAGE"));
        System.out.println();

        // --- Test 3: Batch Summaries ---
        System.out.println("--- Test 3: Batch Summaries ---");
        String[][] attempts = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(AccessRuleEngine.summarizeBatch(attempts)); // Allowed: 2 | Denied: 1
        System.out.println(AccessRuleEngine.summarizeByModifier(attempts));
        System.out.println();

        // --- Test 4: PatientRecord Validation ---
        System.out.println("--- Test 4: PatientRecord Validation ---");
        try {
            PatientRecord record1 = new PatientRecord("MT94", "W3", 98.2, "MediTrack Central");
            System.out.println("Successfully created record for ID: " + record1.getPatientId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            PatientRecord record2 = new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
            System.out.println("Successfully created record for ID: " + record2.getPatientId());
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Expected Error: " + e.getMessage());
        }
    }
}