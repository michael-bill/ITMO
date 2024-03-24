using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    public class MultipleEquation
    {
        public delegate double Value(params double[] x);
        public const double STEP = 0.00000001;

        private Value function;
        private string lable;
        private int variables;
        private string latex;

        internal Value Function { get => function; }
        internal int Variables { get => variables; }
        internal string Latex { get => latex; }

        public MultipleEquation(Value value, string lable, string latex, int variables)
        {
            this.function = value;
            this.lable = lable;
            this.variables = variables;
            this.latex = latex;
        }

        public double Derivative(double x, int index)
        {
            double[] values = new double[variables];
            for (int i = 0; i < variables; i++)
                values[i] = 1;
            values[index] = x;
            double[] stepValues = new double[variables];
            values.CopyTo(stepValues, 0);
            stepValues[index] = x + STEP;
            return (Function(stepValues) - Function(values)) / STEP;
        }

        public override string ToString()
        {
            return lable;
        }
    }
}
