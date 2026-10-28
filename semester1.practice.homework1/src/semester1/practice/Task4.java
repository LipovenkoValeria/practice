package semester1.practice;

class MatrixSolver {
    private double[][] data;

    public void setMatrix(double[][] data) {
        this.data = data;
    }

    public void printMatrix() {
        for (int i = 0; i < data.length; i++) {
            System.out.print("[ ");

            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }

            System.out.println("]");
        }
    }

    public double getDeterminant2x2() {
        return data[0][0] * data[1][1]
                - data[0][1] * data[1][0];
    }

    public double getDeterminant3x3() {
        return data[0][0] * data[1][1] * data[2][2]
                + data[0][1] * data[1][2] * data[2][0]
                + data[0][2] * data[1][0] * data[2][1]
                - data[0][2] * data[1][1] * data[2][0]
                - data[0][0] * data[1][2] * data[2][1]
                - data[0][1] * data[1][0] * data[2][2];
    }

    public void transpose() {
        double[][] transposed = new double[data[0].length][data.length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                transposed[j][i] = data[i][j];
            }
        }

        data = transposed;
    }

    public void solveSystem2x2(double c1, double c2) {
        double delta = getDeterminant2x2();

        double deltaX = c1 * data[1][1] - c2 * data[0][1];
        double deltaY = data[0][0] * c2 - data[1][0] * c1;

        System.out.println("Головний визначник Delta = " + delta);
        System.out.println("Визначник Delta_x = " + deltaX);
        System.out.println("Визначник Delta_y = " + deltaY);

        if (Math.abs(delta) > 1e-9) {
            double x = deltaX / delta;
            double y = deltaY / delta;

            System.out.println("Знайдено єдиний розв'язок:");
            System.out.println("x = " + x);
            System.out.println("y = " + y);
        } else if (deltaX == 0 && deltaY == 0) {
            System.out.println("Система має безліч розв'язків.");
        } else {
            System.out.println("Система несумісна (не має розв'язків).");
        }
    }
}

public class Task4 {
    public static void main(String[] args) {
        MatrixSolver solver = new MatrixSolver();

        double[][] matrix2x2 = {
                {2, 3},
                {4, -1}
        };

        solver.setMatrix(matrix2x2);

        System.out.println("=============== МАТРИЧНИЙ КАЛЬКУЛЯТОР ===============");
        System.out.println("Початкова матриця 2x2:");

        solver.printMatrix();

        System.out.println("Визначник det(A) = " + solver.getDeterminant2x2());

        System.out.println();
        System.out.println("Транспонована матриця A^T:");

        solver.transpose();
        solver.printMatrix();

        double[][] systemMatrix = {
                {2, 3},
                {4, -1}
        };

        solver.setMatrix(systemMatrix);

        System.out.println();
        System.out.println("--- РОЗВ'ЯЗАННЯ СИСТЕМИ МЕТОДОМ КРАМЕРА ---");
        System.out.println("2x + 3y = 8");
        System.out.println("4x - y = 2");

        solver.solveSystem2x2(8, 2);

        double[][] matrix3x3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        solver.setMatrix(matrix3x3);

        System.out.println();
        System.out.println("Визначник матриці 3x3:");
        solver.printMatrix();
        System.out.println("det(A) = " + solver.getDeterminant3x3());

        System.out.println("======================================================");
    }
}
