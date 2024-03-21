data = [-1.223, 0.303, -0.730, -1.143, -1.413,
        -0.155, -0.098, 0.997, 0.434, -0.225]

"""Вариационный ряд:"""

print(*sorted(data))

"""Наименьшее и наибольшое значение:"""

print("Максимум:", max(data))
print("Минимум:", min(data))

"""Число интервалов:"""

import math

k = int(math.ceil(1 + 3.32*math.log2(len(data))))
print("Число интервалов:", k)

left_border = math.floor(min(data))
right_border = math.ceil(max(data))
h = round((right_border-left_border)/k, 2)

print("Левая граница:", left_border)
print("Правая граница:", right_border)
print("Шаг:", h)

import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
from IPython.display import display, HTML

sns.set(style="whitegrid", palette="pastel")
def show_graph(title, x_label, y_label):
    plt.title(title)
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.grid(True)
    
    plt.show()
def graph(data, interval_count):
    # Гистограмма
    hist, bins = np.histogram(sorted(data), bins=interval_count)
    plt.figure(figsize=(10, 6))
    sns.histplot(sorted(data), bins=interval_count, kde=False, color='orangered', edgecolor='orange')
    # Таблица
    print("Интервальный ряд")
    Fnx = 0
    Fnx_values = [0]
    Fnx_labels = [0]
    df = pd.DataFrame(columns=['Номер', 'Границы интервала', 'Середина интервала', 'Абсолютная частота', 'Относительная частота', 'Fn', 'Плотность относительной частоты'])
    # Для вывода плотнсти относительной частоты
    xy = [[], []]
    for i in range(len(bins)-1):
      column1 = i + 1 # Номер
      column2 = "["+str(round(bins[i],2))+", "+str(round(bins[i+1],2))+("]" if i == len(bins)-1 else ")") # Границы интервала группировки
      column3 = round((bins[i]+bins[i+1])/2, 2) # Середина интервала группировки
      xy[0].append(column3)
      column4 = hist[i] # Абсолютная частота
      column5 = round(hist[i] / len(data), 2) # Относительная частота
      Fnx += round(column5, 2)
      Fnx_values.append(Fnx)
      Fnx_labels.append(column3)
      column6 = round(column5 / (bins[i+1]-bins[i]), 2) # Плотность относительной частоты
      xy[1].append(column6)
      temp_df = pd.DataFrame({'Номер': [column1],
                            'Границы интервала': [column2],
                            'Середина интервала': [column3],
                            'Абсолютная частота': [column4],
                            'Относительная частота': [column5],
                            'Fn': [Fnx],
                            'Плотность относительной частоты': [column6]})
      df = pd.concat([df, temp_df], ignore_index=True)
    print(df)
    display(HTML(df.to_html(index=False)))
    show_graph('Гистограмма', 'Значение', 'Частота')
    plt.figure(figsize=(10, 6))
    plt.bar(xy[0], xy[1], width=0.2)
    show_graph('Гистограмма', 'Значение', 'Плотность относительной частоты')
  
    # График
    plt.figure(figsize=(10, 6))
    plt.plot(Fnx_values, marker='o', linestyle='-', color='b')
    plt.xticks(range(len(Fnx_labels)), Fnx_labels)
    show_graph('Fnx', 'Середина интервала', 'Значение')
graph(data, k)