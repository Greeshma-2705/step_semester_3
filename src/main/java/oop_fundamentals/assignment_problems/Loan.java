package assignment_problems;
class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    static {
        System.out.println("LoanReceipt system initialized");
    }

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid member ID");
        }
        if (bookIds == null) {
            throw new IllegalArgumentException("Book IDs cannot be null");
        }
        for (String id : bookIds) {
            if (id == null || !id.matches("BK-\\d{3}")) {
                throw new IllegalArgumentException("Invalid book ID: " + id);
            }
        }
        this.memberId = memberId;
        this.bookIds = bookIds.clone(); // defensive copy
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone(); // defensive copy
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (newId == null || !newId.matches("BK-\\d{3}")) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        String[] newBooks = bookIds.clone();
        newBooks[index] = newId;
        return new LoanReceipt(memberId, newBooks);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

class CirculationProcessor {
    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0, nullSkipped = 0, referenceOnly = 0, regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }
}

public class Loan {
    public static void main(String[] args) {
        String[] books = {"BK-101", "BK-202", "BK-303"};
        LoanReceipt receipt = new LoanReceipt("LIB1001", books);

        System.out.println("Member ID: " + receipt.getMemberId());
        System.out.println("Book IDs:");
        for (String id : receipt.getBookIds()) System.out.println(id);

        // Defensive copy test
        String[] copy = receipt.getBookIds();
        copy[0] = "BK-999";
        System.out.println("\nAfter modifying returned array:");
        for (String id : receipt.getBookIds()) System.out.println(id);

        // Correct one book ID
        LoanReceipt corrected = receipt.withCorrectedBookId(1, "BK-555");
        System.out.println("\nCorrected receipt:");
        for (String id : corrected.getBookIds()) System.out.println(id);

        // Reference-only receipt
        ReferenceOnlyLoanReceipt referenceReceipt =
                new ReferenceOnlyLoanReceipt("LIB2002", new String[]{"BK-111", "BK-222"}, "ROOM-5");
        System.out.println("\nReference room: " + referenceReceipt.getRoomNumber());

        // Nightly processing
        LoanReceipt[] receipts = {receipt, null, referenceReceipt};
        String result = CirculationProcessor.processNightlyCirculation(receipts);

        System.out.println("\nNightly circulation:");
        System.out.println(result);
    }
}
