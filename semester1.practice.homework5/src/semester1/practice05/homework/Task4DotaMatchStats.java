package semester1.practice05.homework;

public class Task4DotaMatchStats {
    public static void main(String[] args) {
        String[] heroes = {
                "Pudge", "Invoker", "Juggernaut",
                "Crystal Maiden", "Earthshaker"
        };

        int[] kills = {8, 12, 10, 2, 4};
        int[] deaths = {6, 3, 2, 9, 5};
        int[] assists = {14, 9, 8, 18, 16};
        int[] netWorth = {14500, 21200, 24800, 9800, 12300};

        double[] kdaRatios = new double[heroes.length];
        double[] killParticipation = new double[heroes.length];

        int totalTeamKills = 0;
        int totalNetWorth = 0;

        for (int i = 0; i < heroes.length; i++) {
            totalTeamKills += kills[i];
            totalNetWorth += netWorth[i];
        }

        int mvpIndex = 0;

        for (int i = 0; i < heroes.length; i++) {
            int deathsForCalculation = Math.max(1, deaths[i]);

            kdaRatios[i] = (double) (kills[i] + assists[i])
                    / deathsForCalculation;

            killParticipation[i] =
                    (double) (kills[i] + assists[i]) / totalTeamKills * 100;

            if (kdaRatios[i] > kdaRatios[mvpIndex]) {
                mvpIndex = i;
            }
        }

        System.out.println("==========================================================================");
        System.out.println("                 DOTA 2: ЗВІТ ПІСЛЯ МАТЧУ");
        System.out.println("==========================================================================");

        System.out.printf(
                "%-18s | %-11s | %-10s | %-9s | %-19s%n",
                "Герой", "В / С / Д", "Net Worth", "KDA", "Участь у вбивствах"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (int i = 0; i < heroes.length; i++) {
            String kdaText = kills[i] + " / " + deaths[i] + " / " + assists[i];

            System.out.printf(
                    "%-18s | %-11s | %-10d | %-9.2f | %.2f%%%n",
                    heroes[i], kdaText, netWorth[i],
                    kdaRatios[i], killParticipation[i]
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.println("Загальна кількість вбивств команди: " + totalTeamKills);
        System.out.println("Загальний Net Worth команди: " + totalNetWorth + " золота");

        System.out.printf(
                "НАЙКРАЩИЙ ГРАВЕЦЬ МАТЧУ (MVP): %s (KDA: %.2f)%n",
                heroes[mvpIndex], kdaRatios[mvpIndex]
        );

        System.out.println("==========================================================================");
    }
}