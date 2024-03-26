from scipy.optimize import root_scalar
import numpy as np
import matplotlib.pyplot as plt
from equation_result import Result

class Equation:
    def __init__(self, f, a, b, eps, max_iterations=5000) -> None:
        self.f = f
        self.a = a
        self.b = b
        self.eps = eps
        self.max_iterations = max_iterations
    
    # Метод библиотеки scipy
    def python_method(self) -> Result:
        method_name = "Библиотека Python"
        try:
            result = root_scalar(self.first_derivative, bracket=[self.a, self.b], xtol=self.eps)
            if result.converged: return Result(result.root, result.iterations, method_name, self)
            else: return None
        except ValueError as e:
            return Result(success=False, message=str(e), method_name=method_name, equation=self)

    # Метод половинного деления
    def bisection_method(self) -> Result:
            method_name = "Половинного деления"
            table = [["#", "a", "b", "f(x1)", "f(x2)", "|a-b|"]]

            a, b = self.a, self.b
            f = self.first_derivative
            i = 0

            if f(a) * f(b) >= 0:
                return Result(
                    success=False,
                    message="Метод не применим. f(a) и f(b) Должны иметь разные знаки.",
                    method_name=method_name,
                    equation=self)

            while (b - a) / 2.0 > self.eps and i < self.max_iterations:
                i += 1

                x0 = (a + b) / 2.0
                if f(x0) == 0:
                    return x0
                elif f(a) * f(x0) < 0:
                    b = x0
                else:
                    a = x0
                
                table.append([i, a, b, f(a), f(b), abs(b - a)])

            return Result((a + b) / 2.0, i, method_name, self, table)

    # Метод Ньютона
    def newton_method(self) -> Result:
        method_name = "Ньютона"
        table = [["#", "x_i", "f(x)", "f'(x)", "x_{i+1}"]]
        
        a, b = self.a, self.b
        f = self.first_derivative
        i = 0

        if self.first_derivative(a) * self.first_derivative(b) >= 0:
            return Result(
                success=False,
                message="Метод не применим. f'(a) и f'(b) Должны иметь разные знаки.",
                method_name=method_name,
                equation=self)

        x0 = None

        while abs(f(a)) > self.eps and i < self.max_iterations:
            i += 1

            x0 = a - f(a) / (f(b) - f(a)) * (b - a)
            prev_x = x0

            if self.second_derivative(x0) == 0:
                return Result(
                    success=False,
                    message="Метод не применим. f'(x0) = 0.",
                    method_name=method_name,
                    equation=self)

            if f(x0) == 0:
                break
            elif f(a) * f(x0) < 0:
                b = x0
            else:
                a = x0
            
            table.append([i, prev_x, f(x0), self.first_derivative(x0), x0])

        return Result(x0, i, method_name, self, table)

    # Метод золотого сечения
    def golden_section_method(self) -> Result:
        method_name = "Золотого сечения"
        table = [["#", "a", "b", "c", "d", "f(c)", "f(d)", "|a-b|"]]

        a, b = self.a, self.b
        f = self.f
        i = 0

        fc, fd = 0, 0
        golden_ratio = (1 + 5 ** 0.5) / 2
        c = b - (b - a) / golden_ratio
        d = a + (b - a) / golden_ratio

        while abs(c - d) > self.eps and i < self.max_iterations:
            i += 1

            if fc == 0: fc = f(c)
            if fd == 0: fd = f(d)

            if fc < fd:
                b = d
                fc, fd = 0, fc
            else:
                a = c
                fc, fd = fd, 0

            table.append([i, a, b, c, d, fc, fd, abs(b - a)])
            
            c = b - (b - a) / golden_ratio
            d = a + (b - a) / golden_ratio
        
        return Result((a + b) / 2, i, method_name, self, table)
     
    def first_derivative(self, x, h=1e-6):
        f = self.f
        return (f(x + h) - f(x)) / h

    def second_derivative(self, x, h=1e-6):
        f = self.f
        return (f(x + 2*h) - 2*f(x + h) + f(x)) / (h**2)

    def show(self, left=-10, right=10):
        x = np.linspace(left, right, 100)
        y = [self.f(x_i) for x_i in x]
        plt.plot(x, y)
        plt.xlabel('x')
        plt.ylabel('f(x)')
        plt.grid(True)
        plt.axhline(0, color='black', lw=0.5)
        plt.axvline(0, color='black', lw=0.5)
        plt.show()
