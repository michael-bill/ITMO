# for i in range(32):
#     sep = ','
#     args = bin(i)[2:]
#     args = '0' * (5 - len(args)) + args
#     x1 = int(args[0])
#     x2 = int(args[1])
#     x3 = int(args[2])
#     x4 = int(args[3])
#     x5 = int(args[4])
#     line = str(i) + sep
#     line += ' '.join(args) + sep
#     line += str(x1) + ' ' + str(x2) + sep
#     f1_10 = int(str(x1) + str(x2), 2)
#     line += str(f1_10) + sep
#     line += str(x3) + ' ' + str(x4) + ' ' + str(x5) + sep
#     f2_10 = int(str(x3) + str(x4) + str(x5), 2)
#     line += str(f2_10) + sep
#     f_dif = f1_10 - f2_10
#     line += str(f_dif) + sep
#     if f_dif == -3:
#         line += 'd' + sep
#     elif -2 <= f_dif <= 1:
#         line += '1' + sep
#     else:
#         line += '0' + sep
#     print(line)

# line = ''
# for i in range(32):
#     sep = '\t\t'
#     args = bin(i)[2:]
#     args = '0' * (5 - len(args)) + args
#     x1 = int(args[0])
#     x2 = int(args[1])
#     x3 = int(args[2])
#     x4 = int(args[3])
#     x5 = int(args[4])
#     hex_arg = hex(i)[2:].upper()
#     f1_10 = int(str(x4) + str(x5), 2)
#     f2_10 = int(str(x1) + str(x2) + str(x3), 2)
#     f_dif = f1_10 - f2_10
#     if f_dif == -5:
#         line += '(' + hex_arg + ')v'
#     elif -2 <= f_dif < 1:
#         line += hex_arg + 'v'
# print(line)

# x1 = 0

# for a in ['00', '01', '11', '10']:
#     for b in ['00', '01', '11', '10']:
#         x2 = int(a[0])
#         x3 = int(a[1])

#         x4 = int(b[0])
#         x5 = int(b[1])

#         f1_10 = int(str(x4) + str(x5), 2)
#         f2_10 = int(str(x1) + str(x2) + str(x3), 2)
#         f_dif = f1_10 - f2_10
#         if f_dif == -5:
#             print('d', end='\t')
#         elif -2 <= f_dif < 1:
#             print('1', end='\t')
#         else:
#             print('0', end='\t')
#     print()
# print()

# x1 = 1

# for a in ['00', '01', '11', '10']:
#     for b in ['00', '01', '11', '10']:
#         x2 = int(a[0])
#         x3 = int(a[1])

#         x4 = int(b[0])
#         x5 = int(b[1])

#         f1_10 = int(str(x4) + str(x5), 2)
#         f2_10 = int(str(x1) + str(x2) + str(x3), 2)
#         f_dif = f1_10 - f2_10
#         if f_dif == -5:
#             print('d', end='\t')
#         elif -2 <= f_dif < 1:
#             print('1', end='\t')
#         else:
#             print('0', end='\t')
#     print()

for i in range(32):
    sep = ','
    args = bin(i)[2:]
    args = '0' * (5 - len(args)) + args
    a1 = int(args[0])
    a2 = int(args[1])
    a3 = int(args[2])
    b1 = int(args[3])
    b2 = int(args[4])
    line = str(i) + sep
    line += sep.join(args)
    A = int(str(a1) + str(a2) + str(a3), 2)
    B = int(str(b1) + str(b2), 2)
    if A == 0 or B == 0: C = 'd'
    else: C = A * B
    if C == 'd':
        line += sep + sep.join('ddddd') + sep
    else:
        line += sep + sep.join('0' * (5 - len(bin(C)[2:])) + bin(C)[2:]) + sep
    line += str(A) + sep
    line += str(B) + sep
    line += str(C)
    print(line)