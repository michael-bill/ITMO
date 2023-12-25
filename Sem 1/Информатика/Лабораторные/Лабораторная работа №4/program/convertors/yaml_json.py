def convert_simplest_values(value):
    """Конвертирует простейшие значения из формата yaml в формат типов данных Python.

    Args:
        value (str): Значение из yaml строки.

    Returns:
        (bool, int, float, list, dict, str): Примитивный тип данных python.
    """
    if value == "true":
        return True
    if value == "false":
        return False
    if value.isdigit():
        return int(value)
    try:
        return float(value)
    except:
        if len(value) > 0 and (
            (value[0] == "[" and value[-1] == "]")
            or (value[0] == "{" and value[-1] == "}")
        ):
            return eval(value)
    if len(value) > 0:
        if value[0] == value[-1] == "'" or value[0] == value[-1] == '"':
            return value[1:-1]
    return value.replace("\n", "").strip()

def get_spaces_count(line):
    """Возвращает количество пробелов слева от строки с данными.

    Args:
        line (str): строка для подсчета.

    Returns:
        int: Количество пробелов.
    """
    return len(line) - len(line.lstrip())

def convert_yaml_to_python_map(spaces_count, lines, start):
    """Рекурсивная функция конвертации всего yaml документа в словарь типа данных python

    Args:
        spaces_count (int): Количество пробелов блока с которого мы начинаем.
        lines (list): List[str] из yaml строк для конвертации
        start (int): Строка, с которой мы начинаем работать
    
    Returns:
        dict: Словарь из всех значений документа в формате Python.
    """
    i = start
    result = {}
    while True:
        try:
            # Игнорируем комментарии
            data = lines[i].lstrip().split("#")[0]
            # Количество пробелов на данной строке
            line_spaces_count = get_spaces_count(lines[i])
            # Если строка пустая или мы вышли за границу блока
            if line_spaces_count > spaces_count or len(data.strip()) == 0:
                i += 1
                if i >= len(lines):
                    break
                continue
            if line_spaces_count < spaces_count:
                break
            # Если блок является элементом списка
            if data[0] == "-":
                fieldValue = data[1:].strip()
                if not isinstance(result, list):
                    result = []
                content = convert_simplest_values(fieldValue)
                if len(fieldValue.strip()) == 0:
                    content = convert_yaml_to_python_map(
                        get_spaces_count(lines[i + 1]), lines, i + 1
                    )
                # Если блок является элементом списка, но черточка (-) на уровне первой строки блока
                if ":" in fieldValue:
                    fieldName = fieldValue.split(":")[0]
                    fieldValue = ":".join(fieldValue.split(":")[1:]).strip()
                    content = {fieldName: fieldValue}
                    content.update(
                        convert_yaml_to_python_map(
                            get_spaces_count(lines[i + 1]), lines, i + 1
                        )
                    )
                result.append(content)
            # Если блок является словарем
            elif ":" in data:
                content = 0
                fieldName = data.split(":")[0]
                fieldValue = ":".join(data.split(":")[1:]).strip()
                if not isinstance(result, dict):
                    result = {}
                if len(fieldValue.strip()) == 0:
                    content = convert_yaml_to_python_map(
                        get_spaces_count(lines[i + 1]), lines, i + 1
                    )
                else: content = convert_simplest_values(fieldValue.strip())
                result[fieldName] = content
            else:
                raise Exception()
            i += 1
            if i >= len(lines):
                break
        except:
            print("Error at line: ", i + 1)
            print("Contents of line:", lines[i])
            exit(0)
    return result


def convert_python_map_to_json(data):
    """Конвертирует словарь данных python в json формат

    Args:
        data (dict): Словарь данных Python.

    Returns:
        str: Текст документа в формате json.
    """
    data = str(data)
    data = data.replace('"', '\\"')
    data = data.replace("'", '"')
    data = data.replace(" False", " false")
    data = data.replace(" True", " true")
    data = data.replace('\\\\"', '\\"')
    return data


def convert(yaml_file_path, json_file_path):
    """Конвертация данных из yaml файла в json файл.

    Args:
        yaml_file_path (str): Путь до yaml документа.
        json_file_path (str): Путь для сохранения json документа.
    """
    yaml_file = open(yaml_file_path, "r")
    lines = yaml_file.readlines()
    yaml_file.close()
    json_text = convert_python_map_to_json(
        convert_yaml_to_python_map(get_spaces_count(lines[0]), lines, 0)
    )
    json_file = open(json_file_path, "w")
    json_file.write(json_text)
    json_file.close()
