package semester1.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

abstract class Shape {
    private final String id;
    private final String color;

    public Shape(String id, String color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Shape)) {
            return false;
        }

        Shape shape = (Shape) o;
        return Objects.equals(id, shape.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(String id, String color, double radius) {
        super(id, color);

        if (radius <= 0) {
            throw new IllegalArgumentException("Радіус повинен бути додатним!");
        }

        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String id, String color, double width, double height) {
        super(id, color);

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Розміри повинні бути додатними!");
        }

        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
}

class RightTriangle extends Shape {
    private double legA;
    private double legB;

    public RightTriangle(String id, String color, double legA, double legB) {
        super(id, color);

        if (legA <= 0 || legB <= 0) {
            throw new IllegalArgumentException("Катети повинні бути додатними!");
        }

        this.legA = legA;
        this.legB = legB;
    }

    @Override
    public double getArea() {
        return legA * legB / 2;
    }

    @Override
    public double getPerimeter() {
        double hypotenuse = Math.sqrt(legA * legA + legB * legB);

        return legA + legB + hypotenuse;
    }
}

class GeometryCanvas {
    private String title;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private HashSet<String> usedIds = new HashSet<>();

    public GeometryCanvas(String title) {
        this.title = title;
    }

    public boolean addShape(Shape s) {
        if (s == null || !usedIds.add(s.getId())) {
            return false;
        }

        shapes.add(s);
        return true;
    }

    public int removeSmallShapes(double minArea) {
        int removed = 0;

        Iterator<Shape> it = shapes.iterator();

        while (it.hasNext()) {
            Shape s = it.next();

            if (s.getArea() < minArea) {
                it.remove();
                usedIds.remove(s.getId());
                removed++;

                System.out.println("  Видалено: " + s.getId()
                        + " (площа " + String.format("%.2f", s.getArea()) + ")");
            }
        }

        return removed;
    }

    public double getTotalArea() {
        double total = 0;

        for (Shape s : shapes) {
            total += s.getArea();
        }

        return total;
    }

    public double getTotalPerimeter() {
        double total = 0;

        for (Shape s : shapes) {
            total += s.getPerimeter();
        }

        return total;
    }

    public HashSet<String> getUniqueColors() {
        HashSet<String> colors = new HashSet<>();

        for (Shape s : shapes) {
            colors.add(s.getColor());
        }

        return colors;
    }

    public ArrayList<Shape> getShapesCopy() {
        return new ArrayList<>(shapes);
    }

    public String getTitle() {
        return title;
    }
}

public class Task3GeometryCanvasAndStats {

    public static void main(String[] args) {

        System.out.println("=============== ГЕОМЕТРИЧНЕ ПОЛОТНО ===============");

        GeometryCanvas canvas1 = new GeometryCanvas("Головне полотно");

        System.out.println("Додавання фігур:");

        System.out.println("Circle C-1: "
                + canvas1.addShape(new Circle("C-1", "Red", 3.0)));

        System.out.println("Circle C-1 (дублікат): "
                + canvas1.addShape(new Circle("C-1", "Blue", 5.0)));

        System.out.println("Rectangle R-1: "
                + canvas1.addShape(new Rectangle("R-1", "Blue", 4.0, 5.0)));

        System.out.println("Rectangle R-2: "
                + canvas1.addShape(new Rectangle("R-2", "Green", 2.0, 3.0)));

        System.out.println("RightTriangle T-1: "
                + canvas1.addShape(new RightTriangle("T-1", "Red", 3.0, 4.0)));

        System.out.println("RightTriangle T-2: "
                + canvas1.addShape(new RightTriangle("T-2", "Yellow", 6.0, 8.0)));

        System.out.println("\n[ДО ОЧИЩЕННЯ]");

        System.out.println("Фігур: " + canvas1.getShapesCopy().size());
        System.out.println("Сумарна площа: "
                + String.format("%.2f", canvas1.getTotalArea()));
        System.out.println("Сумарний периметр: "
                + String.format("%.2f", canvas1.getTotalPerimeter()));

        System.out.println("Унікальні кольори: "
                + canvas1.getUniqueColors());

        System.out.println("\n--- Видалення фігур через Iterator ---");

        int removed = canvas1.removeSmallShapes(10.0);

        System.out.println("Видалено фігур: " + removed);

        System.out.println("\n[ПІСЛЯ ОЧИЩЕННЯ]");

        System.out.println("Фігур: " + canvas1.getShapesCopy().size());
        System.out.println("Сумарна площа: "
                + String.format("%.2f", canvas1.getTotalArea()));
        System.out.println("Сумарний периметр: "
                + String.format("%.2f", canvas1.getTotalPerimeter()));

        HashSet<String> palette1 = canvas1.getUniqueColors();

        HashSet<String> palette2 = new HashSet<>();

        palette2.add("Blue");
        palette2.add("Yellow");
        palette2.add("Purple");

        HashSet<String> common = new HashSet<>(palette1);
        common.retainAll(palette2);

        HashSet<String> exclusive = new HashSet<>(palette1);
        exclusive.removeAll(palette2);

        HashSet<String> all = new HashSet<>(palette1);
        all.addAll(palette2);

        System.out.println("\n--- Порівняння палітр ---");

        System.out.println("Палітра 1: " + palette1);
        System.out.println("Палітра 2: " + palette2);
        System.out.println("Спільні кольори: " + common);
        System.out.println("Тільки на полотні 1: " + exclusive);
        System.out.println("Усі кольори: " + all);

        System.out.println("==================================================");
    }
}