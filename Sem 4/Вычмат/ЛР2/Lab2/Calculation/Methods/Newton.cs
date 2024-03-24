using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    internal class Newton
    {
        public static Result Calculate(Equation equation,
                                        Tuple<double, double> limits,
                                        double epsilon)
        {
            if (!EquationUtils.HasSolutions(equation.Function, limits) && limits.Item1 != limits.Item2)
                throw new InvalidOperationException("На указанном участке нет корней");
            double x0 = limits.Item1;
            if (equation.Function(limits.Item2) * equation.SecondDerivative(limits.Item2) > 0)
                x0 = limits.Item2;
            return Perform(equation, x0, epsilon, limits, 0);
        }

        private static Result Perform(Equation equation,
                                        double lastX,
                                        double epsilon,
                                        Tuple<double, double> limits,
                                        int iteration)
        {
            if (iteration > EquationUtils.LIMIT_ITERATIONS)
                throw new StackOverflowException();
            double f = equation.Function(lastX);
            if (Math.Abs(f) < epsilon) return new Result(iteration, lastX);
            double fDerivative = equation.Derivative(lastX);
            if(fDerivative == 0)
                throw new InvalidOperationException("Первая производная на промежутке равна нулю");
            double h = f / fDerivative;
            double x = lastX - h;
            //if (limits.Item1 != limits.Item2 && (x < limits.Item1 || x > limits.Item2))
            //    throw new InvalidOperationException("Приближение выходит за границы промежутка, уточните промежуток ближе к корню");
            if (Math.Abs(x - lastX) < epsilon) return new Result(iteration, x);
            return Perform(equation, x, epsilon, limits, iteration + 1);
        }
    }
}
