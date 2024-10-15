import numpy as np
import pandas as pd


def f1(x):
    return 5 * x ** 3 - 2 * x ** 2 + 3 * x - 17


def f2(x):
    return np.sin(x) + np.cos(x)


def f3(x):
    return 2 * np.log(x + 1)


def f4(x, y):
    return x + 2 * y


def f5(x):
    return np.sin(x / 2) + 2 * np.cos(x)


def f6(x, y):
    return x ** 2 + 3 * x * y - 7 * y + 1


domains = {
    "f1": [[-10, 10], [0, 100], [-1, 1], [-1000, 1000]],
    "f2": [[-3.14, 3.14], [0, 7], [0, 100], [-100, 100]],
    "f3": [[0, 4], [0, 9], [0, 99], [0, 999]],
    "f4": [[0, 1], [-10, 10], [0, 100], [-1000, 1000]],
    "f5": [[-3.14, 3.14], [0, 7], [0, 100], [-100, 100]],
    "f6": [[-10, 10], [0, 100], [-1, 1], [-1000, 1000]],
}


def generate_data_1d(f, domain, num_points=100):
    x = np.linspace(domain[0], domain[1], num_points)
    y = f(x)
    return pd.DataFrame({"x": x, "y": y})


def generate_data_2d(f, domain, num_points=100):
    x = np.linspace(domain[0], domain[1], num_points)
    y = np.linspace(domain[0], domain[1], num_points)
    X, Y = np.meshgrid(x, y)
    Z = f(X, Y)
    return pd.DataFrame({"x": X.flatten(), "y": Y.flatten(), "z": Z.flatten()})


def write_to_dat_file_with_header(filename, data, varnumber, minrandom, maxrandom, fitnesscases):
    with open(filename, 'w') as f:
        header = f"{varnumber} 0 {minrandom} {maxrandom} {fitnesscases}\n"
        f.write(header)

        for i in range(len(data)):
            line = " ".join(map(str, data[i]))
            f.write(line + "\n")


def prepare_dataset_for_tinygp_with_header():
    for i, domain in enumerate(domains['f1']):
        data = generate_data_1d(f1, domain).values
        fitnesscases = len(data)
        varnumber = 1
        minrandom = domain[0]
        maxrandom = domain[1]
        write_to_dat_file_with_header(f'f1_domain_{i + 1}.dat', data, varnumber, minrandom, maxrandom, fitnesscases)

    for i, domain in enumerate(domains['f2']):
        data = generate_data_1d(f2, domain).values
        fitnesscases = len(data)
        varnumber = 1
        minrandom = domain[0]
        maxrandom = domain[1]
        write_to_dat_file_with_header(f'f2_domain_{i + 1}.dat', data, varnumber, minrandom, maxrandom, fitnesscases)


prepare_dataset_for_tinygp_with_header()
