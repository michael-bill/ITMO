def insert_symbol(my_str, sybmol, index):
    return my_str[:index] + sybmol + my_str[index:]

def replace_by_index(my_str, symbol, index):
    return my_str[:index] + symbol + my_str[index + 1:]

# encode_shred Закодировать сообщение в код Хэмминга
# Принимает на вход строку в бинарном виде
# Возвращает двоичный код с подсчитанными контрольными битами
def encode_shred(binary_input):
    power = 0
    control_byte_index = 2 ** power - 1
    encoded = binary_input
    while control_byte_index < len(encoded):
        encoded = insert_symbol(encoded, '-', control_byte_index)
        power += 1
        control_byte_index = 2 ** power - 1
    power = -1
    for k in range(len(encoded)):
        if encoded[k] == '-':
            power += 1
            byte_value = 0
            cur_index = k
            while cur_index <= len(encoded):
                start = cur_index
                end = cur_index + (2 ** power)
                for i in range(start, end):
                    cur_index += 1
                    if i >= len(encoded):
                        break
                    if encoded[i] == '-':
                        continue
                    else:
                        byte_value += int(encoded[i])
                cur_index += (2 ** power)
            encoded = encoded.replace('-', str(byte_value % 2), 1)
    return encoded

# decode_shred Отобразить информационные биты
# Принимает на вход двоичный код Хэмминга
# Возвращает 3 переменных,
# 1. Исходное сообщение с исправленной ошибкой,
# 2. Сообщение с кодом Хэмминга,
# 3. Бит с ошибкой (если нет, то = 0)
def decode_shred(binary_input):
    power = 0
    control_bytes = []
    while (2 ** power - 1) < len(binary_input):
        byte_value = 0
        cur_index = (2 ** power - 1)
        while cur_index <= len(binary_input):
            start = cur_index
            end = cur_index + (2 ** power)
            for i in range(start, end):
                cur_index += 1
                if i >= len(binary_input):
                    break
                if i + 1 == 2 ** power:
                    continue
                byte_value += int(binary_input[i])
            cur_index += (2 ** power)
        control_bytes.append(byte_value % 2)
        power += 1

    power = 0
    error_byte_n = 0
    i = 0
    while (2 ** power - 1) < len(binary_input):
        if int(binary_input[2 ** power - 1]) != control_bytes[i]:
            error_byte_n += 2 ** power
        i += 1
        power += 1
    ch_binary_input = binary_input
    if error_byte_n != 0:
        ch_binary_input = replace_by_index(binary_input, str(abs(
            int(binary_input[error_byte_n - 1]) - 1)), error_byte_n - 1)
    message = ''
    power = 0
    for i in range(len(ch_binary_input)):
        if i + 1 != 2 ** power:
            message += ch_binary_input[i]
        else:
            power += 1
    return message, binary_input, error_byte_n

d = ''
while not (d in ['1', '2', '3', '4']):
    d = input('''Введите число от 1 до 2
    1. Закодировать сообщение в код Хэмминга
    2. Раскодировать сообщение из кода Хэмминга
    -> ''')
    if d == '1':
        try:
            i = input('Введите двоичный код -> ')
            int(i, 2)
            print("Сообщение в коде Хэмминга:", encode_shred(i))
        except:
            print('Неверный формат ввода данных')
    elif d == '2':
        try:
            i = input('Введите двоичный код -> ')
            int(i, 2)
            o1, o2, o3 = decode_shred(i)
            print()
            print('Исходное сообщение с исправленной ошибкой:',o1)
            print('Сообщение с кодом Хэмминга:', o2)
            print('Бит с ошибкой (если нет, то = 0):', o3)
        except:
            print('Неверный формат ввода данных')