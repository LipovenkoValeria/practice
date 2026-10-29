package semester1.practice08.homework;

class Matrix2D {
    protected double[][] data;
    private int rows;
    private int cols;

    public Matrix2D(double[][] source) {
        if (source == null || source.length == 0 || source[0] == null
                || source[0].length == 0) {
            data = new double[0][0];
            rows = 0;
            cols = 0;
            return;
        }

        rows = source.length;
        cols = source[0].length;
        data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (source[i] == null || source[i].length != cols) {
                throw new IllegalArgumentException(
                        "Усі рядки матриці мають бути однакової довжини."
                );
            }

            for (int j = 0; j < cols; j++) {
                data[i][j] = source[i][j];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getElement(int r, int c) {
        checkIndexes(r, c);
        return data[r][c];
    }

    public void setElement(int r, int c, double val) {
        checkIndexes(r, c);
        data[r][c] = val;
    }

    private void checkIndexes(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            throw new IndexOutOfBoundsException("Неправильні індекси матриці.");
        }
    }

    public double getSum() {
        double sum = 0.0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j];
            }
        }

        return sum;
    }

    public double getMaxElement() {
        if (rows == 0 || cols == 0) {
            return 0.0;
        }

        double max = data[0][0];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (data[i][j] > max) {
                    max = data[i][j];
                }
            }
        }

        return max;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            result.append("[ ");

            for (int j = 0; j < cols; j++) {
                result.append(String.format("%8.2f", data[i][j]));

                if (j < cols - 1) {
                    result.append(" ");
                }
            }

            result.append(" ]");

            if (i < rows - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }
}

class SquareMatrix extends Matrix2D {
    public SquareMatrix(double[][] source) {
        super(source);

        if (getRows() != getCols()) {
            System.out.println("Попередження: матриця не є квадратною.");
        }
    }

    public int getDimension() {
        return getRows();
    }

    public double getTrace() {
        double trace = 0.0;
        int size = Math.min(getRows(), getCols());

        for (int i = 0; i < size; i++) {
            trace += getElement(i, i);
        }

        return trace;
    }

    public boolean isSymmetric() {
        if (getRows() != getCols()) {
            return false;
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = i + 1; j < getCols(); j++) {
                if (Math.abs(getElement(i, j) - getElement(j, i)) > 1e-9) {
                    return false;
                }
            }
        }

        return true;
    }

    public double getDeterminant() {
        int n = getDimension();

        if (n != getCols()) {
            return 0.0;
        }

        if (n == 2) {
            return data[0][0] * data[1][1]
                    - data[0][1] * data[1][0];
        }

        if (n == 3) {
            return data[0][0] * data[1][1] * data[2][2]
                    + data[0][1] * data[1][2] * data[2][0]
                    + data[0][2] * data[1][0] * data[2][1]
                    - data[0][2] * data[1][1] * data[2][0]
                    - data[0][1] * data[1][0] * data[2][2]
                    - data[0][0] * data[1][2] * data[2][1];
        }

        return 0.0;
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(
                "%nХарактеристики SquareMatrix: Розмір = %dx%d"
                        + " | Слід (Trace) = %.2f | Симетрична = %b"
                        + " | det(A) = %.2f",
                getRows(), getCols(), getTrace(), isSymmetric(),
                getDeterminant()
        );
    }
}

class CramerMatrixSolver extends SquareMatrix {
    private double[] freeTerms;

    public CramerMatrixSolver(double[][] coefficients, double[] freeTerms) {
        super(coefficients);

        if (freeTerms == null || freeTerms.length != getRows()) {
            throw new IllegalArgumentException(
                    "Кількість вільних членів має відповідати кількості рівнянь."
            );
        }

        this.freeTerms = new double[freeTerms.length];

        for (int i = 0; i < freeTerms.length; i++) {
            this.freeTerms[i] = freeTerms[i];
        }
    }

    public double[] solve() {
        int n = getDimension();

        if (n != getCols() || (n != 2 && n != 3)) {
            System.out.println("Підтримуються лише квадратні матриці 2x2 та 3x3.");
            return new double[0];
        }

        double determinant = getDeterminant();

        if (Math.abs(determinant) < 1e-9) {
            System.out.println("Єдиного розв'язку немає: визначник дорівнює нулю.");
            return new double[0];
        }

        double[] roots = new double[n];

        for (int col = 0; col < n; col++) {
            double[][] replaced = new double[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    replaced[i][j] = (j == col)
                            ? freeTerms[i] : data[i][j];
                }
            }

            SquareMatrix replacementMatrix = new SquareMatrix(replaced);
            roots[col] = replacementMatrix.getDeterminant() / determinant;
        }

        return roots;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Розширена матриця системи [A | B]:\n");

        for (int i = 0; i < getRows(); i++) {
            result.append("[ ");

            for (int j = 0; j < getCols(); j++) {
                result.append(String.format("%8.2f", getElement(i, j)));
            }

            result.append("  | ");
            result.append(String.format("%8.2f", freeTerms[i]));
            result.append(" ]\n");
        }

        result.append(String.format(
                "Головний визначник det(A) = %.2f%n", getDeterminant()
        ));

        double[] roots = solve();

        if (roots.length > 0) {
            result.append("Розв'язок системи: ");

            for (int i = 0; i < roots.length; i++) {
                result.append(String.format("x%d = %.2f", i + 1, roots[i]));

                if (i < roots.length - 1) {
                    result.append(", ");
                }
            }
        } else {
            result.append("Система не має єдиного розв'язку.");
        }

        return result.toString();
    }
}

public class Task4MatrixHierarchy {
    public static void main(String[] args) {
        double[][] matrix3x3 = {
                {2, 1, 0},
                {1, 3, 1},
                {0, 1, 2}
        };

        SquareMatrix squareMatrix = new SquareMatrix(matrix3x3);

        double[][] coefficients = {
                {2, 3},
                {4, -1}
        };
        double[] freeTerms = {8, 2};

        CramerMatrixSolver solver = new CramerMatrixSolver(
                coefficients, freeTerms
        );
        System.out.println("================ ІЄРАРХІЯ МАТРИЦЬ ТА СЛАР ================");
        System.out.println("--- 1. Квадратна матриця 3x3 (SquareMatrix) ---");
        System.out.println(squareMatrix);

        System.out.println();
        System.out.println("--- 2. Розв'язувач системи Крамера (CramerMatrixSolver 2x2) ---");
        System.out.println(solver);
        System.out.println("==========================================================");
    }
}