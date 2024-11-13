import numpy as np
import os
import json
import sympy as sp
import numpy as np
import matplotlib.pyplot as plt
import re

# Funkcja referencyjna
def f5(x):
    return 2 * np.log(x + 1)

# Wczytanie danych z JSON
file_path = "..\\output\\f3_domain_4.json"
with open(file_path, "r") as file:
    data = json.load(file)

# Pobranie ostatniego pokolenia
last_generation = data[-1]
best_individual_expr = last_generation["best_individual"]

# Funkcja do uproszczenia wyrażenia i kontroli dzielenia
def preprocess_expression(expr):
    expr = re.sub(r'\(\s*([^()]+)\s*\)', r'\1', expr)  # Usunięcie nadmiaru nawiasów wokół prostych elementów
    expr = re.sub(r'\s+', '', expr)  # Usunięcie zbędnych białych znaków
    return expr

def safe_eval_expr(expr, x_value, tolerance=0.001):
    x = sp.symbols('x')
    parsed_expr = sp.sympify(expr)
    try:
        # Podstawienie wartości
        simplified_expr = sp.simplify(parsed_expr.subs(x, x_value))

        # Rozdzielenie wyrażenia na licznik i mianownik
        num, den = simplified_expr.as_numer_denom()
        num_val = num.evalf(subs={x: x_value})
        den_val = den.evalf(subs={x: x_value})

        # Kontrola dzielenia, aby uniknąć błędów przy bliskich zero mianownikach
        if abs(den_val) < tolerance:
            return float(num_val)  # Zwraca licznik, gdy mianownik jest bliski zera
        else:
            return float(num_val / den_val)  # Zwraca wynik dzielenia, jeśli mianownik jest wystarczająco duży

    except Exception as e:
        print(f"Błąd przy obliczaniu dla x={x_value}: {e}")
        return np.nan

# Przetwarzanie wyrażenia i ewaluacja
best_individual_expr = preprocess_expression(best_individual_expr)

X_values = np.linspace(0, 999, 100)
y_values = []

for x_val in X_values:
    y = safe_eval_expr(best_individual_expr, x_val)
    y_values.append(y)

# Generowanie wykresu
y_values = np.array(y_values)
plt.figure(figsize=(12, 8))
plt.plot(X_values, f5(X_values), label='Funkcja odniesienia f5(x)', color='blue', linewidth=2)
plt.plot(X_values, y_values, label='TinyGP', linestyle='--', color='red')
plt.xlabel("X")
plt.ylabel("Wartość funkcji")
plt.legend()
plt.grid(True)
plt.show()

# Zapis wykresu
directory = "..\\plotting"
filename = "f3_comp_domain_4.png"
full_path = os.path.join(directory, filename)
os.makedirs(directory, exist_ok=True)
plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')
plt.show()

print(f"Wykres zapisano w: {full_path}")
