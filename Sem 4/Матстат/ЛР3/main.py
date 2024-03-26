def mx(ns):
    res = 0
    for i in range(len(ns)):
        res += ns[i]
    res /= len(ns)
    return res

def dx(ns, mx):
    res = 0
    for i in range(len(ns)):
        res += (ns[i] - mx) ** 2
    res /= (len(ns) - 1)
    return res

nsx = [9.14, 11.49, 10.19, 8.96, 9.00, 8.54, 10.80, 11.62, 6.69, 6.93, 7.89, 9.69, 7.81]
nsy = [7.76, 15.60, 8.04, 7.33, 9.86, 8.67, 8.49, 8.72, 8.90, 8.01, 6.73, 7.14, 10.51, 9.00, 11.29]

print("Объем выборки x: ", len(nsx))
print("Объем выборки y: ", len(nsy))

mxx = mx(nsx)
mxy = mx(nsy)
print("Матожидание x: ", mxx)
print("Матожидание y: ", mxy)

dxx = dx(nsx, mxx)
dxy = dx(nsy, mxy)
print("Дисперсия x: ", dx(nsx, mxx))
print("Дисперсия y: ", dx(nsy, mxy))

print("Критерий свободы: ", len(nsx) + len(nsy) - 2)

def calculate_T(mxx, mxy, dxx, dxy, nx, ny):
    numerator = mxy - mxx
    denominator = ((nx - 1) * dxx + (ny - 1) * dxy)**0.5
    correction_factor = (((nx * ny) * (nx + ny - 2)) / (nx + ny))**0.5
    return numerator / denominator * correction_factor

print("T: ", calculate_T(mxx, mxy, dxx, dxy, len(nsx), len(nsy)))
