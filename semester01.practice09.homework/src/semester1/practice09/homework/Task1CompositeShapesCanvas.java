package semester1.practice09.homework;

class Point2D {
    private double x, y;

    Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point2D(Point2D p) {
        this(p.x, p.y);
    }

    double getX() { return x; }
    double getY() { return y; }

    void translate(double a, double b) {
        x += a;
        y += b;
    }

    double distanceTo(Point2D p) {
        return Math.hypot(x - p.x, y - p.y);
    }

    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}

abstract class AbstractShape2D {
    private final String name;
    private final Point2D point;

    AbstractShape2D(String n, Point2D p) {
        name = n;
        point = p == null ? new Point2D(0, 0) : new Point2D(p);
    }

    String getName() { return name; }

    Point2D getAnchorPoint() {
        return new Point2D(point);
    }

    void moveBy(double x, double y) {
        point.translate(x, y);
    }

    abstract double getArea();
    abstract double getPerimeter();
    abstract String size();

    public String toString() {
        return getClass().getSimpleName()
                + "[name='" + name + "', center=" + point
                + ", " + size()
                + " | S=" + String.format("%.2f", getArea())
                + ", P=" + String.format("%.2f", getPerimeter()) + "]";
    }
}

class Circle2D extends AbstractShape2D {
    private final double radius;

    Circle2D(String n, Point2D p, double r) {
        super(n, p);
        if (r <= 0) throw new IllegalArgumentException();
        radius = r;
    }

    double getArea() {
        return Math.PI * radius * radius;
    }

    double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    String size() {
        return String.format("r=%.2f", radius);
    }
}

class Rectangle2D extends AbstractShape2D {
    private final double w, h;

    Rectangle2D(String n, Point2D p, double w, double h) {
        super(n, p);
        if (w <= 0 || h <= 0) throw new IllegalArgumentException();
        this.w = w;
        this.h = h;
    }

    double getArea() {
        return w * h;
    }

    double getPerimeter() {
        return 2 * (w + h);
    }

    String size() {
        return String.format("%.2fx%.2f", w, h);
    }
}

class RightTriangle2D extends AbstractShape2D {
    private final double a, b;

    RightTriangle2D(String n, Point2D p, double a, double b) {
        super(n, p);
        if (a <= 0 || b <= 0) throw new IllegalArgumentException();
        this.a = a;
        this.b = b;
    }

    double getHypotenuse() {
        return Math.hypot(a, b);
    }

    double getArea() {
        return a * b / 2;
    }

    double getPerimeter() {
        return a + b + getHypotenuse();
    }

    String size() {
        return String.format("a=%.2f, b=%.2f, c=%.2f",
                a, b, getHypotenuse());
    }
}

class GeometryCanvas {
    private final String title;
    private final AbstractShape2D[] shapes;
    private int count;

    GeometryCanvas(String t, int capacity) {
        title = t;
        shapes = new AbstractShape2D[capacity];
    }

    boolean addShape(AbstractShape2D s) {
        if (s == null || count == shapes.length) return false;
        shapes[count++] = s;
        return true;
    }

    double getTotalArea() {
        double v = 0;
        for (int i = 0; i < count; i++)
            v += shapes[i].getArea();
        return v;
    }

    double getTotalPerimeter() {
        double v = 0;
        for (int i = 0; i < count; i++)
            v += shapes[i].getPerimeter();
        return v;
    }

    AbstractShape2D findLargestShape() {
        if (count == 0) return null;

        AbstractShape2D m = shapes[0];

        for (int i = 1; i < count; i++)
            if (shapes[i].getArea() > m.getArea())
                m = shapes[i];

        return m;
    }

    void moveAllShapes(double x, double y) {
        for (int i = 0; i < count; i++)
            shapes[i].moveBy(x, y);
    }
    public String toString() {
        StringBuilder s = new StringBuilder(
                "Полотно: '" + title + "' (Фігур: "
                        + count + "/" + shapes.length + ")\n");

        for (int i = 0; i < count; i++)
            s.append("  ").append(i + 1).append(". ")
                    .append(shapes[i]).append('\n');

        s.append(String.format(
                "Сумарна площа: %.2f | Периметр: %.2f%n",
                getTotalArea(), getTotalPerimeter()));

        if (count > 0)
            s.append("Найбільша фігура: ")
                    .append(findLargestShape().getName());

        return s.toString();
    }
}

public class Task1CompositeShapesCanvas {
    public static void main(String[] args) {
        Point2D p = new Point2D(0, 0);
        Circle2D c = new Circle2D("Коло-А", p, 5);
        p.translate(99, 99);

        GeometryCanvas g =
                new GeometryCanvas("Інженерне креслення №1", 5);

        g.addShape(c);
        g.addShape(new Rectangle2D(
                "Плита-Б", new Point2D(10, 5), 4, 6));
        g.addShape(new RightTriangle2D(
                "Клин-В", new Point2D(-3, 2), 3, 4));

        System.out.println(g);

        g.moveAllShapes(5, -2);

        System.out.println("Новий центр кола: " + c.getAnchorPoint());
    }
}