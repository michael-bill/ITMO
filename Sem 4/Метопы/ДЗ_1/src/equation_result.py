from tabulate import tabulate
from colorama import init as init_colorama, Fore
import inspect

init_colorama()

class Result:
    def __init__(self, root=None, iterations=None, method_name=None, equation=None, table=None, success=True, message=None) -> None:
        self.root = root
        self.iterations = iterations
        self.method_name = method_name
        self.success = success
        self.message = message
        self.equation = equation
        self.table = table
        self.decimal_places = 3

    def __str__(self) -> str:
        a = round(self.equation.a, self.decimal_places)
        b = round(self.equation.b, self.decimal_places)
        eps = round(self.equation.eps, self.decimal_places)
        if not self.success:
            return \
            f"{Fore.BLUE}Метод{Fore.RESET}: {self.method_name}\n" + \
            f"{Fore.RED}Не удалось найти корень{Fore.RESET}: {self.message}\n" + \
            f"{Fore.BLUE}Границы и точность{Fore.RESET}: a={a}, b={b}, eps={eps}\n" + \
            f"{Fore.BLUE}Уравнение{Fore.RESET}: {inspect.getsource(self.equation.f)}"
        return \
            f"{Fore.GREEN}Метод{Fore.RESET}: {self.method_name}\n" + \
            f"{Fore.BLUE}Корень{Fore.RESET}: {self.root}\n" + \
            f"{Fore.BLUE}Количество итераций{Fore.RESET}: {self.iterations}\n" + \
            f"{Fore.BLUE}Границы и точность{Fore.RESET}: a={a}, b={b}, eps={eps}\n" + \
            f"{Fore.BLUE}Уравнение{Fore.RESET}: {inspect.getsource(self.equation.f)}\n\n" + \
            f"Таблица:\n{tabulate(self.table, headers='firstrow', tablefmt='grid')}"
