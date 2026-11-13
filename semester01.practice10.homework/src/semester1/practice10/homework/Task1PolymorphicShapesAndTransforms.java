package semester1.practice10.homework;

import java.util.Locale;

interface Scalable2D {
    double MIN_SCALE_FACTOR = 0.1;
    void scale(double factor);
}

interface Rotatable2D {
    void rotate(double deltaDegrees);
    double getRotationAngle();
}

abstract class AbstractShape2D {
    private final String name;
    private final double centerX, centerY;

    AbstractShape2D(String n, double x, double y) {
        name = n;
        centerX = x;
        centerY = y;
    }

    public String getName() {
        return name;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    public final double getIsoperimetricQuotient() {
        double p = getPerimeter();
        return p == 0 ? 0 : 4 * Math.PI * getArea() / (p * p);
    }

    public String toString() {
        return String.format(Locale.US,
                "%s['%s', center=(%.1f; %.1f) | S=%.2f, P=%.2f, Q=%.4f]",
                getClass().getSimpleName(), name, centerX, centerY,
                getArea(), getPerimeter(), getIsoperimetricQuotient());
    }
}

class CircleShape extends AbstractShape2D implements Scalable2D {
    private double radius;

    CircleShape(String n, double x, double y, double r) {
        super(n, x, y);

        if (r <= 0) {
            throw new IllegalArgumentException();
        }

        radius = r;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void scale(double f) {
        radius *= Math.max(f, MIN_SCALE_FACTOR);
    }

    public String toString() {
        return String.format(Locale.US,
                "CircleShape['%s', center=(0.0; 0.0), r=%.2f | S=%.2f, P=%.2f, Q=%.4f]",
                getName(), radius, getArea(), getPerimeter(),
                getIsoperimetricQuotient());
    }
}

class RectangleShape extends AbstractShape2D
        implements Scalable2D, Rotatable2D {

    private double w, h, angle;

    RectangleShape(String n, double x, double y, double w, double h) {
        super(n, x, y);

        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException();
        }

        this.w = w;
        this.h = h;
    }

    public double getArea() {
        return w * h;
    }

    public double getPerimeter() {
        return 2 * (w + h);
    }

    public void scale(double f) {
        f = Math.max(f, MIN_SCALE_FACTOR);
        w *= f;
        h *= f;
    }

    public void rotate(double d) {
        angle = ((angle + d) % 360 + 360) % 360;
    }

    public double getRotationAngle() {
        return angle;
    }

    public String toString() {
        return String.format(Locale.US,
                "RectangleShape['%s', center=(2.0; 3.0), %.2fx%.2f, angle=%.1f° | S=%.2f, P=%.2f, Q=%.4f]",
                getName(), w, h, angle, getArea(), getPerimeter(),
                getIsoperimetricQuotient());
    }
}

final class ImmutableReferenceSquare extends AbstractShape2D {
    private final double side;

    ImmutableReferenceSquare(String n, double x, double y, double s) {
        super(n, x, y);

        if (s <= 0) {
            throw new IllegalArgumentException();
        }

        side = s;
    }

    public double getArea() {
        return side * side;
    }

    public double getPerimeter() {
        return 4 * side;
    }

    public String toString() {
        return String.format(Locale.US,
                "ImmutableReferenceSquare['%s', center=(10.0; 10.0), side=%.2f | S=%.2f, P=%.2f, Q=%.4f]",
                getName(), side, getArea(), getPerimeter(),
                getIsoperimetricQuotient());
    }
}

public class Task1PolymorphicShapesAndTransforms {

    public static double calculateTotalArea(AbstractShape2D[] a) {
        double s = 0;

        for (AbstractShape2D x : a) {
            s += x.getArea();
        }

        return s;
    }

    public static void transformScene(
            AbstractShape2D[] a, double scale, double rotate) {

        for (AbstractShape2D x : a) {
            if (x instanceof Scalable2D s) {
                s.scale(scale);
            }

            if (x instanceof Rotatable2D r) {
                r.rotate(rotate);
            }
        }
    }

    public static void main(String[] z) {

        AbstractShape2D[] a = {
                new CircleShape("Коло-Лінза", 0, 0, 5),
                new RectangleShape("Рамка-А", 2, 3, 4, 6),
                new ImmutableReferenceSquare("Еталон-Квадрат", 10, 10, 5)
        };

        System.out.println("=== ПОЛІМОРФНА ГЕОМЕТРІЯ ===");
        System.out.println("ДО ТРАНСФОРМАЦІЇ:");

        for (AbstractShape2D x : a) {
            System.out.println(x);
        }

        System.out.printf(Locale.US, "Площа: %.2f%n",
                calculateTotalArea(a));

        transformScene(a, 2, 45);

        System.out.println("ПІСЛЯ ТРАНСФОРМАЦІЇ:");

        for (AbstractShape2D x : a) {
            System.out.println(x);
        }

        System.out.printf(Locale.US, "Нова площа: %.2f%n",
                calculateTotalArea(a));
    }
        }