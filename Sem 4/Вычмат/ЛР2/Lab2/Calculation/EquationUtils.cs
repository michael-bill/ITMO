using System;
using System.Collections;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab2.Calculation
{
    public static class EquationUtils
    {
        public const int LIMIT_ITERATIONS = 5000;
        public static bool HasSolutions(Equation.Value equation, Tuple<double, double> limits)
        {
            int countRoots = 0;
            double epsilon = 0.0001;
            for (double left = limits.Item1; left <= limits.Item2; left += epsilon)
            {
                double right = left + epsilon;
                if (equation(left) * equation(right) < 0)
                    countRoots++;
                if (countRoots > 0) return true;
            }
            return false;
        }

        public static List<double> countRoots(Equation.Value equation, Tuple<double, double> limits, double epsilon)
        {
            List<double> roots = new List<double>();
            for (double left = limits.Item1; left <= limits.Item2; left += epsilon)
            {
                double right = left + epsilon;
                if (equation(left) * equation(right) < 0)
                {
                    roots.Add(left);
                }
            }
            return roots;
        }

        public static bool IsDoubleValid(string text)
        {
            return double.TryParse(text.Replace(".", ","), out double result);
        }
        public static bool IsDoublePositive(string text)
        {
            if (double.TryParse(text.Replace(".", ","), out double result))
                return result > 0;
            return false;
        }

        public static void SetTextColor(TextBox textBox, bool valid)
        {
            if (valid)
                textBox.BackColor = SystemColors.Window;
            else
                textBox.BackColor = Color.LightSalmon;
        }

        public static string FormatNumber(double number)
        {
            if (number.ToString().Contains("E"))
            {
                string[] parts = number.ToString().Split('E');
                double coefficient = double.Parse(parts[0]);
                int exponent = int.Parse(parts[1]);

                return $"{coefficient} · 10^({exponent})";
            }

            return number.ToString();
        }
    }
}
