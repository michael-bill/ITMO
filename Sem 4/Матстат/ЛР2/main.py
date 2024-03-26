ns = [-2.53, -0.74, -4.49, 1.27, -5.37, 3.77, -3.35, -1.87, -2.62, -0.38]
print('Выборка: ', ns)

ns = sorted(ns)
print('Вариационный ряд: ', ns)

mx = 0
for i in range(len(ns)):
    mx += ns[i]
mx /= len(ns)

print('Математическое ожидание: ', mx)

dx = 0
for i in range(len(ns)):
    dx += (ns[i] - mx) ** 2
dx /= (len(ns) - 1)

print('Дисперсия: ', dx)

ox = dx ** 0.5
print('Стандартное отклонение: ', ox)


import numpy as np
import matplotlib.pyplot as plt
ecdf = np.arange(1, len(ns) + 1) / len(ns)
plt.step(ns, ecdf, where="post", label="Эмпирическая функция распределения")
plt.xlabel('Значения')
plt.ylabel('F(x)')
plt.title('График эмпирической функции распределения')
plt.legend()
plt.grid(True)
plt.show()

from math import exp, sqrt, pi
def f(x):
    return (1 / (sqrt(2 * pi * 2.73))) * exp(-(x + 1.63)**2 / (2 * 7.43))

from scipy.integrate import quad
def phi(z):
    result, _ = quad(lambda t: exp(-t**2 / 2), 0, z)
    return (2 / sqrt(2 * pi)) * result

def F(x):
    return 0.5 * (1 + phi((x + 1.63) / 2.73))

x_values = np.linspace(-10, 10, 400)

f_values = [f(x) for x in x_values]
F_values = [F(x) for x in x_values]

plt.figure(figsize=(14, 7))

plt.subplot(1, 2, 1)
plt.plot(x_values, f_values, label="f(x)", color="blue")
plt.title("Функция плотности вероятности f(x)")
plt.xlabel("x")
plt.ylabel("f(x)")
plt.grid(True)
plt.legend()

plt.subplot(1, 2, 2)
plt.plot(x_values, F_values, label="F(x)", color="red")
plt.title("Функция распределения F(x)")
plt.xlabel("x")
plt.ylabel("F(x)")
plt.grid(True)
plt.legend()

plt.tight_layout()
plt.show()
