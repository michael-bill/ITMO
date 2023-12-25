import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv(
    "trades_18_for_diagram.csv",
    delimiter=";",
    encoding='utf-8')
data.boxplot()
plt.xticks(rotation=90)
plt.show()