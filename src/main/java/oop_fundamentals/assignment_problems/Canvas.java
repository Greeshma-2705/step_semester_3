package main.java.oop_fundamentals.assignment_problems;
abstract class Shape {
    private static int counter = 0;
    private final String shapeId;

    public Shape() {
        counter++;
        this.shapeId = "SHAPE_" + counter;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
    }

    public void scale(double xFactor, double yFactor) {
    }

    public String getShapeId() {
        return shapeId;
    }

    public static void printArea(Shape s) {
        System.out.printf("Shape ID: %s | Area: %.2f%n", s.getShapeId(), s.calculateArea());
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        this.radius *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        this.radius *= (xFactor + yFactor) / 2.0;
    }

    public double getRadius() {
        return radius;
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        super();
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        this.side *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        this.side *= xFactor;
    }

    public double getSide() {
        return side;
    }
}

public class Canvas {
    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.println("Circle ID: " + c.getShapeId());
        System.out.printf("Circle Area: %.2f%n", c.calculateArea());

        System.out.println("-----------------------------------");

        SquareShape sq = new SquareShape(4.0);
        System.out.println("Square ID: " + sq.getShapeId());
        System.out.println("Initial Square Area: " + sq.calculateArea());

        sq.scale(2.0);
        System.out.println("Scaled Square Area (scale 2.0): " + sq.calculateArea());

        System.out.println("-----------------------------------");

        System.out.print("Printing via static printArea(Shape): ");
        Shape.printArea(c);

        System.out.print("Printing via static printArea(Shape): ");
        Shape.printArea(sq);
    }
}