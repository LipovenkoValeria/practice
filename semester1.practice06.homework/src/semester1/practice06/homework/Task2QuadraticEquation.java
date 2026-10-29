package semester1.practice06.homework;

class QuadraticEquation {
    private double a;
    private double b;
    private double c;

    public void setCoefficients(double a, double b, double c) {
        if (a == 0) {
            System.out.println("Увага: коефіцієнт 'a' не може дорівнювати 0!");
            a = 1.0;
        }

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getDiscriminant() {
        return b * b - 4 * a * c;
    }

    public boolean hasRealRoots() {
        return getDiscriminant() >= 0;
    }

    public double getRoot1() {
        double d = getDiscriminant();

        if (d < 0) {
            return Double.NaN;
        }

        return (-b + Math.sqrt(d)) / (2 * a);
    }

    public double getRoot2() {
        double d = getDiscriminant();

        if (d < 0) {
            return Double.NaN;
        }

        return (-b - Math.sqrt(d)) / (2 * a);
    }

    public void printSolution() {
        System.out.println("Рівняння: " + a + "*x^2 + (" + b + ")*x + (" + c + ") = 0");

        double d = getDiscriminant();
        System.out.printf("Дискримінант D = %.2f%n", d);

        if (d > 0) {
            System.out.println("Рівняння має 2 дійсні корені:");
            System.out.printf("  x1 = %.2f%n", getRoot1());
            System.out.printf("  x2 = %.2f%n", getRoot2());
        } else if (d == 0) {
            System.out.println("Рівняння має один дійсний корінь:");
            System.out.printf("  x = %.2f%n", getRoot1());
        } else {
            System.out.println("Рівняння не має дійсних коренів (D < 0)");
        }
    }
}

public class Task2QuadraticEquation {
    public static void main(String[] args) {
        System.out.println("=== РОЗВ'ЯЗУВАЧ КВАДРАТНИХ РІВНЯНЬ ===");

        QuadraticEquation equation = new QuadraticEquation();

        System.out.println("Тест 1:");
        equation.setCoefficients(1, -5, 6);
        equation.printSolution();

        System.out.println("--------------------------------------");
        System.out.println("Тест 2:");
        equation.setCoefficients(1, -4, 4);
        equation.printSolution();

        System.out.println("--------------------------------------");
        System.out.println("Тест 3:");
        equation.setCoefficients(1, 2, 5);
        equation.printSolution();

        System.out.println("======================================");
    }
}
