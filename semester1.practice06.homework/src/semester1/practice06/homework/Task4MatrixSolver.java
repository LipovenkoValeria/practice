package semester1.practice06.homework;

class MatrixSolver {
    private double[][] data;

    public void setMatrix(double[][] data) {
        if (data == null || data.length == 0) {
            System.out.println("Помилка: матриця порожня!");
            return;
        }

        int columns = data[0].length;

        if (columns == 0) {
            System.out.println("Помилка: матриця не має стовпців!");
            return;
        }

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].length != columns) {
                System.out.println("Помилка: неправильний розмір матриці!");
                return;
            }
        }

        this.data = new double[data.length][columns];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < columns; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public void printMatrix() {
        if (data == null) {
            System.out.println("Матриця не задана.");
            return;
        }

        for (int i = 0; i < data.length; i++) {
            System.out.print("[ ");

            for (int j = 0; j < data[i].length; j++) {
                System.out.printf("%7.2f ", data[i][j]);
            }

            System.out.println("]");
        }
    }

    public double getDeterminant2x2() {
        if (data == null || data.length != 2 ||
        data[0].length != 2 || data[1].length != 2) {
            System.out.println("Помилка: потрібна матриця 2x2!");
            return 0;
        }

        return data[0][0] * data[1][1]
                - data[0][1] * data[1][0];
    }

    public double getDeterminant3x3() {
        if (data == null || data.length != 3
                || data[0].length != 3
                || data[1].length != 3
                || data[2].length != 3) {
            System.out.println("Помилка: потрібна матриця 3x3!");
            return 0;
        }

        return data[0][0] * data[1][1] * data[2][2]
                + data[0][1] * data[1][2] * data[2][0]
                + data[0][2] * data[1][0] * data[2][1]
                - data[0][2] * data[1][1] * data[2][0]
                - data[0][0] * data[1][2] * data[2][1]
                - data[0][1] * data[1][0] * data[2][2];
    }

    public void transpose() {
        if (data == null) {
            System.out.println("Матриця не задана.");
            return;
        }

        int rows = data.length;
        int columns = data[0].length;
        double[][] transposed = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposed[j][i] = data[i][j];
            }
        }

        data = transposed;
    }

    public void solveSystem2x2(double c1, double c2) {
        if (data == null || data.length != 2 ||
        data[0].length != 2 || data[1].length != 2) {
            System.out.println("Помилка: потрібна матриця коефіцієнтів 2x2!");
            return;
        }

        double delta = getDeterminant2x2();
        double deltaX = c1 * data[1][1] - c2 * data[0][1];
        double deltaY = data[0][0] * c2 - data[1][0] * c1;

        System.out.printf("Головний визначник Delta = %.2f%n", delta);
        System.out.printf("Визначник Delta_x = %.2f%n", deltaX);
        System.out.printf("Визначник Delta_y = %.2f%n", deltaY);

        if (Math.abs(delta) > 1e-9) {
            double x = deltaX / delta;
            double y = deltaY / delta;

            System.out.println("Знайдено єдиний розв'язок:");
            System.out.printf("  x = %.2f%n", x);
            System.out.printf("  y = %.2f%n", y);
        } else if (Math.abs(deltaX) < 1e-9
                && Math.abs(deltaY) < 1e-9) {
            System.out.println("Система має безліч розв'язків");
        } else {
            System.out.println("Система несумісна (не має розв'язків)");
        }
    }
}
public class Task4MatrixSolver {
    public static void main(String[] args) {
        System.out.println("=============== МАТРИЧНИЙ КАЛЬКУЛЯТОР ===============");

        MatrixSolver matrix = new MatrixSolver();
        matrix.setMatrix(new double[][]{
                {2, 3},
                {4, -1}
        });

        System.out.println("Початкова матриця 2x2:");
        matrix.printMatrix();

        System.out.printf("%nВизначник det(A) = %.2f%n",
                matrix.getDeterminant2x2());

        System.out.println("\nТранспонована матриця A^T:");
        matrix.transpose();
        matrix.printMatrix();

        MatrixSolver system = new MatrixSolver();
        system.setMatrix(new double[][]{
                {2, 3},
                {4, -1}
        });

        System.out.println("\n--- РОЗВ'ЯЗАННЯ СИСТЕМИ МЕТОДОМ КРАМЕРА ---");
        system.solveSystem2x2(8, 2);

        MatrixSolver matrix3 = new MatrixSolver();
        matrix3.setMatrix(new double[][]{
                {1, 2, 3},
                {0, 1, 4},
                {5, 6, 0}
        });

        System.out.println("\nМатриця 3x3:");
        matrix3.printMatrix();

        System.out.printf("Визначник матриці 3x3 = %.2f%n",
                matrix3.getDeterminant3x3());

        System.out.println("======================================================");
    }
}