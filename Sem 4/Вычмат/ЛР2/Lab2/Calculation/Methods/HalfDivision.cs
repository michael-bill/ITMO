using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    internal class HalfDivision
    {
        public static Result Calculate(Equation equation,
                                        Tuple<double, double> limits,
                                        double epsilon,
                                        int iteration)
        {
            if (iteration > EquationUtils.LIMIT_ITERATIONS)
                throw new StackOverflowException();
            if (!EquationUtils.HasSolutions(equation.Function, limits))
                throw new InvalidOperationException("На указанном участке нет корней");
            double a = limits.Item1;
            double b = limits.Item2;
            double x0 = (a + b) / 2;
            double f = equation.Function(x0);
            if(Math.Abs(f) < epsilon) { return new Result(iteration, x0); }
            if(EquationUtils.HasSolutions(equation.Function, new Tuple<double, double>(a, x0)))
            {
                if(Math.Abs(a - x0) < epsilon) { return new Result(iteration, x0); }
                return Calculate(equation, new Tuple<double, double>(a, x0), epsilon, iteration + 1);
            } else if (EquationUtils.HasSolutions(equation.Function, new Tuple<double, double>(x0, b)))
            {
                if (Math.Abs(b - x0) < epsilon) { return new Result(iteration, x0); }
                return Calculate(equation, new Tuple<double, double>(x0, b), epsilon, iteration + 1);
            }
            throw new InvalidOperationException("На указанном участке нет корней");
        }
    }
}
