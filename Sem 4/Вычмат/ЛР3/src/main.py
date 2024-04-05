import math
from Method import (
    Method,
    left_rectangles,
    right_rectangles,
    mid_rectangles,
    trapez,
    simpson
)


fs = [
    [lambda x: 3 * x ** 2 + 4, '3x^2 + 4'],
    [lambda x: 5 * math.sin(5 * x) + 3 * x, '5 * sin(5x) + 3x'],
    [lambda x: math.exp(x ** 3 + 8 * x), 'e^(x^3 + 8x)'],
    [lambda x: math.atan(x), 'arctg(x)']
]

methods = [
    Method("Левый метод прямоугольников", left_rectangles, 1),
    Method("Правый метод прямоугольников", right_rectangles, 1),
    Method("Средний метод прямоугольников", mid_rectangles, 2),
    Method("Метод трапеций", trapez, 2),
    Method("Метод Симпсона", simpson, 4)
]

def input_func():
    for i in range(len(fs)):
        print(f'{i + 1}. {fs[i][1]}')
    i = -1
    while i < 1 or i > len(fs):
        i = int(input('Выберите функцию -> '))
    return fs[i - 1][0]

def input_method():
    for i in range(len(methods)):
        print(f'{i + 1}. {methods[i].name}')
    i = -1
    while i < 1 or i > len(methods):
        i = int(input('Выберите метод -> '))
    return methods[i - 1]

def input_eps():
    eps = 0
    while eps <= 0:
        eps = float(input('Введите точность вычислений -> '))
    return eps

def input_ab():
    a = 0
    b = 0
    while a >= b:
        a = float(input('Введите левую границу -> '))
        b = float(input('Введите правую границу -> '))
    return a, b

f = input_func()
method = input_method()
a, b = input_ab()
n = 2
eps = input_eps()

res, err, n = method.calculate(f, a, b, n, eps)
print(f'Ответ: {res}\nПогрешность: {err}\nКоличество разбиения: {n}')
res_lib = method.calculate_lib(f, a, b, eps)
print(f'Ответ библиотеки: {res_lib}')
