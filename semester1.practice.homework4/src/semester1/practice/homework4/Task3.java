package semester1.practice.homework4;

import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;

        do {
            System.out.print("Скільки чисел вивести (від 3 до 30): ");
            n = scanner.nextInt();

            if (n < 3 || n > 30) {
                System.out.println("Введіть число від 3 до 30!");
            }
        } while (n < 3 || n > 30);

        int f1 = 1;
        int f2 = 1;
        double ratio = 0;

        System.out.println("Числа Фібоначчі:");
        System.out.println("№ 1: 1");
        System.out.println("№ 2: 1");

        for (int i = 3; i <= n; i++) {
            int next = f1 + f2;
            ratio = (double) next / f2;

            System.out.printf("№ %d: %d | Відношення: %.6f%n",
                    i, next, ratio);

            f1 = f2;
            f2 = next;
        }

        System.out.printf("Наближення золотого перетину: %.6f%n", ratio);
        System.out.println("Еталонне значення: 1.618034");

        scanner.close();
    }
}