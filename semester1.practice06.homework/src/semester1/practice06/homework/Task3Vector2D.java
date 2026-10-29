package semester1.practice06.homework;

class Vector2D {
    private double x;
    private double y;

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void multiplyByScalar(double k) {
        x = x * k;
        y = y * k;
    }

    public double dotProduct(double otherX, double otherY) {
        return x * otherX + y * otherY;
    }

    public boolean isPerpendicularTo(double otherX, double otherY) {
        return Math.abs(dotProduct(otherX, otherY)) < 1e-9;
    }

    public void normalize() {
        double length = getLength();

        if (length > 0) {
            x = x / length;
            y = y / length;
        }
    }

    public void printVector() {
        System.out.printf("(%.2f; %.2f), Довжина = %.2f%n",
                x, y, getLength());
    }
}

public class Task3Vector2D {
    public static void main(String[] args) {
        System.out.println("=== ВЕКТОРНИЙ АНАЛІЗАТОР (2D GEOMETRY) ===");

        Vector2D a = new Vector2D();
        a.set(3, 4);

        Vector2D b = new Vector2D();
        b.set(-4, 3);

        System.out.print("Вектор A: ");
        a.printVector();

        System.out.print("Вектор B: ");
        b.printVector();

        System.out.printf("%nСкалярний добуток A · B: %.2f%n",
                a.dotProduct(b.getX(), b.getY()));

        System.out.println("Чи є вектори перпендикулярними? -> "
                + a.isPerpendicularTo(b.getX(), b.getY()));

        System.out.println("\nНормалізація вектора A...");
        a.normalize();

        System.out.print("Одиничний вектор A: ");
        a.printVector();

        System.out.printf("Нова довжина вектора A: %.2f%n",
                a.getLength());

        System.out.println("==========================================");
    }
}