using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab2.Calculation
{
    internal class SimpleIteration
    {
        public static Result Calculate(Equation equation,
                                        Tuple<double, double> limits,
                                        double epsilon)
        {
            if (!EquationUtils.HasSolutions(equation.Function, limits) && limits.Item1 != limits.Item2)
                throw new InvalidOperationException("На указанном участке нет корней");
            double lambda = -1d / (Math.Max(equation.Derivative(limits.Item1), equation.Derivative(limits.Item2)));
            Equation newEquation = new Equation((x) => x + lambda * equation.Function(x), null, null);
            double startX = equation.Derivative(limits.Item1) > equation.Derivative(limits.Item2) ? limits.Item1 : limits.Item2;
            if (Math.Abs(newEquation.Derivative(limits.Item1)) >= 1 ||Math.Abs(newEquation.Derivative(limits.Item2)) >= 1)
                MessageBox.Show("Для указанного промежутка не выполняется условие сходимости." +
                    "F'(a)=" + newEquation.Derivative(limits.Item1) + ", F'(b) = " + newEquation.Derivative(limits.Item2));
            MessageBox.Show("F'(a)=" + newEquation.Derivative(limits.Item1) + ", F'(b) = " + newEquation.Derivative(limits.Item2));
            return Perform(equation, newEquation, startX, Double.MaxValue, epsilon, limits, 0);
        }

        public static Result Perform(Equation equation,
                                        Equation newEquation,
                                        double lastX,
                                        double lastDiff,
                                        double epsilon,
                                        Tuple<double, double> limits,
                                        int iteration)
        {
            if (iteration > EquationUtils.LIMIT_ITERATIONS)
                throw new StackOverflowException();
            double f = equation.Function(lastX);
            if (Math.Abs(f) < epsilon) return new Result(iteration, lastX);
            double x = newEquation.Function(lastX);
            double diff = Math.Abs(x - lastX);
            if(diff > lastDiff)
                throw new InvalidOperationException("Решение не сходится, уменьшите рассматриваемый промежуток");
            if (limits.Item1 != limits.Item2 && (x < limits.Item1 || x > limits.Item2))
                throw new InvalidOperationException("Приближение выходит за границы промежутка, уточните промежуток ближе к корню. x=" + x);
            return Perform(equation, newEquation, x, lastDiff, epsilon, limits, iteration + 1);
        }
    }
}
