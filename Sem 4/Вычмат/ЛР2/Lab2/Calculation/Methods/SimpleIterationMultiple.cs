using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    internal class SimpleIterationMultiple
    {
        public static MultipleResult Calculate(MultipleEquation[] equations, double[] startX, double epsilon)
        {
            MultipleEquation[] fiEquations = new MultipleEquation[equations.Length];
            for (int i = 0; i < equations.Length; i++)
            {
                int index = i;
                fiEquations[index] = new MultipleEquation((x) => x[index] - equations[index].Function(x), null, null, equations[index].Variables);
            }
            double maxSum = 0;
            for(int i = 0; i < fiEquations.Length; i++)
            {
                double derivativeSum = 0;
                for(int j = 0; j < startX.Length; j++)
                {
                    derivativeSum += Math.Abs(fiEquations[i].Derivative(startX[j], j));
                }
                maxSum = Math.Max(maxSum, derivativeSum);
            }
            if (maxSum >= 1)
                throw new InvalidOperationException($"Не выполняется условие сходимости метода итерационного процесса. max|φ'(x)| = {maxSum}");
            return Perform(fiEquations, startX, Double.MaxValue, epsilon, 0);
        }
        public static MultipleResult Perform(MultipleEquation[] fiEquations, double[] lastX, double lastDiff, double epsilon, int iteration)
        {
            if (iteration > EquationUtils.LIMIT_ITERATIONS)
                throw new StackOverflowException();
            double[] x = new double[lastX.Length];
            for (int i = 0; i < x.Length; i++)
                x[i] = fiEquations[i].Function(lastX);
            double max = Double.MinValue;
            for(int i = 0; i < x.Length; i++)
                max = Math.Max(max, Math.Abs(x[i] - lastX[i]));
            if (max < epsilon)
            {
                double[] differences = new double[x.Length];
                for (int i = 0; i < x.Length; i++)
                {
                    differences[i] = Math.Abs(x[i] - lastX[i]);
                }
                return new MultipleResult(iteration, x, differences);
            }
            if (max > lastDiff)
                throw new InvalidOperationException("Решение не сходится, уточните начальные приближения");
            return Perform(fiEquations, x, max, epsilon, iteration + 1);
        }
    }
}
