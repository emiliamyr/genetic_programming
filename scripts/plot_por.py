import json
import numpy as np
import os
import matplotlib.pyplot as plt
import sympy as sp

# Funkcja referencyjna (przykładowa funkcja jednej zmiennej)
def reference_function(x):
    return np.sin(x / 2) + 2 * np.cos(x)

# Wczytanie najlepszego osobnika z pliku JSON
def load_best_individual_expr(file_path):
    with open(file_path, "r") as file:
        data = json.load(file)
    best_individual_expr = data["simplified"]
    print(best_individual_expr)
    print(" funkcje ")
    return best_individual_expr

# Funkcja do bezpiecznej ewaluacji dla funkcji jednej zmiennej
def safe_eval_1d(expr, x_sym, x_val):
    try:
        value = expr.subs(x_sym, x_val).evalf()
        if value.is_real:
            return float(value)
        else:
            return 0.0
    except Exception as e:
        print(f"Error evaluating expression at x = {x_val}: {e}")
        return 0.0  # Zwraca 0.0 w przypadku błędu

# Główna funkcja do porównania
def compare_functions_1d(folder1, folder2, output_folder="output_plots2", file_name="comparison_f5_1d.png"):
    x = sp.symbols('x')

    # Ścieżki do plików JSON
    file1_path = os.path.join(folder1, "f5_d1_s.json")  # Zmień nazwę pliku na odpowiedni plik z folderu
    file2_path = os.path.join(folder2, "f5_d1_s.json")  # Zmień nazwę pliku na odpowiedni plik z folderu

    # Wczytaj wyrażenia z obu plików
    expr1_str = load_best_individual_expr(file1_path)
    expr2_str = load_best_individual_expr(file2_path)

    # Parsowanie wyrażeń
    expr1 = sp.sympify(expr1_str.replace("x1", "x"))
    expr2 = sp.sympify(expr2_str.replace("x1", "x"))

    # Zdefiniuj zakres X
    X_values = np.linspace(-10, 10, 100)

    # Ocen funkcję referencyjną
    Y_values_ref = [reference_function(val) for val in X_values]

    # Ocen najlepszy osobnik z `simp` i `simp2`
    Y_values_best1 = [safe_eval_1d(expr1, x, val) for val in X_values]
    Y_values_best2 = [safe_eval_1d(expr2, x, val) for val in X_values]

    # Inicjalizuj wykres
    plt.figure(figsize=(12, 8))

    # Rysuj funkcję referencyjną
    plt.plot(X_values, Y_values_ref, label="Reference Function", color="blue")

    # Rysuj najlepszy osobnik z `simp`
    plt.plot(X_values, Y_values_best1, label="Best Individual - simp", color="red", linestyle='--')

    # Rysuj najlepszy osobnik z `simp2`
    plt.plot(X_values, Y_values_best2, label="Best Individual - simp2", color="green", linestyle='--')

    # Ustawienia wykresu
    plt.title("Comparison of Last Generation's Best Individuals with Reference Function")
    plt.xlabel("x")
    plt.ylabel("f(x)")
    plt.legend()

    # Upewnij się, że katalog istnieje
    os.makedirs(output_folder, exist_ok=True)

    # Zapisz wykres
    full_path = os.path.join(output_folder, file_name)
    plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')

    # Wyświetl wykres
    plt.show()

    print(f"Plot saved at: {full_path}")

# Wywołanie funkcji z folderami `simp` i `simp2`
compare_functions_1d('../simp', '../simp2')
