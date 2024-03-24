using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    internal class ChordMethod
    {
        public static Result Calculate(Equation equation,
                                        Tuple<double, double> limits,
                                        double epsilon)
        {
            if (!EquationUtils.HasSolutions(equation.Function, limits))
                throw new InvalidOperationException("На указанном участке нет корней");
            int iterCount = 0;
            double a = limits.Item1, b = limits.Item2, x = 0, lastX = 0;
            while (true) {
                x = a - ((b - a) / (equation.Function(b) - equation.Function(a))) * equation.Function(a);
                if (Math.Abs(x - lastX) < epsilon) break;
                iterCount++;
                if (iterCount > EquationUtils.LIMIT_ITERATIONS) throw new StackOverflowException();
                if (equation.Function(x) == 0) break;
                else if (equation.Function(a) * equation.Function(x) < 0) b = x;
                else a = x;
                lastX = x;
            }
            return new Result(iterCount, x);
        }
    }
}
