from scipy import integrate

class Method:
    def __init__(self, name, calc_function, k):
        self.calc_function = calc_function
        self.name = name
        self.k = k

    def runge(self, ih, ih2):
        return (ih2 - ih) / (2 ** self.k - 1)

    def calculate(self, f, a, b, n, eps):
        f_calc = self.calc_function
        i, err = 0, 2 ** 31 - 1
        while abs(err) >= eps:
            n *= 2
            i_next = f_calc(f, a, b, n)
            err = self.runge(i, i_next)
            i = i_next
        return i, err, n
    
    def calculate_lib(self, f, a, b, eps):
        return integrate.quad(f, a, b, epsabs=eps)


def values(f, a, b, n):
    h = (b - a) / n
    y = [f(a + h * i) for i in range(0, n + 1)]
    return y

def left_rectangles(f, a, b, n):
    h = (b - a) / n
    y = values(f, a, b, n)
    return sum(y[:-1]) * h

def right_rectangles(f, a, b, n):
    h = (b - a) / n
    y = values(f, a, b, n)
    return sum(y[1:]) * h

def mid_rectangles(f, a, b, n):
    h = (b - a) / n
    y = values(f, a + h / 2, b + h / 2, n)
    return sum(y[:-1]) * h

def trapez(f, a, b, n):
    h = (b - a) / n
    y = values(f, a, b, n)
    y[0] /= 2
    y[-1] /= 2
    return sum(y) * h

def simpson(f, a, b, n):
    h = (b - a) / n
    y = values(f, a, b, n)
    s = 0
    for i in range(len(y)):
        if i == 0 or i == len(y) - 1:
            s += y[i]
        elif i % 2 == 0:
            s += 2 * y[i]
        else:
            s += 4 * y[i]
    return h * s / 3
