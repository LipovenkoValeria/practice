package semester1.practice09.homework;

class EncapsulatedMatrix {
    private final int rows, cols;
    private final double[][] grid;

    EncapsulatedMatrix(double[][] a) {
        if (a == null || a.length == 0 || a[0].length == 0)
        throw new IllegalArgumentException();

        rows = a.length;
        cols = a[0].length;
        grid = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (a[i].length != cols)
                throw new IllegalArgumentException("Ragged matrix");

            grid[i] = a[i].clone();
        }
    }

    int getRows() { return rows; }
    int getCols() { return cols; }

    double[][] getGridCopy() {
        double[][] a = new double[rows][cols];

        for (int i = 0; i < rows; i++)
            a[i] = grid[i].clone();

        return a;
    }

    double getElement(int r, int c) {
        return grid[r][c];
    }

    boolean isSquare() {
        return rows == cols;
    }

    double getTrace() {
        if (!isSquare()) throw new IllegalStateException();

        double s = 0;

        for (int i = 0; i < rows; i++)
            s += grid[i][i];

        return s;
    }

    double getFrobeniusNorm() {
        double s = 0;

        for (double[] r : grid)
            for (double v : r)
                s += v * v;

        return Math.sqrt(s);
    }

    double getDeterminant() {
        if (!isSquare()) throw new IllegalStateException();

        if (rows == 1)
            return grid[0][0];

        if (rows == 2)
            return grid[0][0] * grid[1][1]
                    - grid[0][1] * grid[1][0];

        if (rows == 3)
            return grid[0][0] * grid[1][1] * grid[2][2]
                    + grid[0][1] * grid[1][2] * grid[2][0]
                    + grid[0][2] * grid[1][0] * grid[2][1]
                    - grid[0][2] * grid[1][1] * grid[2][0]
                    - grid[0][1] * grid[1][0] * grid[2][2]
                    - grid[0][0] * grid[1][2] * grid[2][1];

        throw new IllegalStateException("Only up to 3x3");
    }

    public String toString() {
        StringBuilder s = new StringBuilder(
                String.format("Matrix %dx%d norm=%.2f",
                        rows, cols, getFrobeniusNorm()));

        if (isSquare())
            s.append(String.format(
                    " trace=%.2f det=%.2f",
                    getTrace(), getDeterminant()));

        s.append('\n');

        for (double[] r : grid) {
            for (double v : r)
                s.append(String.format("%8.2f", v));

            s.append('\n');
        }

        return s.toString();
    }
}

abstract class AbstractMatrixOperator {
    private final String operatorName;

    AbstractMatrixOperator(String n) {
        operatorName = n;
    }

    abstract boolean isApplicable(EncapsulatedMatrix m);
    abstract EncapsulatedMatrix apply(EncapsulatedMatrix m);

    final EncapsulatedMatrix executeSafely(EncapsulatedMatrix m) {
        if (m != null && isApplicable(m)) {
            System.out.println("Оператор: " + operatorName);
            return apply(m);
        }

        System.out.println("Несумісний оператор: " + operatorName);
        return m;
    }
}

class ScaleMatrixOperator extends AbstractMatrixOperator {
    private final double scaleFactor;

    ScaleMatrixOperator(double f) {
        super("Scale");
        scaleFactor = f;
    }

    boolean isApplicable(EncapsulatedMatrix m) {
        return m != null;
    }

    EncapsulatedMatrix apply(EncapsulatedMatrix m) {
        double[][] a = m.getGridCopy();

        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] *= scaleFactor;

        return new EncapsulatedMatrix(a);
    }
}

class TransposeMatrixOperator extends AbstractMatrixOperator {
    TransposeMatrixOperator() {
        super("Transpose");
    }

    boolean isApplicable(EncapsulatedMatrix m) {
        return m != null;
    }

    EncapsulatedMatrix apply(EncapsulatedMatrix m) {
        double[][] a = new double[m.getCols()][m.getRows()];
        for (int i = 0; i < m.getRows(); i++)
            for (int j = 0; j < m.getCols(); j++)
                a[j][i] = m.getElement(i, j);

        return new EncapsulatedMatrix(a);
    }
}

class DiagonalShiftOperator extends AbstractMatrixOperator {
    private final double lambda;

    DiagonalShiftOperator(double l) {
        super("Diagonal shift");
        lambda = l;
    }

    boolean isApplicable(EncapsulatedMatrix m) {
        return m != null && m.isSquare();
    }

    EncapsulatedMatrix apply(EncapsulatedMatrix m) {
        double[][] a = m.getGridCopy();

        for (int i = 0; i < a.length; i++)
            a[i][i] += lambda;

        return new EncapsulatedMatrix(a);
    }
}

class MultiplyByMatrixOperator extends AbstractMatrixOperator {
    private final EncapsulatedMatrix rightMatrix;

    MultiplyByMatrixOperator(EncapsulatedMatrix b) {
        super("Multiply");
        rightMatrix = b;
    }

    boolean isApplicable(EncapsulatedMatrix a) {
        return a != null && a.getCols() == rightMatrix.getRows();
    }

    EncapsulatedMatrix apply(EncapsulatedMatrix a) {
        double[][] c = new double[
                a.getRows()][rightMatrix.getCols()];

        for (int i = 0; i < a.getRows(); i++)
            for (int j = 0; j < rightMatrix.getCols(); j++)
                for (int k = 0; k < a.getCols(); k++)
                    c[i][j] += a.getElement(i, k)
                            * rightMatrix.getElement(k, j);

        return new EncapsulatedMatrix(c);
    }
}

class MatrixPipeline {
    private final String pipelineName;
    private final AbstractMatrixOperator[] stages;
    private int stageCount;

    MatrixPipeline(String n, int cap) {
        pipelineName = n;
        stages = new AbstractMatrixOperator[cap];
    }

    void addStage(AbstractMatrixOperator op) {
        if (op != null && stageCount < stages.length)
            stages[stageCount++] = op;
    }

    EncapsulatedMatrix runPipeline(EncapsulatedMatrix m) {
        System.out.println(pipelineName);

        for (int i = 0; i < stageCount; i++) {
            m = stages[i].executeSafely(m);
            System.out.println(m);
        }

        return m;
    }
}

public class Task4MatrixOperatorPipeline {
    public static void main(String[] args) {
        double[][] raw = {{1, 2}, {3, 4}};

        EncapsulatedMatrix m = new EncapsulatedMatrix(raw);
        raw[0][0] = 999;

        System.out.println("Original:\n" + m);

        MatrixPipeline p = new MatrixPipeline("Matrix pipeline", 4);

        p.addStage(new ScaleMatrixOperator(2));
        p.addStage(new TransposeMatrixOperator());
        p.addStage(new DiagonalShiftOperator(3));

        p.addStage(new MultiplyByMatrixOperator(
                new EncapsulatedMatrix(
                        new double[][]{{1, 0}, {0, 1}})));

        p.runPipeline(m);
    }
}
