import json
import numpy as np
import os
import matplotlib.pyplot as plt
import sympy as sp

from scripts.plots3d import best_individual_expr


def f5(x):
    return np.sin(x) + np.cos(x)

# Wczytaj dane JSON z pliku
file_path = "..\\simp2\\f2_d2_s.json"
with open(file_path, "r") as file:
    data = json.load(file)

# Wybierz dane ostatniej generacji
#last_generation = data[-1]
#best_individual_expr = last_generation["best_individual"]
best_individual_expr = data["simplified"]
# Drukuj, aby debugować i sprawdzić format wyrażenia
print("Best Individual Expression:", best_individual_expr)

# Zdefiniuj dziedzinę X
X_values = np.linspace(-3.14, 3.14, 100)

# Ocen funkcję referencyjną
Y_values_ref = f5(X_values)

# Inicjalizuj wykres
fig, ax = plt.subplots(figsize=(10, 6))

# Rysuj funkcję referencyjną
ax.plot(X_values, Y_values_ref, color='blue', label='Reference Function (f2(x))')

# Tworzenie zmiennej symbolicznej
x = sp.symbols('x')

# Zdefiniuj wyrażenie najlepszego osobnika symbolicznie
try:
    expr = sp.sympify(best_individual_expr.replace("X1", "x"))
    print(f"Parsed Expression: {expr}")  # Dodajemy debugowanie
except Exception as e:
    print(f"Error parsing expression: {e}")
    expr = None  # Jeśli wystąpi błąd, ustaw wyrażenie na None

# Funkcja do bezpiecznej ewaluacji
def safe_eval(expr, x_val):
    try:
        # Podstaw x do wyrażenia
        value = expr.subs(x, x_val)
        if value.is_real:
            return float(value)
        else:
            return 0.0  # Zwróć 0 dla wartości nie-rzeczywistych
    except ZeroDivisionError:
        print(f"ZeroDivisionError at x={x_val}")
        return 0.0
    except Exception as e:
        print(f"Error evaluating expression at x = {x_val}: {e}")
        return 0.0

# Jeśli wyrażenie jest poprawnie sparsowane, wykonaj obliczenia
if expr:
    Y_values_best = np.array([safe_eval(expr, x_val) for x_val in X_values])

    # Rysuj oceniane wartości funkcji najlepszego osobnika
    ax.plot(X_values, Y_values_best, color='red', linestyle='--', label='Best Individual - Last Generation')
else:
    print("Expression parsing failed. Skipping the best individual plotting.")

# Dostosowanie wykresu
ax.set_title("Comparison of Last Generation's Best Individual with Reference Function")
ax.set_xlabel("X")
ax.set_ylabel("Function Value")
ax.legend()

# Określenie katalogu i nazwy pliku do zapisania wykresu
directory = "..\\plotting2"
filename = "f2_comp_domain_2.png"
full_path = os.path.join(directory, filename)

# Upewnij się, że katalog istnieje
os.makedirs(directory, exist_ok=True)

# Zapisz wykres jako plik PNG
plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')

# Wyświetl wykres
plt.show()

print(f"Plot saved at: {full_path}")
