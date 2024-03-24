using Lab2.Calculation;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab2.UI
{
    public partial class MultipleForm : Form
    {
        public MultipleEquation[][] equations = new MultipleEquation[2][]{
            new MultipleEquation[]
            {
                new MultipleEquation((x) => Math.Tan(x[0] * x[1] + 0.1) - x[0] * x[0], null, "\\\\tan(xy+0.1)=x^2", 2),
                new MultipleEquation((x) => x[0]*x[0]+2d*x[1]*x[1]-1d, null, "x^2+2y^2=1", 2)
            },
            new MultipleEquation[]
            {
                new MultipleEquation((x) => 0.1d*x[0]*x[0] + x[0] + 0.2d*x[1]*x[1] - 0.3d, null, "0.1x^2+x+0.2y^2-0.3=0", 2),
                new MultipleEquation((x) => 0.2d * x[0] * x[0] + x[1] + 0.1d * x[0] * x[1] - 0.7d, null, "0.2x^2+y+0.1xy-0.7=0", 2)
            }
        };
        public MultipleForm()
        {
            InitializeComponent();
        }

        private void MultipleForm_Load(object sender, EventArgs e)
        {
            BackColor = Color.White;
            MinimumSize = Size;
            MaximumSize = Size;
            MaximizeBox = false;
            string indexPath = System.IO.Path.Combine(Application.StartupPath, "UI", "Desmos", "index.html");
            graph.Source = new Uri(indexPath);

            starterXBox.TextChanged += ValidateDouble;
            starterYBox.TextChanged += ValidateDouble;
            epsilonBox.TextChanged += ValidatePositiveDouble;
        }

        private void calculateButton_Click(object sender, EventArgs e)
        {
            double x = ParseDouble(starterXBox.Text);
            double y = ParseDouble(starterYBox.Text);
            double epsilon = ParseDouble(epsilonBox.Text);
            MultipleEquation[] equation = firstButton.Checked ? equations[0] : equations[1];
            try
            {
                MultipleResult result = SimpleIterationMultiple.Calculate(equation, new double[] { x, y }, epsilon);
                resultLabel.Text = $"Результат:" +
                    $"\nВектор неизвестных:" +
                    $"\nx ={EquationUtils.FormatNumber(result.Data[0])}" +
                    $"\ny = {EquationUtils.FormatNumber(result.Data[1])}" +
                    $"\nВектор погрешностей:" +
                    $"\ndx ={EquationUtils.FormatNumber(result.Errors[0])}" +
                    $"\ndy ={EquationUtils.FormatNumber(result.Errors[1])}" +
                    $"\n\nРезультат получен за {result.Iterations} итераций" +
                    $"\n\nРасхождения:\n{equation[0].Function(result.Data)}\n{equation[1].Function(result.Data)}";
            }
            catch (InvalidOperationException ex)
            {
                MessageBox.Show(ex.Message, "Ошибка вычислений", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (StackOverflowException)
            {
                MessageBox.Show("Превышен лимит итераций", "Ошибка вычислений", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void changeFormButton_Click(object sender, EventArgs e)
        {
            SingleForm form = new SingleForm();
            Hide();
            form.ShowDialog();
            Close();
        }

        private void firstButton_CheckedChanged(object sender, EventArgs e)
        {
            if (!firstButton.Checked) return;
            if(secondButton.Checked) secondButton.Checked = false;
            RerenderGraph();
        }

        private void secondButton_CheckedChanged(object sender, EventArgs e)
        {
            if (!secondButton.Checked) return;
            if (firstButton.Checked) firstButton.Checked = false;
            RerenderGraph();
        }

        public void ValidateDouble(object sender, EventArgs e)
        {
            string text = ((TextBox)sender).Text;
            EquationUtils.SetTextColor((TextBox)sender, EquationUtils.IsDoubleValid(text));
            RerenderButton();
        }
        public void ValidatePositiveDouble(object sender, EventArgs e)
        {
            string text = ((TextBox)sender).Text;
            EquationUtils.SetTextColor((TextBox)sender, EquationUtils.IsDoublePositive(text));
            RerenderButton();
        }
        private void RerenderButton()
        {
            calculateButton.Enabled = CheckCalculability();
        }
        private bool CheckCalculability()
        {
            if (!firstButton.Checked && !secondButton.Checked) return false;
            if (!EquationUtils.IsDoubleValid(starterXBox.Text)) return false;
            if (!EquationUtils.IsDoubleValid(starterYBox.Text)) return false;
            if (!EquationUtils.IsDoublePositive(epsilonBox.Text)) return false;
            return true;
        }

        private void RerenderGraph()
        {
            if (graph.CoreWebView2 == null) return;
            graph.CoreWebView2.ExecuteScriptAsync($"clear()");
            List<string> script = new List<string>();
            if (firstButton.Checked)
            {
                foreach(MultipleEquation equation in equations[0])
                {
                    script.Add($"'{equation.Latex}'");
                }
            } else if(secondButton.Checked)
            {
                foreach (MultipleEquation equation in equations[1])
                {
                    script.Add($"'{equation.Latex}'");
                }
            }
            graph.CoreWebView2.ExecuteScriptAsync($"expressions([{String.Join(", ", script)}])");
            RerenderButton();
        }

        private double ParseDouble(string text)
        {
            return double.Parse(text.Replace(".", ","));
        }

        private void firstPicture_Click(object sender, EventArgs e)
        {
            firstButton.Checked = true;
        }

        private void secondPicture_Click(object sender, EventArgs e)
        {
            secondButton.Checked = true;
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.Filter = "Текстовые файлы (*.txt)|*.txt|Все файлы (*.*)|*.*";
            saveFileDialog.Title = "Сохранить файл";

            if (saveFileDialog.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string filePath = saveFileDialog.FileName;
                    string valueToSave = resultLabel.Text;
                    File.WriteAllText(filePath, valueToSave);
                    MessageBox.Show("Файл успешно сохранен", "Успех", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Ошибка при сохранении файла: {ex.Message}", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void loadButton_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "Текстовые файлы (*.txt)|*.txt|Все файлы (*.*)|*.*";
            openFileDialog.Title = "Открыть файл";

            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string filePath = openFileDialog.FileName;
                    string[] lines = File.ReadAllLines(filePath);
                    firstButton.Checked = lines[0] == "1";
                    secondButton.Checked = lines[0] == "2";
                    epsilonBox.Text = lines[1];
                    starterXBox.Text = lines[2];
                    starterYBox.Text = lines[3];
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Ошибка при чтении файла: {ex.Message}", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }
    }
}
