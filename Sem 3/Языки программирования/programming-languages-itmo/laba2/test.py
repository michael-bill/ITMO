import subprocess

test_inputs = [
    "car",
    "dog",
    "house",
    "tree",
    "sun",
    "",
    "asdasd",
    # len = 256
    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
    # len = 255
    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
]
expected_outputs = [
    "A four-wheeled motor vehicle used for transportation",
    "A domesticated carnivorous mammal that typically has a long snout, an acute sense of smell, non-retractable claws, and a barking, howling, or whining voice",
    "A building for human habitation, especially one that is lived in by a family or small group of people",
    "A woody perennial plant, typically having a single stem or trunk growing to a considerable height and bearing lateral branches at some distance from the ground",
    "The star around which the earth orbits, providing light and heat",
    "",
    "",
    "",
    ""
]
expected_errors = [
    "",
    "",
    "",
    "",
    "",
    "Provided element is not found",
    "Provided element is not found",
    "Provided key is invalid",
    "Provided element is not found"
]
num_failures = 0

print("Starting tests")
print("-------------------")

for i in range(len(test_inputs)):
    process = subprocess.Popen(["./main"], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = process.communicate(input=test_inputs[i].encode())
    stdout = stdout.decode().strip()
    stderr = stderr.decode().strip()

    if stdout == expected_outputs[i] and stderr == expected_errors[i]:
        print("Test %s passed" % i)
    else:
        num_failures += 1
        print("Test %s failed" % i)
        print("Input: %s" % test_inputs[i])
        print("Awaited output: %s" % expected_outputs[i])
        print("Stdout: %s" % stdout.strip())
        print("Awaited error: %s" % expected_errors[i])
        print("Stderr: %s" % stderr.strip())
        print("Exit code: %s" % process.returncode)
    print("-------------------")

if num_failures == 0:
    print("All tests passed")
else:
    print("%d tests failed" % num_failures)