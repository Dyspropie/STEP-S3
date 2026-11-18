package week7;

/**
 * Problem 1: Basic Drawing Canvas
 * Abstract Shape class with compile-time overloaded scale() methods, static ID counter, and concrete subclasses.
 */
public class Problem1_BasicDrawingCanvas {

    public static abstract class Shape {
        private static int shapeCounter = 0;
        private final String shapeId;

        public Shape() {
            shapeCounter++;
            this.shapeId = "SHAPE-" + shapeCounter;
        }

        public abstract double calculateArea();

        // Compile-time polymorphism: uniform scaling
        public abstract void scale(double factor);

        // Compile-time polymorphism: uneven scaling
        public abstract void scale(double xFactor, double yFactor);

        public String getShapeId() {
            return shapeId;
        }
    }

    public static class CircleShape extends Shape {
        private double radius;

        public CircleShape(double radius) {
            super();
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
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
            // For circle, average the two scaling factors
            this.radius *= ((xFactor + yFactor) / 2.0);
        }

        public double getRadius() {
            return radius;
        }
    }

    public static class SquareShape extends Shape {
        private double side;

        public SquareShape(double side) {
            super();
            if (side <= 0) {
                throw new IllegalArgumentException("Side must be positive");
            }
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
            this.side *= Math.sqrt(xFactor * yFactor);
        }

        public double getSide() {
            return side;
        }
    }

    public static void printArea(Shape s) {
        if (s != null) {
            System.out.printf("Shape ID: %s | Area: %.2f%n", s.getShapeId(), s.calculateArea());
        }
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.printf("Circle area: ~%.2f%n", c.calculateArea());

        SquareShape sq = new SquareShape(4.0);
        System.out.println("Square area: " + sq.calculateArea());

        sq.scale(2.0); // Doubling the side
        System.out.println("Square area after scale(2.0): " + sq.calculateArea());

        printArea(c);
    }
}
