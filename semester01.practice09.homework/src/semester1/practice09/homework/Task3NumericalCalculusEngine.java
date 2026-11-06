package semester1.practice09.homework;

abstract class AbstractFunction1D {
    private final String expressionName;

    AbstractFunction1D(String n) {
        expressionName = n;
    }

    String getExpressionName() {
        return expressionName;
    }

    abstract double evaluate(double x);
    abstract double analyticDerivative(double x);

    final double numericalDerivative(double x) {
        double h = 1e-5;
        return (evaluate(x + h) - evaluate(x - h)) / (2 * h);
    }

    final double getDerivativeError(double x) {
        return Math.abs(
                analyticDerivative(x) - numericalDerivative(x));
    }

    final double integrateTrapezoidal(
            double a, double b, int intervals) {

        if (intervals <= 0) intervals = 1000;

        double h = (b - a) / intervals;
        double s = (evaluate(a) + evaluate(b)) / 2;

        for (int i = 1; i < intervals; i++)
            s += evaluate(a + i * h);

        return s * h;
    }
}

class PolynomialFunction extends AbstractFunction1D {
    private final double[] coeffs;

    PolynomialFunction(String n, double[] c) {
        super(n);
        coeffs = c.clone();
    }

    double[] getCoefficients() {
        return coeffs.clone();
    }

    double evaluate(double x) {
        double s = 0, p = 1;

        for (double c : coeffs) {
            s += c * p;
            p *= x;
        }

        return s;
    }

    double analyticDerivative(double x) {
        double s = 0, p = 1;

        for (int i = 1; i < coeffs.length; i++) {
            s += i * coeffs[i] * p;
            p *= x;
        }

        return s;
    }
}

class SineWaveFunction extends AbstractFunction1D {
    private final double amplitude, omega, phase;

    SineWaveFunction(String n, double a, double o, double p) {
        super(n);
        amplitude = a;
        omega = o;
        phase = p;
    }

    double evaluate(double x) {
        return amplitude * Math.sin(omega * x + phase);
    }

    double analyticDerivative(double x) {
        return amplitude * omega * Math.cos(omega * x + phase);
    }
}

class CompositeFunction1D extends AbstractFunction1D {
    private final AbstractFunction1D outer, inner;

    CompositeFunction1D(AbstractFunction1D o, AbstractFunction1D i) {
        super("(" + o.getExpressionName()
                + " ∘ " + i.getExpressionName() + ")");
        outer = o;
        inner = i;
    }

    double evaluate(double x) {
        return outer.evaluate(inner.evaluate(x));
    }

    double analyticDerivative(double x) {
        return outer.analyticDerivative(inner.evaluate(x))
                * inner.analyticDerivative(x);
    }
}

public class Task3NumericalCalculusEngine {
    public static void main(String[] args) {
        PolynomialFunction g = new PolynomialFunction(
                "g(x)", new double[]{1, 0, 1});

        SineWaveFunction f = new SineWaveFunction(
                "f(u)", 2, 1, 0);

        CompositeFunction1D h = new CompositeFunction1D(f, g);

        double x = 1.5;

        System.out.printf(
                "g(2)=%.2f; g'(2)=%.6f; integral[0,3]=%.4f%n",
                g.evaluate(2),
                g.analyticDerivative(2),
                g.integrateTrapezoidal(0, 3, 1000));

        System.out.printf(
                "Integral 2sin(x) [0,pi]=%.4f%n",
                f.integrateTrapezoidal(0, Math.PI, 1000));

        System.out.printf(
                "H(1.5)=%.6f; H'=%.6f; numeric=%.6f; error=%.3e%n",
                h.evaluate(x),
                h.analyticDerivative(x),
                h.numericalDerivative(x),
                h.getDerivativeError(x));
    }
}