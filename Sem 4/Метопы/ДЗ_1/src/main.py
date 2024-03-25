from equation_solver import Equation
from math import log, sin, pi

def f(x): return log(1 + x ** 2) - sin(x)

equation = Equation(f=f, a=-1, b=pi / 4, eps=0.03)

print('\n' + str(equation.python_method()) + '\n')
print(str(equation.bisection_method()) + '\n')
print(str(equation.newton_method()) + '\n')
print(str(equation.golden_section_method()) + '\n')

equation.show()
