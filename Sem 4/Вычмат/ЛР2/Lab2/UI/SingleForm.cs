using Lab2.Calculation;
using Lab2.Properties;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab2.UI
{
    public partial class SingleForm : Form
    {
        public Equation[] equations = new Equation[]{
            new Equation((x) => x, "Не выбрано", ""),
            new Equation((x) => -1.38*x*x*x - 5.42*x*x + 2.57*x + 10.95, "-1.38x³ - 5.42x² + 2.57x + 10.95 = 0", "-1.38x^3 - 5.42x^2 + 2.57x + 10.95"),
            new Equation((x) => x*x*x - x + 4, "x³ - x + 4 = 0", "x^3 - x + 4"),
            new Equation((x) => x*x*x - 1.89d*x*x - 2*x + 1.76, "x³ - 1,89x² - 2x + 1,76 = 0", "x^3 - 1.89x^2 - 2x + 1.76"),
            new Equation((x) => Math.Pow(Math.E, x) - 3d, "exp(x) - 3 = 0", "e^{x} - 3")
        };

        public SingleForm()
        {
            InitializeComponent();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            BackColor = Color.White;
            MinimumSize = Size;
            MaximumSize = Size;
            MaximizeBox = false;
            string indexPath = System.IO.Path.Combine(Application.StartupPath, "UI", "Desmos", "index.html");
            graph.Source = new Uri(indexPath);

            equationBox.DataSource = equations;
            methodBox.SelectedIndex = 0;
            boundBox.TextChanged += ValidateDouble;
            secondBoundBox.TextChanged += ValidateDouble;
            epsilonBox.TextChanged += ValidatePositiveDouble;
        }

        private void equationBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            RerenderButton();
            if (graph.CoreWebView2 == null) return;
            graph.CoreWebView2.ExecuteScriptAsync($"clear()");
            if (equationBox.SelectedIndex == 0)
                return;
            graph.CoreWebView2.ExecuteScriptAsync($"expression('{((Equation)equationBox.SelectedItem).Latex}')");
        }

        private double ParseDouble(string text)
        {
            return double.Parse(text.Replace(".", ","));
        }

        private void UpdateGraphBounds()
        {
            if (graph.CoreWebView2 == null) return;
            if (selectStarerBox.Checked)
            {
                if (!EquationUtils.IsDoubleValid(boundBox.Text))
                    graph.CoreWebView2.ExecuteScriptAsync($"setBounds(0, 0)");
                else
                    graph.CoreWebView2.ExecuteScriptAsync($"setBounds(-999999, 999999)");
                return;
            }
            if (!EquationUtils.IsDoubleValid(boundBox.Text) || !EquationUtils.IsDoubleValid(secondBoundBox.Text)) return;
            graph.CoreWebView2.ExecuteScriptAsync($"setBounds({boundBox.Text.Replace(",",".")}, {secondBoundBox.Text.Replace(",", ".")})");
        }
        private void RerenderButton()
        {
            calculateButton.Enabled = CheckCalculability();
        }
        private bool CheckCalculability()
        {
            if (equationBox.SelectedIndex == 0) return false;
            if (!EquationUtils.IsDoubleValid(boundBox.Text)) return false;
            if (!EquationUtils.IsDoublePositive(epsilonBox.Text)) return false;
            if (!selectStarerBox.Checked)
            {
                if(!EquationUtils.IsDoubleValid(secondBoundBox.Text)) return false;
                if (ParseDouble(secondBoundBox.Text) <= ParseDouble(boundBox.Text)) return false;
            }
            return true;
        }

        private void selectStarerBox_CheckedChanged(object sender, EventArgs e)
        {
            if(selectStarerBox.Checked == true)
            {
                intervalLabel.Text = "Начальное приближение:";
                toLabel.Visible = false;
                secondBoundBox.Visible = false;
            } else
            {
                intervalLabel.Text = "Границы интервала: От";
                toLabel.Visible = true;
                secondBoundBox.Visible = true;
            }
            UpdateGraphBounds();
        }
        private void calculateButton_Click(object sender, EventArgs e)
        {
            int method = methodBox.SelectedIndex;
            Result result = null;

            double leftBound = ParseDouble(boundBox.Text);
            double rightBound;
            double.TryParse(secondBoundBox.Text.Replace(".", ","), out rightBound);
            if(selectStarerBox.Checked)
                rightBound = leftBound;
            Tuple<double, double> limit = new Tuple<double, double>(leftBound, rightBound);
            double epsilon = ParseDouble(epsilonBox.Text);
            Equation equation = (Equation)equationBox.SelectedItem;
            try
            {
                double findRootsEpsilon = 1;
                List<double> roots = EquationUtils.countRoots(equation.Function, limit, findRootsEpsilon);
                if (roots.Count > 1)
                {
                    resultLabel.Text = "";
                    for (int i = 0; i < roots.Count; i++)
                    {
                        var newLimit = new Tuple<double, double>(roots[i], roots[i] + findRootsEpsilon);
                        if (method == 0) result = ChordMethod.Calculate(equation, newLimit, epsilon);
                        else if (method == 1) result = Newton.Calculate(equation, newLimit, epsilon);
                        else if (method == 2) result = SimpleIteration.Calculate(equation, newLimit, epsilon);
                        resultLabel.Text += $"{i + 1}. Результат:\nx = {EquationUtils.FormatNumber(result.Data)}\n" +
                            $"y = {EquationUtils.FormatNumber(equation.Function(result.Data))}\nКоличество итераций: {result.Iterations}\n";
                    }
                } else
                {
                    if (method == 0) result = ChordMethod.Calculate(equation, limit, epsilon);
                    else if (method == 1) result = Newton.Calculate(equation, limit, epsilon);
                    else if (method == 2) result = SimpleIteration.Calculate(equation, limit, epsilon);
                    resultLabel.Text = $"Результат:\nx = {EquationUtils.FormatNumber(result.Data)}\n" +
                        $"y = {EquationUtils.FormatNumber(equation.Function(result.Data))}\nКоличество итераций: {result.Iterations}";
                }
            } catch (InvalidOperationException ex)
            {
                MessageBox.Show(ex.Message, "Ошибка вычислений", MessageBoxButtons.OK, MessageBoxIcon.Error);
            } catch (StackOverflowException)
            {
                MessageBox.Show("Превышен лимит итераций", "Ошибка вычислений", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void methodBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (methodBox.SelectedIndex == 0)
            {
                selectStarerBox.Visible = false;
                selectStarerBox.Checked = false;
            }
            else selectStarerBox.Visible = true;
            UpdateGraphBounds();
        }

        private void changeFormButton_Click(object sender, EventArgs e)
        {
            MultipleForm form = new MultipleForm();
            Hide();
            form.ShowDialog();
            Close();
        }

        public void ValidateDouble(object sender, EventArgs e)
        {
            string text = ((TextBox)sender).Text;
            EquationUtils.SetTextColor((TextBox)sender, EquationUtils.IsDoubleValid(text));
            Rerender();
        }
        public void ValidatePositiveDouble(object sender, EventArgs e)
        {
            string text = ((TextBox)sender).Text;
            EquationUtils.SetTextColor((TextBox)sender, EquationUtils.IsDoublePositive(text));
            Rerender();
        }

        public void Rerender()
        {
            UpdateGraphBounds();
            RerenderButton();
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
                    int index = 0;
                    if (int.TryParse(lines[1], out index) && index >= 0 && index < methodBox.Items.Count)
                        methodBox.SelectedIndex = index;
                    epsilonBox.Text = lines[2];
                    selectStarerBox.Checked = lines[3].ToLower() == "y";
                    index = 0;
                    if (int.TryParse(lines[0], out index) && index >= 0 && index < equationBox.Items.Count)
                        equationBox.SelectedIndex = index;
                    boundBox.Text = lines[4];
                    if(lines.Length > 4)
                        secondBoundBox.Text = lines[5];
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Ошибка при чтении файла: {ex.Message}", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }
    }
}
