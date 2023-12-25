from convertors.yaml_json import convert_yaml_to_python_map
from convertors.yaml_json import convert_python_map_to_json
from convertors.yaml_json import get_spaces_count
import pandas as pd


def convert(yaml_file_path, csv_file_path):
    """Конвертация данных из yaml файла в csv файл.
    ВНИМАЕНИЕ: Возможно возникновение исключений
    из-за неоднозначного соответсвия в представлении данных yaml и csv

    Args:
        yaml_file_path (str): Путь до yaml документа.
        csv_file_path (str): Путь для сохранения csv документа.
    """
    yaml_file = open(yaml_file_path, "r")
    lines = yaml_file.readlines()
    yaml_file.close()
    dict = convert_yaml_to_python_map(get_spaces_count(lines[0]), lines, 0)
    json_text = convert_python_map_to_json(dict)
    json_object = pd.read_json(json_text)
    csv_text = json_object.to_csv()
    csv_file = open(csv_file_path, 'w')
    csv_file.write(csv_text)
    csv_file.close()