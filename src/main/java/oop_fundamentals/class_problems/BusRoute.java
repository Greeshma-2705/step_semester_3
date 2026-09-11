package main.java.oop_fundamentals.class_problems;
import java.util.Scanner;

public class BusRoute implements Comparable<BusRoute> {

    private String routeCode;
    private String routeName;
    private int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0);
    }

    @Override
    public int compareTo(BusRoute other) {

        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }

        int codeCompareIgnoreCase =
                this.routeCode.compareToIgnoreCase(other.routeCode);

        if (codeCompareIgnoreCase != 0) {
            return codeCompareIgnoreCase;
        }

        int codeCompareExact =
                this.routeCode.compareTo(other.routeCode);

        if (codeCompareExact != 0) {
            return codeCompareExact;
        }

        return this.routeName.compareTo(other.routeName);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {

        if (routes == null || routes.length <= 1) {
            return routes;
        }

        for (int i = 1; i < routes.length; i++) {

            BusRoute key = routes[i];
            int j = i - 1;

            while (j >= 0 && routes[j].compareTo(key) > 0) {
                routes[j + 1] = routes[j];
                j--;
            }

            routes[j + 1] = key;
        }

        return routes;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of routes: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        BusRoute[] routes = new BusRoute[n];

        System.out.println("Enter route details as:");
        System.out.println("RouteCode, RouteName, Priority");

        for (int i = 0; i < n; i++) {

            String line = scanner.nextLine();

            String[] tokens = line.split(",", -1);

            String code = tokens[0].trim();
            String name = tokens[1].trim();

            if (tokens.length > 2 && !tokens[2].trim().isEmpty()) {

                int priority = Integer.parseInt(tokens[2].trim());

                routes[i] =
                        new BusRoute(code, name, priority);

            } else {

                routes[i] =
                        new BusRoute(code, name);
            }
        }

        rankRoutes(routes);

        System.out.print("[");

        for (int i = 0; i < routes.length; i++) {

            System.out.print("\"" + routes[i].routeCode + "\"");

            if (i < routes.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");

        scanner.close();
    }
}