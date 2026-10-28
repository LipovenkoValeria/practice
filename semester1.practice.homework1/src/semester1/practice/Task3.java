package semester1.practice;

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
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void multiplyByScalar(double k) {
        x = x * k;
        y = y * k;
    }

    public double dotProduct(double otherX, double otherY) {
        return x * otherX + y * otherY;
    }

    public boolean isPerpendicularTo(double otherX, double otherY) {
        double dot = dotProduct(otherX, otherY);
        return Math.abs(dot) < 1e-9;
    }

    public void normalize() {
        double length = getLength();

        if (length > 0) {
            x = x / length;
            y = y / length;
        }
    }

    public void printVector() {
        System.out.println("(" + x + "; " + y + "), Довжина = " + getLength());
    }
}

public class Task3 {
    public static void main(String[] args) {
        Vector2D a = new Vector2D();
        Vector2D b = new Vector2D();

        a.set(3, 4);
        b.set(-4, 3);

        System.out.println("=== ВЕКТОРНИЙ АНАЛІЗАТОР (2D GEOMETRY) ===");

        System.out.print("Вектор A: ");
        a.printVector();

        System.out.print("Вектор B: ");
        b.printVector();

        double dot = a.dotProduct(b.getX(), b.getY());

        System.out.println();
        System.out.println("Скалярний добуток A · B: " + dot);
        System.out.println("Чи є вектори перпендикулярними? "
                + a.isPerpendicularTo(b.getX(), b.getY()));

        System.out.println();
        System.out.println("Нормалізація вектора A...");
        a.normalize();

        System.out.print("Одиничний вектор A: ");
        a.printVector();

        System.out.println("Нова довжина вектора A: " + a.getLength());
        System.out.println("==========================================");
    }
}