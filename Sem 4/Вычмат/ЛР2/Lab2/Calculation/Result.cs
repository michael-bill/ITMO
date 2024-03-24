using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2.Calculation
{
    internal class Result
    {
        private int iterations;
        private double data;

        public int Iterations { get => iterations; set => iterations = value; }
        public double Data { get => data; set => data = value; }

        public Result(int iterations, double data)
        {
            this.iterations = iterations;
            this.data = data;
        }
    }
}
