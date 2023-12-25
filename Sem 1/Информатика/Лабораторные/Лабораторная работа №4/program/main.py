import convertors.yaml_json as yaml_json
import convertors.yaml_json_lib as yaml_json_lib
import convertors.yaml_json_reg as yaml_json_reg
import convertors.yaml_csv as yaml_csv
from datetime import datetime
from colorama import init as init_colorama, Fore, Style

# Конвертация без использования сторонних библиотек и регулярных выражений
try: yaml_json.convert('yaml_data/data.yaml', 'json_data/data_yaml_json.json')
except: print('Возникла ошибка при конвертации файла без использования сторонних библиотек и рег. выражений.')
# Конвертация c использованием сторонней библиотеки
try: yaml_json_lib.convert('yaml_data/data.yaml', 'json_data/data_yaml_json_lib.json')
except: print('Возникла ошибка при конвертации файла используя сторонюю библиотеку.')
# Конвертация без использования сторонних библиотек, но с регулярными выражениями
try: yaml_json_reg.convert('yaml_data/data.yaml', 'json_data/data_yaml_json_reg.json')
except: print('Возникла ошибка при конвертации файла с использованием регулярных выражений.')
# Конвертация из yaml в csv
try: yaml_csv.convert('yaml_data/data_for_csv.yaml', 'csv_data/data_yaml_csv.csv')
except: print('Данный yaml файл не подходит для конвертации в csv.')


def calculate_execution_time(base_file, end_file, function):
    """Замерка времени 100 кратного исполнения функции конвертации.

    Args:
        base_file (str): Путь до документа для конвертации.
        end_file (str): Путь до конвертированного документа.
        function (function): Функция конвертации.

    Returns:
        datetime: Время исполнения функции конвертации.
    """
    start_time = datetime.now()
    for i in range(100):
        try: function(base_file, end_file)
        except: print('Возинкла ошибка при конвертации файла ' + base_file)
    end_time = datetime.now()
    return end_time - start_time

init_colorama()
print('Формат времени - Часы:Минуты:Секунды:Микросекунды (10^-6 от секунды)')
print(Style.RESET_ALL + 'Время 100 кратного исполнения конвертации без библиотек:', Fore.GREEN + 
    str(calculate_execution_time('yaml_data/data.yaml', 'json_data/data_yaml_json.json', yaml_json.convert)))
print(Style.RESET_ALL + 'Время 100 кратного исполнения конвертации с библиотеками:',  Fore.GREEN + 
    str(calculate_execution_time('yaml_data/data.yaml', 'json_data/data_yaml_json_lib.json', yaml_json_lib.convert)))
print(Style.RESET_ALL + 'Время 100 кратного исполнения конвертации с регулярными выражениями:',  Fore.GREEN + 
    str(calculate_execution_time('yaml_data/data.yaml', 'json_data/data_yaml_json_reg.json', yaml_json_reg.convert)))

# Ну и для теста конвертация другого файла
yaml_json.convert('yaml_data/test.yaml', 'json_data/test.json')