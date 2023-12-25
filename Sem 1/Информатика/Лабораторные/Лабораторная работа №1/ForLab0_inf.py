from math import factorial

def ns10_to_base_i(number, base):
    if number == 0:
        return '0'
    nums = "0123456789ABCDEF"
    res = ''
    while number > 0:
        res = nums[number % base] + res
        number //= base
    return res

def ns10_to_base_d(number, base, accuracy):
    nums = "0123456789ABCDEF"
    i_part = ns10_to_base_i(int(number), base)
    d_part_10 = number - int(number)
    d_part = ''
    for i in range(accuracy):
        digit = int((d_part_10 * base) // 1)
        d_part += nums[digit]
        d_part_10 = (d_part_10 * base) - int(d_part_10 * base)
    return i_part + '.' + d_part

def ns(number, base1, base2, accuracy):
    nums = "0123456789ABCDEF"
    i_part = number.split('.')[0]
    d_part = number.split('.')[1]
    res_10 = 0.0
    for i in range(len(i_part)):
        res_10 += nums.index(i_part[len(i_part) - i - 1]) * base1 ** i
    for i in range(len(d_part)):
        res_10 += nums.index(d_part[i]) * base1 ** (-i - 1)
    res = ns10_to_base_d(res_10, base2, accuracy)
    return res

def ns10_to_fact(number):
    res = 0
    i = 0
    while number > 0:
        res += (number % (i + 2)) * (10 ** i)
        number //= (i + 2)
        i += 1
    return res

def nsfact_to_ns10(number):
    res = 0
    number_str = str(number)[::-1]
    for i in range(len(number_str)):
        res += int(number_str[i]) * factorial(i + 1)
    return res

def nsberg_to_ns10(num):
    z = (1 + 5 ** 0.5) / 2
    num_i = ''
    num_d = ''
    if sorted(list(set(num))) == sorted(['0', '1', '.']) \
        or sorted(list(set(num))) == sorted(['0', '1']):
        if '.' in num:
            num_i = num.split('.')[0]
            num_d = num.split('.')[1]
        else:
            num_i = num
        res = 0
        for i in range(len(num_d)):
            if num_d[i] == '1':
                res += z ** (- (i + 1))
        for i in range(len(num_i[::-1])):
            if num_i[::-1][i] == '1':
                res += z ** i
        return res
    else:
        raise Exception("Неверный формат")

d = ''
while not (d in ['1', '2', '3', '4']):
    d = input('''Введите число от 1 до 4
    1. Перевод из другой СС в другую СС
    2. Перевод из 10 в факториальную
    3. Перевод из факториальной в 10
    4. Перевод из CC Бергмана в 10
    -> ''')
    if d == '1':
        try:
            number = input("Введите число (с точкой разделения знака, если оно дробное): ")
            base1 = int(input("Введите основание системы счисления числа: "))
            base2 = int(input("Введите основание, куда нужно перевести число: "))
            if '.' in number:
                accuracy = int(input("Введите точность знаков после запятой: "))
                print("Результат:", ns(number, base1, base2, accuracy))
            else:
                number = int(number, base1)
                print("Результат:", ns10_to_base_i(number, base2))
        except:
            print("Неверный формат ввода данных")
    elif d == '2':
        try:
            number = int(input("Введите целое десятичное число: "))
            print("Результат:", ns10_to_fact(number))
        except:
            print("Неверный формат ввода данных")
    elif d == '3':
        try:
            number = int(input("Введите целое число в факториалной системе: "))
            print("Результат:", nsfact_to_ns10(number))
        except:
            print()
    elif d == '4':
        try:
            number = input("Введите число в системе счисления Бергмана: ")
            print("Результат:", nsberg_to_ns10(number))
        except:
            print("Неверный формат ввода данных")