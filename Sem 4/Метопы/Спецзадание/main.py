from NeuralNetwork import NeuralNetwork

# Инициализируем нейронную сеть
network = NeuralNetwork()
# Обучаем нейронную сеть
network.train_model()

# Предсказываем результат
print(network.predict([1, 0, 0]))
