import numpy as np
from matplotlib import pyplot as plt
import math

# Функция для нахождения коэффициентов полинома степени n, аппроксимирующего данные
def polynomial_n(x, y, n):
    # Количество точек данных
    m = len(x)
    
    # Составление матрицы A для системы линейных уравнений
    # Матрица A будет размером n x n, где n - степень полинома
    A = np.zeros((n, n))
    for i in range(n):
        for j in range(n):
            A[i, j] = sum([k ** (i + j) for k in x])

    # Составление вектора b для системы линейных уравнений
    # Вектор b будет размером n, где n - степень полинома
    b = np.zeros(n)
    for i in range(n):
        b[i] = sum([x[j] ** i * y[j] for j in range(m)])
    
    # Решение системы уравнений Ax = b для нахождения коэффициентов полинома
    # np.linalg.solve решает систему линейных уравнений и возвращает вектор коэффициентов
    coefficients = np.linalg.solve(A, b)
    
    # Возврат коэффициентов в обратном порядке для удобства
    # (обычно коэффициенты полинома записываются начиная с высшей степени)
    return coefficients[::-1]

# Функция для линейной аппроксимации методом наименьших квадратов
def linear_mnk(x, y):
    return polynomial_n(x, y, 2)

# Функция для аппроксимации полиномом второй степени
def polynomial_2(x, y):
    return polynomial_n(x, y, 3)

# Функция для аппроксимации полиномом третьей степени
def polynomial_3(x, y):
    return polynomial_n(x, y, 4)

# Функция для экспоненциальной аппроксимации
def exponential(x, y):
    a = linear_mnk(x, [math.log(i) for i in y])
    return math.exp(a[0]), a[1]

# Функция для логарифмической аппроксимации
def logarithmic(x, y):
    a = linear_mnk([math.log(i) for i in x], y) 
    return a[0], a[1]

# Функция для степенной аппроксимации
def stepennoy(x, y):
    a = linear_mnk([math.log(i) for i in x], [math.log(i) for i in y])
    return math.exp(a[1]), a[0]

# Функция для вычисления среднего значения списка
def mean(x):
    return sum(x) / len(x)

# Функция для вычисления коэффициента корреляции Пирсона
def pirson(x, y):
    return sum(
        [(x[i] - mean(x)) * (y[i] - mean(y)) for i in range(len(x))]
    ) / math.sqrt(
        sum([(x[i] - mean(x)) ** 2 for i in range(len(x))])
        * sum([(y[i] - mean(y)) ** 2 for i in range(len(y))])
    )

# Список функций аппроксимации и их соответствующих функций для нахождения коэффициентов
approxes = [
    [lambda k, x: k[0] * x + k[1], linear_mnk],
    [lambda k, x: k[0] * x**2 + k[1] * x + k[2], polynomial_2],
    [lambda k, x: k[0] * x**3 + k[1] * x**2 + k[2] * x + k[3], polynomial_3],
    [lambda k, x: k[0] * math.exp(k[1] * x), exponential],
    [lambda k, x: k[0] * math.log(x) + k[1], logarithmic],
    [lambda k, x: k[0] * x ** k[1], stepennoy],
]

# Список названий функций аппроксимации
approxes_names = [
    "Линейная",
    "Квадратичная",
    "Кубическая",
    "Экспоненциальная",
    "Логарифмическая",
    "Степенная"
]

# Функция для вычисления коэффициента детерминации
def determination(x, y, f):
    r2 = sum([(y[j] - f[j]) ** 2 for j in range(len(y))])
    f_mean = mean(f)
    s = sum([(y[j] - f_mean) ** 2 for j in range(len(y))])
    r2 = abs(1 - (r2 / s))
    return r2

# Функция для выбора наилучшей функции аппроксимации
def select_best_approx(x, y):
    koeffs = []
    for i in range(len(approxes)):
        try:
            # Попытка найти коэффициенты для каждой функции аппроксимации
            koeffs += [[i, approxes[i][1](x, y)]]
        except ValueError:
            pass
    # Вычисление среднеквадратичных отклонений для каждой аппроксимации
    e = [
        [
            (approxes[koeffs[i][0]][0](koeffs[i][1], x[j]) - y[j]) ** 2
            for j in range(len(x))
        ]
        for i in range(len(koeffs))
    ]
    e = [math.sqrt(sum(i) / len(i)) for i in e]
    for j in range(len(koeffs)):
        print(approxes_names[koeffs[j][0]])
        print("Коэффициенты", koeffs[j][1])
        print("СКО", e[j])
        # Вычисление коэффициента детерминации
        r2 = determination(
            x, y, [approxes[koeffs[j][0]][0](koeffs[j][1], i) for i in x]
        )
        print("Коэффициент детерминации", ":", r2)
        print("Точность аппроксимации: ", end=" ")
        if r2 >= 0.95:
            print("высокая")
        elif r2 >= 0.75:
            print("удовлетворительная")
        elif r2 >= 0.5:
            print("слабая")
        else:
            print("недостаточная")
        if koeffs[j][0] == 0:
            print("Коэффициент корреляции Пирсона", pirson(a, b))
        print("  x  |  y  |  f  | e")
        for k in range(len(x)):
            print(
                f"{x[k]:.3f}|{y[k]:.3f}|{approxes[koeffs[j][0]][0](koeffs[j][1],x[k]):.3f}|{approxes[koeffs[j][0]][0](koeffs[j][1],x[k])-y[k]:.3f}"
            )
        print()
    l = []
    for o, i in enumerate(e):
        if i == min(e):
            l += [o]
    return [koeffs[i] for i in l]


while True:
    try:
        n = int(input("Введите количество точек -> "))
        break
    except ValueError:
        print("Неправильный ввод")
a = []
b = []
xs = set()
while len(a) < n:
    s = list(map(float, input(f"Введите точку номер {len(a)+1} (x, y) -> ").split()))
    if s[0] not in xs:
        xs.add(s[0])
        a += [s[0]]
        b += [s[1]]
    else:
        print("Координата x не должна повторяться")

# Выбор наилучшей функции аппроксимации
k = select_best_approx(a, b)
print("Лучшая функция аппроксимации: ", end="")
for l in k:
    print(approxes_names[l[0]], end=", ")
print()
k = k[0]

# Построение графиков
plt.plot(a, b, "bo")
r = np.linspace(min(a) - 5, max(a) + 5, 20)

for i in range(len(approxes)):
    if approxes_names[i] == "Логарифмическая":
        r = np.linspace(1, max(a) + 5, 20)
    p = list(map(lambda x: approxes[i][0](approxes[i][1](a, b), x), r))
    t = []
    for j in range(len(p)):
        t += [[r[j], p[j]]]
    plt.plot([u[0] for u in t], [u[1] for u in t], label=approxes_names[i])
plt.legend()
plt.show()
