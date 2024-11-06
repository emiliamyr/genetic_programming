import numpy as np
import os


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

def f7(x):
    return np.sin(x + 4.141592/2)

def f8(x):
    return np.tan(2*x + 1)


def save_data_to_file(data, filename, folder, nvar, nrand=10, minrand=-10.0, maxrand=10.0):
    os.makedirs(folder, exist_ok=True)
    filepath = os.path.join(folder, filename)
    nfitcases = len(data)

    with open(filepath, 'w') as file:
        file.write(f"{nvar} {nrand} {minrand} {maxrand} {nfitcases}\n")

        for row in data:
            file.write(" ".join(map(str, row)) + "\n")

    print(f"Data saved to: {filepath}")


def generate_data_for_different_domains():
    domains = {
        "f1": [
            np.linspace(-10, 10, 100),
            np.linspace(0, 100, 100),
            np.linspace(-1, 1, 100),
            np.linspace(-1000, 1000, 100),
        ],
        "f2": [
            np.linspace(-3.14, 3.14, 100),
            np.linspace(0, 7, 100),
            np.linspace(0, 100, 100),
            np.linspace(-100, 100, 100),
        ],
        "f3": [
            np.linspace(0, 4, 100),
            np.linspace(0, 9, 100),
            np.linspace(0, 99, 100),
            np.linspace(0, 999, 100),
        ],
        "f4": [
            (np.linspace(0, 1, 50), np.linspace(0, 1, 50)),
            (np.linspace(-10, 10, 50), np.linspace(-10, 10, 50)),
            (np.linspace(0, 100, 50), np.linspace(0, 100, 50)),
            (np.linspace(-1000, 1000, 50), np.linspace(-1000, 1000, 50)),
        ],
        "f5": [
            np.linspace(-3.14, 3.14, 100),
            np.linspace(0, 7, 100),
            np.linspace(0, 100, 100),
            np.linspace(-100, 100, 100),
        ],
        "f6": [
            (np.linspace(-10, 10, 50), np.linspace(-10, 10, 50)),
            (np.linspace(0, 100, 50), np.linspace(0, 100, 50)),
            (np.linspace(-1, 1, 50), np.linspace(-1, 1, 50)),
            (np.linspace(-1000, 1000, 50), np.linspace(-1000, 1000, 50)),
        ],
        "f7":[
            np.linspace(-3.14, 3.14, 100),
            np.linspace(0, 7, 100),
            np.linspace(0, 100, 100),
            np.linspace(-100, 100, 100),
        ],
        "f8":[
            np.linspace(-3.14, 3.14, 100),
            np.linspace(0, 7, 100),
            np.linspace(0, 100, 100),
            np.linspace(-100, 100, 100),
        ]
    }

    results = {}

    results['f1'] = [(domain, f1(domain)) for domain in domains['f1']]
    results['f2'] = [(domain, f2(domain)) for domain in domains['f2']]
    results['f3'] = [(domain, f3(domain)) for domain in domains['f3']]
    results['f5'] = [(domain, f5(domain)) for domain in domains['f5']]

    results['f4'] = [(np.meshgrid(domain[0], domain[1]), f4(*np.meshgrid(domain[0], domain[1]))) for domain in
                     domains['f4']]
    results['f6'] = [(np.meshgrid(domain[0], domain[1]), f6(*np.meshgrid(domain[0], domain[1]))) for domain in
                     domains['f6']]
    results['f7'] = [(domain, f7(domain)) for domain in domains['f7']]
    results['f8'] = [(domain, f8(domain)) for domain in domains['f8']]
    return results


def save_all_data_for_different_domains(data, folder="data"):
    for key, domains in data.items():
        for idx, domain_data in enumerate(domains):
            if key in ['f4', 'f6']:
                (X, Y), Z = domain_data
                data_to_save = np.column_stack((X.flatten(), Y.flatten(), Z.flatten()))
                nvar = 2
            else:
                X, Y = domain_data
                data_to_save = np.column_stack((X, Y))
                nvar = 1

            filename = f"{key}_domain_{idx + 1}.dat"
            save_data_to_file(data_to_save, filename, folder, nvar)


data_for_different_domains = generate_data_for_different_domains()

save_all_data_for_different_domains(data_for_different_domains, folder="data")
