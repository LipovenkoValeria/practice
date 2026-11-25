package semester1.practice.homework4;

import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number;

        do {
            System.out.print("Введіть додатне ціле число: ");
            number = scanner.nextInt();

            if (number <= 0) {
                System.out.println("Число має бути більшим за 0!");
            }
        } while (number <= 0);

        int originalNumber = number;
        int temp = number;
        int digitCount = 0;
        int digitSum = 0;
        int maxDigit = 0;
        int reversedNumber = 0;

        while (temp > 0) {
            int digit = temp % 10;

            digitCount++;
            digitSum += digit;

            if (digit > maxDigit) {
                maxDigit = digit;
            }

            reversedNumber = reversedNumber * 10 + digit;
            temp = temp / 10;
        }

        System.out.println("Початкове число: " + originalNumber);
        System.out.println("Кількість цифр: " + digitCount);
        System.out.println("Сума цифр: " + digitSum);
        System.out.println("Найбільша цифра: " + maxDigit);
        System.out.println("Перевернуте число: " + reversedNumber);

        if (originalNumber == reversedNumber) {
            System.out.println("Це число є паліндромом!");
        } else {
            System.out.println("Це число не є паліндромом.");
        }

        scanner.close();
    }
}