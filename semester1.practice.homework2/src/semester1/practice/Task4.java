package semester1.practice;

import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ПАНЕЛЬ КЛІМАТ-КОНТРОЛЮ SMART HOME ===");

        System.out.print("Поточна температура (°C): ");
        double currentTemp = scanner.nextDouble();

        System.out.print("Бажана температура (°C): ");
        double desiredTemp = scanner.nextDouble();

        System.out.print("Вологість повітря (%): ");
        int humidity = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Сезон (зима/літо/міжсезоння): ");
        String season = scanner.nextLine();

        System.out.print("Режим (1-Standby, 2-Eco, 3-Party, 4-Night, 5-Vacation): ");
        int mode = scanner.nextInt();

        System.out.print("Чи відчинені вікна? (true/false): ");
        boolean isWindowOpen = scanner.nextBoolean();

        double effectiveTargetTemp = switch (mode) {
            case 1 -> desiredTemp;
            case 2 -> {
                double correction;

                if (season.equalsIgnoreCase("зима")) {
                    correction = -2.0;
                } else if (season.equalsIgnoreCase("літо")) {
                    correction = 2.0;
                } else {
                    correction = 0.0;
                }

                yield desiredTemp + correction;
            }
            case 3 -> desiredTemp - 1.5;
            case 4 -> 19.0;
            case 5 -> {
                if (season.equalsIgnoreCase("зима")) {
                    yield 12.0;
                } else {
                    yield 28.0;
                }
            }
            default -> desiredTemp;
        };

        String modeName = switch (mode) {
            case 1 -> "Standby (Звичайний)";
            case 2 -> "Eco (Енергозбереження)";
            case 3 -> "Party (Гості)";
            case 4 -> "Night (Ніч)";
            case 5 -> "Vacation (Відпустка)";
            default -> "Невідомий режим";
        };

        String climateStatus;

        if (isWindowOpen && mode != 5) {
            climateStatus = "Кліматичні установки заблоковано";
            System.out.println("ПОПЕРЕДЖЕННЯ: Відчинені вікна! Обігрів та кондиціонування примусово заблоковано!");
        } else if ((currentTemp - effectiveTargetTemp) > 0.5) {
            climateStatus = "Кондиціонер УВІМКНЕНО (Охолодження)";
        } else if ((effectiveTargetTemp - currentTemp) > 0.5) {
            climateStatus = "Опалення УВІМКНЕНО (Обігрів)";
        } else {
            climateStatus = "Температура в зоні комфорту (Кліматичні прилади в очікуванні)";
        }

        String humidityStatus = humidity < 40
                ? "Зволожувач УВІМКНЕНО"
                : (humidity > 65
                ? "Осушувач УВІМКНЕНО"
                : "Вологість оптимальна");

        System.out.println();
        System.out.println("------------- СТАТУС СИСТЕМИ -------------");
        System.out.println("Режим роботи: " + modeName);
        System.out.println("Цільова температура: " + effectiveTargetTemp + "°C");
        System.out.println("Поточна температура: " + currentTemp + "°C");
        System.out.println("Стан терморегуляції: " + climateStatus);
        System.out.println("Стан мікроклімату: " + humidityStatus);

        if (mode == 2 || mode == 5) {
            System.out.println("Енергоспоживання: Оптимізоване");
        } else {
            System.out.println("Енергоспоживання: Звичайне");
        }

        System.out.println("==========================================");

        scanner.close();
    }
}