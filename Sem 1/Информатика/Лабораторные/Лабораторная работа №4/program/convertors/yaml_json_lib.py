import yaml
import json

def convert(yaml_file_path, json_file_path):
    """Конвертация данных из yaml файла в json файл.

    Args:
        yaml_file_path (str): Путь до yaml документа.
        json_file_path (str): Путь для сохранения json документа.
    """
    yaml_file = open(yaml_file_path, 'r')
    configuration = yaml.safe_load(yaml_file)
    yaml_file.close()
    json_file = open(json_file_path, 'w')
    json.dump(configuration, json_file, indent=2, ensure_ascii=False)
    json_file.close()