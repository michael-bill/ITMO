namespace Lab2.UI
{
    partial class MultipleForm
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
            this.epsilonBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.calculateButton = new System.Windows.Forms.Button();
            this.changeFormButton = new System.Windows.Forms.Button();
            this.resultLabel = new System.Windows.Forms.Label();
            this.starterYBox = new System.Windows.Forms.TextBox();
            this.toLabel = new System.Windows.Forms.Label();
            this.starterXBox = new System.Windows.Forms.TextBox();
            this.intervalLabel = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.graph = new Microsoft.Web.WebView2.WinForms.WebView2();
            this.firstButton = new System.Windows.Forms.RadioButton();
            this.secondButton = new System.Windows.Forms.RadioButton();
            this.firstPicture = new System.Windows.Forms.PictureBox();
            this.secondPicture = new System.Windows.Forms.PictureBox();
            this.saveButton = new System.Windows.Forms.Button();
            this.loadButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.graph)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.firstPicture)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.secondPicture)).BeginInit();
            this.SuspendLayout();
            // 
            // epsilonBox
            // 
            this.epsilonBox.Location = new System.Drawing.Point(798, 86);
            this.epsilonBox.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.epsilonBox.Name = "epsilonBox";
            this.epsilonBox.Size = new System.Drawing.Size(288, 31);
            this.epsilonBox.TabIndex = 29;
            this.epsilonBox.Text = "0.001";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(630, 92);
            this.label3.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(150, 25);
            this.label3.TabIndex = 28;
            this.label3.Text = "Погрешность:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(630, 136);
            this.label2.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(265, 25);
            this.label2.TabIndex = 26;
            this.label2.Text = "Метод простых итераций";
            // 
            // calculateButton
            // 
            this.calculateButton.Enabled = false;
            this.calculateButton.Location = new System.Drawing.Point(770, 333);
            this.calculateButton.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.calculateButton.Name = "calculateButton";
            this.calculateButton.Size = new System.Drawing.Size(326, 39);
            this.calculateButton.TabIndex = 25;
            this.calculateButton.Text = "Вычислить";
            this.calculateButton.UseVisualStyleBackColor = true;
            this.calculateButton.Click += new System.EventHandler(this.calculateButton_Click);
            // 
            // changeFormButton
            // 
            this.changeFormButton.Location = new System.Drawing.Point(636, 9);
            this.changeFormButton.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.changeFormButton.Name = "changeFormButton";
            this.changeFormButton.Size = new System.Drawing.Size(456, 66);
            this.changeFormButton.TabIndex = 24;
            this.changeFormButton.Text = "Переключиться на нелинейные уравнения";
            this.changeFormButton.UseVisualStyleBackColor = true;
            this.changeFormButton.Click += new System.EventHandler(this.changeFormButton_Click);
            // 
            // resultLabel
            // 
            this.resultLabel.Location = new System.Drawing.Point(764, 378);
            this.resultLabel.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.resultLabel.Name = "resultLabel";
            this.resultLabel.Size = new System.Drawing.Size(332, 397);
            this.resultLabel.TabIndex = 23;
            this.resultLabel.Text = "Результат:\r\nВычисления не проводились\r\n";
            // 
            // starterYBox
            // 
            this.starterYBox.Location = new System.Drawing.Point(818, 227);
            this.starterYBox.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.starterYBox.Name = "starterYBox";
            this.starterYBox.Size = new System.Drawing.Size(196, 31);
            this.starterYBox.TabIndex = 21;
            this.starterYBox.Text = "0.5";
            // 
            // toLabel
            // 
            this.toLabel.AutoSize = true;
            this.toLabel.Location = new System.Drawing.Point(706, 234);
            this.toLabel.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.toLabel.Name = "toLabel";
            this.toLabel.Size = new System.Drawing.Size(78, 25);
            this.toLabel.TabIndex = 20;
            this.toLabel.Text = "Для Y:";
            // 
            // starterXBox
            // 
            this.starterXBox.Location = new System.Drawing.Point(818, 184);
            this.starterXBox.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.starterXBox.Name = "starterXBox";
            this.starterXBox.Size = new System.Drawing.Size(196, 31);
            this.starterXBox.TabIndex = 19;
            this.starterXBox.Text = "0.5";
            // 
            // intervalLabel
            // 
            this.intervalLabel.AutoSize = true;
            this.intervalLabel.Location = new System.Drawing.Point(442, 191);
            this.intervalLabel.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.intervalLabel.Name = "intervalLabel";
            this.intervalLabel.Size = new System.Drawing.Size(339, 25);
            this.intervalLabel.TabIndex = 18;
            this.intervalLabel.Text = "Начальное приближение: Для X:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(16, 31);
            this.label1.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(229, 25);
            this.label1.TabIndex = 16;
            this.label1.Text = "Выберите уравнение:";
            // 
            // graph
            // 
            this.graph.AllowExternalDrop = true;
            this.graph.CreationProperties = null;
            this.graph.DefaultBackgroundColor = System.Drawing.Color.White;
            this.graph.Location = new System.Drawing.Point(24, 283);
            this.graph.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.graph.Name = "graph";
            this.graph.Size = new System.Drawing.Size(728, 541);
            this.graph.TabIndex = 15;
            this.graph.ZoomFactor = 1D;
            // 
            // firstButton
            // 
            this.firstButton.AutoSize = true;
            this.firstButton.Location = new System.Drawing.Point(24, 100);
            this.firstButton.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.firstButton.Name = "firstButton";
            this.firstButton.Size = new System.Drawing.Size(27, 26);
            this.firstButton.TabIndex = 30;
            this.firstButton.TabStop = true;
            this.firstButton.UseVisualStyleBackColor = true;
            this.firstButton.CheckedChanged += new System.EventHandler(this.firstButton_CheckedChanged);
            // 
            // secondButton
            // 
            this.secondButton.AutoSize = true;
            this.secondButton.Location = new System.Drawing.Point(22, 219);
            this.secondButton.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.secondButton.Name = "secondButton";
            this.secondButton.Size = new System.Drawing.Size(27, 26);
            this.secondButton.TabIndex = 31;
            this.secondButton.TabStop = true;
            this.secondButton.UseVisualStyleBackColor = true;
            this.secondButton.CheckedChanged += new System.EventHandler(this.secondButton_CheckedChanged);
            // 
            // firstPicture
            // 
            this.firstPicture.Image = global::Lab2.Properties.Resources.equations1;
            this.firstPicture.Location = new System.Drawing.Point(62, 61);
            this.firstPicture.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.firstPicture.Name = "firstPicture";
            this.firstPicture.Size = new System.Drawing.Size(368, 100);
            this.firstPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.firstPicture.TabIndex = 32;
            this.firstPicture.TabStop = false;
            this.firstPicture.Click += new System.EventHandler(this.firstPicture_Click);
            // 
            // secondPicture
            // 
            this.secondPicture.Image = global::Lab2.Properties.Resources.equations2;
            this.secondPicture.Location = new System.Drawing.Point(62, 173);
            this.secondPicture.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.secondPicture.Name = "secondPicture";
            this.secondPicture.Size = new System.Drawing.Size(368, 100);
            this.secondPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.secondPicture.TabIndex = 33;
            this.secondPicture.TabStop = false;
            this.secondPicture.Click += new System.EventHandler(this.secondPicture_Click);
            // 
            // saveButton
            // 
            this.saveButton.Location = new System.Drawing.Point(768, 781);
            this.saveButton.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(326, 39);
            this.saveButton.TabIndex = 34;
            this.saveButton.Text = "Сохранить";
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // loadButton
            // 
            this.loadButton.Enabled = false;
            this.loadButton.Location = new System.Drawing.Point(770, 283);
            this.loadButton.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.loadButton.Name = "loadButton";
            this.loadButton.Size = new System.Drawing.Size(326, 39);
            this.loadButton.TabIndex = 35;
            this.loadButton.Text = "Загрузить";
            this.loadButton.UseVisualStyleBackColor = true;
            this.loadButton.Click += new System.EventHandler(this.loadButton_Click);
            // 
            // MultipleForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1126, 841);
            this.Controls.Add(this.loadButton);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.secondPicture);
            this.Controls.Add(this.firstPicture);
            this.Controls.Add(this.secondButton);
            this.Controls.Add(this.firstButton);
            this.Controls.Add(this.epsilonBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.calculateButton);
            this.Controls.Add(this.changeFormButton);
            this.Controls.Add(this.resultLabel);
            this.Controls.Add(this.starterYBox);
            this.Controls.Add(this.toLabel);
            this.Controls.Add(this.starterXBox);
            this.Controls.Add(this.intervalLabel);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.graph);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.Name = "MultipleForm";
            this.Text = "Лабораторная работа №2";
            this.Load += new System.EventHandler(this.MultipleForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.graph)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.firstPicture)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.secondPicture)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox epsilonBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button calculateButton;
        private System.Windows.Forms.Button changeFormButton;
        private System.Windows.Forms.Label resultLabel;
        private System.Windows.Forms.TextBox starterYBox;
        private System.Windows.Forms.Label toLabel;
        private System.Windows.Forms.TextBox starterXBox;
        private System.Windows.Forms.Label intervalLabel;
        private System.Windows.Forms.Label label1;
        private Microsoft.Web.WebView2.WinForms.WebView2 graph;
        private System.Windows.Forms.RadioButton firstButton;
        private System.Windows.Forms.RadioButton secondButton;
        private System.Windows.Forms.PictureBox firstPicture;
        private System.Windows.Forms.PictureBox secondPicture;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Button loadButton;
    }
}