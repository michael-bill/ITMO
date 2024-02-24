from numpy import float64, linalg
from colorama import init as init_colorama, Fore
init_colorama()

# Проверка на нули в диагональных элементах
def is_zero(matrix):
    n = len(matrix)
    for i in range(n):
        if matrix[i][i] == 0:
            return True
    return False

# Метод диагонального преобладания
def diagonal_pivot(A, b):
    n = len(A)
    for i in range(n):
        max_row = i
        for j in range(i + 1, n):
            if abs(A[j][i]) > abs(A[max_row][i]):
                max_row = j
        A[i], A[max_row] = A[max_row], A[i]
        b[i], b[max_row] = b[max_row], b[i]

# Проверка на диагональное преобладание
def is_diagonal_pivot(A):
    n = len(A)
    for i in range(n):
        row_sum = sum(abs(A[i][j]) for j in range(n) if j != i)
        if abs(A[i][i]) <= row_sum:
            return False
    return True

# Определитель матрицы
def determinant(matrix):
    n = len(matrix)
    if n == 1:
        return matrix[0][0]
    elif n == 2:
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    else:
        det = 0
        for j in range(n):
            minor = [row[:j] + row[j + 1:] for row in matrix[1:]]
            det += (-1) ** j * matrix[0][j] * determinant(minor)
        return det

# Векторное произведение
def dot_product(v1, v2):
    return sum(x * y for x, y in zip(v1, v2))

def zero_vector(n):
    return [float64(0)] * n

def simple_iteration(A, b, tol=0.01, max_iter=1000):
    n = len(b)
    x = zero_vector(n)
    for itr in range(max_iter):
        x_new = zero_vector(n)
        # Вычисление следующего приближения
        for i in range(n):
            x_new[i] = (b[i] - dot_product(A[i][:i], x[:i]) - \
                       dot_product(A[i][i + 1:], x[i + 1:])) / A[i][i]
        # Критерий окончания итерационного процесса
        if max(abs(x_new[i] - x[i]) for i in range(n)) < tol:
            for j in range(n):
                print(f"Вектор погрешностей для x{j}: {abs(x_new[j] - x[j])}")
            return x_new
        x = x_new
        print("Итерация ", itr + 1, ": ", x)
    raise ValueError("Решение не сошлось после максимального числа итераций")

# Вычисление невязки
def residual(A, b, x):
    res = zero_vector(len(b))
    for i in range(len(b)):
        res[i] = b[i] - dot_product(A[i], x)
    return res

try:
    n = int(input('Введите размерность матрицы -> '))
    A = [list(map(float64, input(f'Введите ряд {i + 1} -> ').split())) for i in range(n)]
    if determinant(A) == 0:
        print('Матрица вырождена, метод непременим.')
        exit()
    b = list(map(float64, input('Вектор b -> ').split()))
    if not is_diagonal_pivot(A):
        print('Матрица не соответствует диагональному преобладанию. Попытка преобразования:')
        diagonal_pivot(A, b)
        if is_zero(A):
            print('После преобразования остались нули на диагноанльных элементах. Метод непременим.')
            exit()
        for row in A:
            print(row)
    else:
        print('Диагональное преобладание согласовано.')
    tol = float64(input('Погрешность -> '))
    max_iter = int(input('Максимальное число итераций -> '))
    print()
except Exception:
    print('Неверные входные данные')
    exit()

try:
    solution = simple_iteration(A, b, tol, max_iter)
except ValueError:
    print(Fore.RED + '\nРешение не сошлось после максимального числа итераций')
    exit()

print(Fore.GREEN + "\nРешение:", solution)
print("Определитель:", determinant(A))
print(Fore.RED + "Невязка: ", residual(A, b, solution))
print(Fore.BLUE + "Решение при помощи numpy:", linalg.solve(A, b))
print("Определитель при помощи numpy:", linalg.det(A), end="\n\n")