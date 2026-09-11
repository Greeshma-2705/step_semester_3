package class_problems;
public class Record {

    static class AccessRuleEngine {

        public static String classifyAccess(
                String fieldModifier,
                String accessorContext) {

            if (fieldModifier.equals("private")) {
                return accessorContext.equals("SAME_CLASS")
                        ? "ALLOWED"
                        : "DENIED";
            }

            if (fieldModifier.equals("default")) {
                return (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED"
                        : "DENIED";
            }

            if (fieldModifier.equals("protected")) {
                return (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED"
                        : "DENIED";
            }

            if (fieldModifier.equals("public")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        public static String summarizeBatch(String[][] attempts) {

            int allowed = 0;
            int denied = 0;

            for (String[] attempt : attempts) {

                String result =
                        classifyAccess(attempt[0], attempt[1]);

                if (result.equals("ALLOWED")) {
                    allowed++;
                } else {
                    denied++;
                }
            }

            return "Allowed: " + allowed
                    + " | Denied: " + denied;
        }
    }


    static class PatientRecord {

        private String patientId;
        String wardCode;
        protected double vitalsScore;
        public String facilityName;

        public PatientRecord(
                String patientId,
                String wardCode,
                double vitalsScore,
                String facilityName) {

            String id =
                    patientId == null ? "" : patientId.trim();

            if (id.isEmpty() || id.length() < 4) {
                throw new IllegalArgumentException(
                        "Invalid patientId");
            }

            this.patientId = id;
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }
    }


    public static void main(String[] args) {

        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"protected", "SAME_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                AccessRuleEngine.summarizeBatch(attempts)
        );

        PatientRecord patient =
                new PatientRecord(
                        "MT2026",
                        "WARD-A",
                        98.5,
                        "MediTrack Hospital"
                );

        System.out.println("Patient created successfully.");
        System.out.println("Ward: " + patient.wardCode);
        System.out.println("Vitals: " + patient.vitalsScore);
        System.out.println("Facility: " + patient.facilityName);

        try {
            new PatientRecord(
                    "12",
                    "WARD-B",
                    90.0,
                    "Hospital"
            );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

