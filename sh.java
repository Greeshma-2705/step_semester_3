import java.util.Scanner;

abstract class Shape {
    abstract double calculateArea();
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double calculateArea() {
        return length * width;
    }
}

class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    double calculateArea() {
        return 0.5 * base * height;
    }
}

public class sh {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        Shape s;

        if (choice == 1) {
            System.out.print("Enter radius: ");
            s = new Circle(sc.nextDouble());
        }
        else if (choice == 2) {
            System.out.print("Enter length: ");
            double l = sc.nextDouble();

            System.out.print("Enter width: ");
            double w = sc.nextDouble();

            s = new Rectangle(l, w);
        }
        else {
            System.out.print("Enter base: ");
            double b = sc.nextDouble();

            System.out.print("Enter height: ");
            double h = sc.nextDouble();

            s = new Triangle(b, h);
        }

        System.out.println("Area = " + s.calculateArea());
    }
}