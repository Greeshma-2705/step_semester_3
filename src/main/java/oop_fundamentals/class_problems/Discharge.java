package class_problems;
import java.util.Arrays;

// Base Immutable Class
class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    // Static block for one-time initialization if needed
    static {
        // Shared state initialization
    }

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (medicationCodes == null) {
            throw new IllegalArgumentException("Construction rejected: medicationCodes cannot be null");
        }
        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("Construction rejected: Invalid medication code " + code);
            }
        }
        this.patientId = patientId;
        // Defensive copy on intake
        this.medicationCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        // Defensive copy on access
        return Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    // Wither method returning a brand-new updated object
    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid medication index");
        }
        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException("Invalid medication code format");
        }
        String[] updatedCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
        updatedCodes[index] = newCode;
        return new DischargeSummary(this.patientId, updatedCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        if (summaries == null) {
            return "0 processed | 0 null skipped | 0 critical-care | 0 routine";
        }

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkipped++;
            } else {
                processed++;
                if (summary instanceof CriticalCareDischargeSummary) {
                    criticalCare++;
                } else {
                    routine++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + criticalCare + " critical-care | " + routine + " routine";
    }
}

// Critical Care Subclass
class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}

public class Discharge {
    public static void main(String[] args) {
        // --- Test 1: Valid DischargeSummary Creation & Defensive Copying ---
        System.out.println("--- Test 1: Defensive Copying ---");
        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("After attempting external mutation: " + d.getMedicationCodes()[0]); // MED-A
        System.out.println();

        // --- Test 2: Medication Code Validation ---
        System.out.println("--- Test 2: Validation Failure ---");
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected as expected");
        }
        System.out.println();

        // --- Test 3: Wither Pattern ---
        System.out.println("--- Test 3: Wither Pattern ---");
        DischargeSummary updated = d.withCorrectedMedication(0, "MED-Z");
        System.out.println("Original code[0]: " + d.getMedicationCodes()[0]);      // MED-A
        System.out.println("Updated object code[0]: " + updated.getMedicationCodes()[0]); // MED-Z
        System.out.println();

        // --- Test 4: Nightly Ledger Processing ---
        System.out.println("--- Test 4: processNightlyBatch ---");
        DischargeSummary[] batch = {
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };

        String result = DischargeSummary.processNightlyBatch(batch);
        System.out.println(result);
    }
}