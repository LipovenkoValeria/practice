package semester1.practice;

class QuadraticEquation {
    private double a;
    private double b;
    private double c;

    public void setCoefficients(double a, double b, double c) {
        if (a == 0) {
            System.out.println(
                    "Увага: коефіцієнт 'a' не може дорівнювати 0! Встановлено a = 1.0."
            );
            this.a = 1.0;
        } else {
            this.a = a;
        }

        this.b = b;
        this.c = c;
    }

    public double getDiscriminant() {
        return Math.pow(b, 2) - 4 * a * c;
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
        double d = getDiscriminant();

        System.out.println("Рівняння: " + a + "*x^2 + (" + b + ")*x + " + c + " = 0");
        System.out.println("Дискримінант D = " + d);

        if (d > 0) {
            System.out.println("Рівняння має 2 дійсних корені:");
            System.out.println("x1 = " + getRoot1());
            System.out.println("x2 = " + getRoot2());
        } else if (d == 0) {
            System.out.println("Рівняння має один дійсний корінь:");
            System.out.println("x = " + getRoot1());
        } else {
            System.out.println("Рівняння не має дійсних коренів (D < 0).");
        }
    }
}

public class Task2 {
    public static void main(String[] args) {
        QuadraticEquation equation = new QuadraticEquation();

        System.out.println("=== РОЗВ'ЯЗУВАЧ КВАДРАТНИХ РІВНЯНЬ ===");

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