package semester1.practice05.homework;

import java.util.Scanner;

public class Task3CinemaBooking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] hall = new char[5][6];

        for (int row = 0; row < hall.length; row++) {
            for (int seat = 0; seat < hall[row].length; seat++) {
                hall[row][seat] = 'O';
            }
        }

        int choice = -1;
        final int ticketPrice = 180;

        while (choice != 0) {
            System.out.println("\n=== ТЕРМІНАЛ КІНОТЕАТРУ ===");
            System.out.println("1 - Показати схему залу");
            System.out.println("2 - Забронювати місце");
            System.out.println("3 - Звіт і каса");
            System.out.println("0 - Завершити роботу");
            System.out.print("Ваш вибір: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nСхема залу:");

                    System.out.print("     ");
                    for (int seat = 1; seat <= 6; seat++) {
                        System.out.print(" " + seat + " ");
                    }
                    System.out.println();

                    for (int row = 0; row < hall.length; row++) {
                        System.out.print("Р" + (row + 1) + ":  ");

                        for (int seat = 0; seat < hall[row].length; seat++) {
                            System.out.print("[" + hall[row][seat] + "]");
                        }

                        System.out.println();
                    }
                    break;

                case 2:
                    System.out.print("Введіть номер ряду (1-5): ");
                    int row = scanner.nextInt();

                    System.out.print("Введіть номер місця (1-6): ");
                    int seat = scanner.nextInt();

                    if (row < 1 || row > 5 || seat < 1 || seat > 6) {
                    System.out.println("Помилка: такого місця не існує.");
                } else if (hall[row - 1][seat - 1] == 'X') {
                    System.out.println("Це місце вже зайняте.");
                } else {
                    hall[row - 1][seat - 1] = 'X';
                    System.out.println(
                            "Місце [Ряд " + row + ", Місце " + seat
                                    + "] успішно заброньовано!"
                    );
                }
                break;

                case 3:
                    int soldTickets = 0;
                    int freeSeats = 0;

                    for (int r = 0; r < hall.length; r++) {
                        for (int s = 0; s < hall[r].length; s++) {
                            if (hall[r][s] == 'X') {
                                soldTickets++;
                            } else {
                                freeSeats++;
                            }
                        }
                    }

                    int totalSeats = hall.length * hall[0].length;
                    double occupancy = (double) soldTickets / totalSeats * 100;
                    int revenue = soldTickets * ticketPrice;

                    System.out.println("\n--- СТАТИСТИКА ЗАЛУ ---");
                    System.out.println("Усього місць: " + totalSeats);
                    System.out.println("Зайнятих місць: " + soldTickets);
                    System.out.println("Вільних місць: " + freeSeats);
                    System.out.printf("Заповненість залу: %.2f%%%n", occupancy);
                    System.out.println("Загальний виторг: " + revenue + " грн");
                    break;

                case 0:
                    System.out.println("Роботу термінала завершено.");
                    break;

                default:
                    System.out.println("Неправильний пункт меню. Спробуйте ще раз.");
                    break;
            }
        }

        scanner.close();
    }
}
