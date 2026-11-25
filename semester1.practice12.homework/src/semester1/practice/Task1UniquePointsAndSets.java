package semester1.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

class Point2D {
    private final int x;
    private final int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Point2D)) {
            return false;
        }

        Point2D point = (Point2D) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}

class PointSetUtils {

    public static HashSet<Point2D> union(
            HashSet<Point2D> a, HashSet<Point2D> b) {

        HashSet<Point2D> result = new HashSet<>(a);
        result.addAll(b);

        return result;
    }

    public static HashSet<Point2D> intersection(
            HashSet<Point2D> a, HashSet<Point2D> b) {

        HashSet<Point2D> result = new HashSet<>(a);
        result.retainAll(b);

        return result;
    }

    public static HashSet<Point2D> difference(
            HashSet<Point2D> a, HashSet<Point2D> b) {

        HashSet<Point2D> result = new HashSet<>(a);
        result.removeAll(b);

        return result;
    }

    public static int removeFarPoints(
            HashSet<Point2D> points, double maxRadius) {

        int removed = 0;

        Iterator<Point2D> it = points.iterator();

        while (it.hasNext()) {
            Point2D point = it.next();

            if (point.distanceFromOrigin() > maxRadius) {
                it.remove();
                removed++;
            }
        }

        return removed;
    }
}

public class Task1UniquePointsAndSets {

    public static void main(String[] args) {

        System.out.println("=============== ГЕОДЕЗИЧНИЙ РАДАР ===============");

        ArrayList<Point2D> sensorA = new ArrayList<>();

        sensorA.add(new Point2D(0, 0));
        sensorA.add(new Point2D(3, 4));
        sensorA.add(new Point2D(1, 2));
        sensorA.add(new Point2D(3, 4));
        sensorA.add(new Point2D(6, 8));
        sensorA.add(new Point2D(5, 0));
        sensorA.add(new Point2D(1, 2));

        ArrayList<Point2D> sensorB = new ArrayList<>();

        sensorB.add(new Point2D(3, 4));
        sensorB.add(new Point2D(5, 0));
        sensorB.add(new Point2D(2, 2));
        sensorB.add(new Point2D(8, 6));
        sensorB.add(new Point2D(2, 2));
        sensorB.add(new Point2D(0, 10));

        HashSet<Point2D> setA = new HashSet<>(sensorA);
        HashSet<Point2D> setB = new HashSet<>(sensorB);

        System.out.println("Сенсор A: отримано " + sensorA.size()
                + " точок -> унікальних: " + setA.size() + " " + setA);

        System.out.println("Сенсор B: отримано " + sensorB.size()
                + " точок -> унікальних: " + setB.size() + " " + setB);

        HashSet<Point2D> union = PointSetUtils.union(setA, setB);
        HashSet<Point2D> intersection =
                PointSetUtils.intersection(setA, setB);
        HashSet<Point2D> difference =
                PointSetUtils.difference(setA, setB);

        System.out.println("\n--- Операції над множинами ---");

        System.out.println("Об'єднання A U B [" + union.size() + "]: " + union);
        System.out.println("Перетин A ∩ B [" + intersection.size() + "]: "
                + intersection);
        System.out.println("Різниця A \\ B [" + difference.size() + "]: "
                + difference);

        System.out.println("\n--- Фільтрація через Iterator ---");

        int removed = PointSetUtils.removeFarPoints(union, 7.0);

        System.out.println("Видалено точок поза радіусом: " + removed);
        System.out.println("Залишилось точок: " + union.size() + " " + union);

        System.out.println("=================================================");
    }
}