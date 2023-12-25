# 1. Задание. Мой смайлик: 8<(
# 2. Задание. Вариант 3.
# 3. Задание. Вариант 1.

import re

def first_task(line):
    res = len(re.findall(r"8<\(", line))
    return res

def second_task(line):
    names = re.findall(r"\b[A-Я][а-я-]+\b\s\b[A-Я]\.[A-Я]\.", line)
    res = ""
    for i in range(len(names)):
        if i > 0: res += "\n" + names[i].split()[0]
        else: res += names[i].split()[0]
    names = sorted(names)
    return res

def third_task(line):
    with_matches = re.findall(r"\b\w*[ауоыэяюёие]{2}\w*\b", line, re.IGNORECASE)
    words = re.findall(r"\b[А-я]+\b", line)
    for i in words:
        for j in with_matches:
            if i == j:
                words.remove(j)
    words = sorted(words, key=len)
    res = ""
    for i in range(len(words)):
        if i > 0: res += "\n" + words[i]
        else: res += words[i]
    return res

def machine_number_task(line):
    res = re.fullmatch(r"[АВЕКМНОРСТУХ]{1}\d{3}[АВЕКМНОРСТУХ]{2}\d{1,3}", line)
    return True if res else False
