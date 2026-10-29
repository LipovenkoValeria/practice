package semester1.practice06.homework;

class Fraction {
    private int numerator;
    private int denominator;

    public void setValues(int num, int den) {
        if (den == 0) {
            System.out.println("Помилка: знаменник не може дорівнювати 0!");
            denominator = 1;
            numerator = num;
            return;
        }

        if (den < 0) {
            numerator = -num;
            denominator = -den;
        } else {
            numerator = num;
            denominator = den;
        }
    }

    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    public void simplify() {
        int divisor = gcd(numerator, denominator);

        if (divisor != 0) {
            numerator = numerator / divisor;
            denominator = denominator / divisor;
        }
    }

    public double toDecimal() {
        return (double) numerator / denominator;
    }

    public void add(int otherNum, int otherDen) {
        if (otherDen == 0) {
            System.out.println("Помилка: знаменник не може дорівнювати 0!");
            return;
        }

        if (otherDen < 0) {
            otherNum = -otherNum;
            otherDen = -otherDen;
        }

        numerator = numerator * otherDen + otherNum * denominator;
        denominator = denominator * otherDen;
        simplify();
    }

    public void printFraction() {
        if (denominator == 1) {
            System.out.print(numerator);
        } else {
            System.out.print(numerator + "/" + denominator);
        }
    }
}

public class Task1Fraction {
    public static void main(String[] args) {
        System.out.println("=== КАЛЬКУЛЯТОР ЗВИЧАЙНИХ ДРОБІВ ===");

        Fraction fraction = new Fraction();
        fraction.setValues(8, 12);

        System.out.print("Дріб 1: ");
        fraction.printFraction();
        System.out.println();

        fraction.simplify();

        System.out.print("Після скорочення: ");
        fraction.printFraction();
        System.out.printf(" (десятковий вигляд: %.4f)%n", fraction.toDecimal());

        System.out.println();
        System.out.println("Додаємо дріб 3/4 до 2/3...");
        System.out.println("Формула: 2/3 + 3/4 = (2*4 + 3*3) / (3*4)");

        fraction.add(3, 4);

        System.out.print("Результат додавання: ");
        fraction.printFraction();
        System.out.printf(" (десятковий вигляд: %.4f)%n", fraction.toDecimal());

        System.out.println("=====================================");
    }
}