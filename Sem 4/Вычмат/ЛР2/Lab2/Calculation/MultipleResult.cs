using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    internal class MultipleResult
    {
        private int iterations;
        private double[] data;
        private double[] errors;

        public int Iterations { get => iterations; set => iterations = value; }
        public double[] Data { get => data; set => data = value; }
        public double[] Errors { get => errors; set => errors = value; }

        public MultipleResult(int iterations, double[] data, double[] errors)
        {
            this.iterations = iterations;
            this.data = data;
            this.errors = errors;
        }
    }
}
