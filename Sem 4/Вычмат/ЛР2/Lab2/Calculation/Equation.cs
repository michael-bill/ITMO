using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    public class Equation
    {
        public delegate double Value(double x);
        public const double STEP = 0.00000001;

        private Value function;
        private string lable;
        private string latex;

        internal Value Function { get => function; }
        internal string Latex { get => latex; }

        public Equation(Value value, string lable, string latex)
        {
            this.function = value;
            this.lable = lable;
            this.latex = latex;
        }

        public double Derivative(double x)
        {
            return (Function(x + STEP) - Function(x)) / STEP;
        }

        public double SecondDerivative(double x)
        {
            return (Function(x + STEP) - 2 * Function(x) + Function(x - STEP)) / (STEP * STEP);
        }

        public override string ToString()
        {
            return lable;
        }
    }
}
