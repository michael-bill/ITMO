namespace Lab2.UI
{
    partial class SingleForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.graph = new Microsoft.Web.WebView2.WinForms.WebView2();
            this.label1 = new System.Windows.Forms.Label();
            this.equationBox = new System.Windows.Forms.ComboBox();
            this.intervalLabel = new System.Windows.Forms.Label();
            this.boundBox = new System.Windows.Forms.TextBox();
            this.toLabel = new System.Windows.Forms.Label();
            this.secondBoundBox = new System.Windows.Forms.TextBox();
            this.selectStarerBox = new System.Windows.Forms.CheckBox();
            this.resultLabel = new System.Windows.Forms.Label();
            this.changeFormButton = new System.Windows.Forms.Button();
            this.calculateButton = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.methodBox = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.epsilonBox = new System.Windows.Forms.TextBox();
            this.saveButton = new System.Windows.Forms.Button();
            this.loadButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.graph)).BeginInit();
            this.SuspendLayout();
            // 
            // graph
            // 
            this.graph.AllowExternalDrop = true;
            this.graph.CreationProperties = null;
            this.graph.DefaultBackgroundColor = System.Drawing.Color.White;
            this.graph.Location = new System.Drawing.Point(22, 223);
            this.graph.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.graph.Name = "graph";
            this.graph.Size = new System.Drawing.Size(728, 411);
            this.graph.TabIndex = 0;
            this.graph.ZoomFactor = 1D;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(16, 28);
            this.label1.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(229, 25);
            this.label1.TabIndex = 1;
            this.label1.Text = "Выберите уравнение:";
            // 
            // equationBox
            // 
            this.equationBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.equationBox.FormattingEnabled = true;
            this.equationBox.Location = new System.Drawing.Point(260, 23);
            this.equationBox.Margin = new System.Windows.Forms.Padding(6);
            this.equationBox.Name = "equationBox";
            this.equationBox.Size = new System.Drawing.Size(354, 33);
            this.equationBox.TabIndex = 2;
            this.equationBox.SelectedIndexChanged += new System.EventHandler(this.equationBox_SelectedIndexChanged);
            // 
            // intervalLabel
            // 
            this.intervalLabel.AutoSize = true;
            this.intervalLabel.Location = new System.Drawing.Point(18, 181);
            this.intervalLabel.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.intervalLabel.Name = "intervalLabel";
            this.intervalLabel.Size = new System.Drawing.Size(248, 25);
            this.intervalLabel.TabIndex = 3;
            this.intervalLabel.Text = "Границы интервала: От";
            // 
            // boundBox
            // 
            this.boundBox.Location = new System.Drawing.Point(282, 175);
            this.boundBox.Margin = new System.Windows.Forms.Padding(6);
            this.boundBox.Name = "boundBox";
            this.boundBox.Size = new System.Drawing.Size(196, 31);
            this.boundBox.TabIndex = 4;
            this.boundBox.Text = "-1";
            // 
            // toLabel
            // 
            this.toLabel.AutoSize = true;
            this.toLabel.Location = new System.Drawing.Point(494, 181);
            this.toLabel.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.toLabel.Name = "toLabel";
            this.toLabel.Size = new System.Drawing.Size(40, 25);
            this.toLabel.TabIndex = 5;
            this.toLabel.Text = "До";
            // 
            // secondBoundBox
            // 
            this.secondBoundBox.Location = new System.Drawing.Point(550, 175);
            this.secondBoundBox.Margin = new System.Windows.Forms.Padding(6);
            this.secondBoundBox.Name = "secondBoundBox";
            this.secondBoundBox.Size = new System.Drawing.Size(196, 31);
            this.secondBoundBox.TabIndex = 6;
            this.secondBoundBox.Text = "1";
            // 
            // selectStarerBox
            // 
            this.selectStarerBox.AutoSize = true;
            this.selectStarerBox.Location = new System.Drawing.Point(24, 142);
            this.selectStarerBox.Margin = new System.Windows.Forms.Padding(6);
            this.selectStarerBox.Name = "selectStarerBox";
            this.selectStarerBox.Size = new System.Drawing.Size(395, 29);
            this.selectStarerBox.TabIndex = 7;
            this.selectStarerBox.Text = "Выбирать начальное приближение";
            this.selectStarerBox.UseVisualStyleBackColor = true;
            this.selectStarerBox.CheckedChanged += new System.EventHandler(this.selectStarerBox_CheckedChanged);
            // 
            // resultLabel
            // 
            this.resultLabel.Location = new System.Drawing.Point(760, 223);
            this.resultLabel.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.resultLabel.Name = "resultLabel";
            this.resultLabel.Size = new System.Drawing.Size(332, 361);
            this.resultLabel.TabIndex = 8;
            this.resultLabel.Text = "Результат:\r\nВычисления не проводились\r\n";
            // 
            // changeFormButton
            // 
            this.changeFormButton.Location = new System.Drawing.Point(636, 8);
            this.changeFormButton.Margin = new System.Windows.Forms.Padding(6);
            this.changeFormButton.Name = "changeFormButton";
            this.changeFormButton.Size = new System.Drawing.Size(456, 66);
            this.changeFormButton.TabIndex = 9;
            this.changeFormButton.Text = "Переключиться на системы нелинейных уравнений";
            this.changeFormButton.UseVisualStyleBackColor = true;
            this.changeFormButton.Click += new System.EventHandler(this.changeFormButton_Click);
            // 
            // calculateButton
            // 
            this.calculateButton.Enabled = false;
            this.calculateButton.Location = new System.Drawing.Point(766, 175);
            this.calculateButton.Margin = new System.Windows.Forms.Padding(6);
            this.calculateButton.Name = "calculateButton";
            this.calculateButton.Size = new System.Drawing.Size(326, 39);
            this.calculateButton.TabIndex = 10;
            this.calculateButton.Text = "Вычислить";
            this.calculateButton.UseVisualStyleBackColor = true;
            this.calculateButton.Click += new System.EventHandler(this.calculateButton_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(16, 91);
            this.label2.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(184, 25);
            this.label2.TabIndex = 11;
            this.label2.Text = "Выберите метод:";
            // 
            // methodBox
            // 
            this.methodBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.methodBox.FormattingEnabled = true;
            this.methodBox.Items.AddRange(new object[] {
            "Метод хорд",
            "Метод Ньютона",
            "Метод простой итерации"});
            this.methodBox.Location = new System.Drawing.Point(260, 84);
            this.methodBox.Margin = new System.Windows.Forms.Padding(6);
            this.methodBox.Name = "methodBox";
            this.methodBox.Size = new System.Drawing.Size(354, 33);
            this.methodBox.TabIndex = 12;
            this.methodBox.SelectedIndexChanged += new System.EventHandler(this.methodBox_SelectedIndexChanged);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(630, 91);
            this.label3.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(150, 25);
            this.label3.TabIndex = 13;
            this.label3.Text = "Погрешность:";
            // 
            // epsilonBox
            // 
            this.epsilonBox.Location = new System.Drawing.Point(798, 84);
            this.epsilonBox.Margin = new System.Windows.Forms.Padding(6);
            this.epsilonBox.Name = "epsilonBox";
            this.epsilonBox.Size = new System.Drawing.Size(288, 31);
            this.epsilonBox.TabIndex = 14;
            this.epsilonBox.Text = "0.001";
            // 
            // saveButton
            // 
            this.saveButton.Location = new System.Drawing.Point(766, 591);
            this.saveButton.Margin = new System.Windows.Forms.Padding(6);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(326, 39);
            this.saveButton.TabIndex = 15;
            this.saveButton.Text = "Сохранить";
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // loadButton
            // 
            this.loadButton.Enabled = false;
            this.loadButton.Location = new System.Drawing.Point(766, 131);
            this.loadButton.Margin = new System.Windows.Forms.Padding(6);
            this.loadButton.Name = "loadButton";
            this.loadButton.Size = new System.Drawing.Size(326, 39);
            this.loadButton.TabIndex = 16;
            this.loadButton.Text = "Загрузить";
            this.loadButton.UseVisualStyleBackColor = true;
            this.loadButton.Click += new System.EventHandler(this.loadButton_Click);
            // 
            // SingleForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1114, 653);
            this.Controls.Add(this.loadButton);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.epsilonBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.methodBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.calculateButton);
            this.Controls.Add(this.changeFormButton);
            this.Controls.Add(this.resultLabel);
            this.Controls.Add(this.selectStarerBox);
            this.Controls.Add(this.secondBoundBox);
            this.Controls.Add(this.toLabel);
            this.Controls.Add(this.boundBox);
            this.Controls.Add(this.intervalLabel);
            this.Controls.Add(this.equationBox);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.graph);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.Name = "SingleForm";
            this.Text = "Лабораторная работа №2";
            this.Load += new System.EventHandler(this.MainForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.graph)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Microsoft.Web.WebView2.WinForms.WebView2 graph;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox equationBox;
        private System.Windows.Forms.Label intervalLabel;
        private System.Windows.Forms.TextBox boundBox;
        private System.Windows.Forms.Label toLabel;
        private System.Windows.Forms.TextBox secondBoundBox;
        private System.Windows.Forms.CheckBox selectStarerBox;
        private System.Windows.Forms.Label resultLabel;
        private System.Windows.Forms.Button changeFormButton;
        private System.Windows.Forms.Button calculateButton;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox methodBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox epsilonBox;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Button loadButton;
    }
}