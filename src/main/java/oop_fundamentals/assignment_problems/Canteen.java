import java.util.*;

public class Canteen implements Comparable<Canteen> {
    private final String canteenCode;
    private final String canteenName;
    private final int trustScore;

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3); // default trustScore = 3
    }

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    @Override
    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }
        int codeCompare = this.canteenCode.compareTo(other.canteenCode);
        if (codeCompare != 0) {
            return codeCompare;
        }
        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    public static void rankCanteens(Canteen[] canteens) {
        if (canteens == null || canteens.length <= 1) return;
        Arrays.sort(canteens);
    }

    @Override
    public String toString() {
        return String.format("{\"%s\", \"%s\", %d}", canteenCode, canteenName, trustScore);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;

        String inputLine = scanner.nextLine().trim();
        if (inputLine.equals("[]")) {
            System.out.println("[]");
            return;
        }

        String cleaned = inputLine.replaceAll("[\\[\\]]", "");
        String[] objects = cleaned.split("(?<=}),\\s*(?=\\{)");

        Canteen[] canteens = new Canteen[objects.length];

        for (int i = 0; i < objects.length; i++) {
            String obj = objects[i].replaceAll("[{}]", "");
            String[] fields = obj.split(",", -1);

            String code = fields[0].replaceAll("\"", "").trim();
            String name = fields[1].replaceAll("\"", "").trim();

            if (fields.length >= 3 && !fields[2].trim().isEmpty()) {
                int score = Integer.parseInt(fields[2].trim());
                canteens[i] = new Canteen(code, name, score);
            } else {
                canteens[i] = new Canteen(code, name);
            }
        }

        rankCanteens(canteens);

        System.out.println(Arrays.toString(canteens));
        scanner.close();
    }
}
