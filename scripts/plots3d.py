import json
import numpy as np
import os
import matplotlib.pyplot as plt
import sympy as sp


def f5(x, y):
    return x ** 2 + 3 * x * y - 7 * y + 1

# Wczytaj dane JSON z pliku
file_path = "..\\output2\\f6_domain_1.json"
with open(file_path, "r") as file:
    data = json.load(file)

# Wybierz dane ostatniej generacji
last_generation = data[-1]  # Dostęp do ostatniego elementu listy
best_individual_expr = last_generation["best_individual"]

# Drukuj, aby debugować i sprawdzić format wyrażenia
print("Best Individual Expression:", best_individual_expr)

# Zdefiniuj dziedziny X i Y
X_values = np.linspace(-10, 10, 50)
Y_values = np.linspace(-10, 10, 50)
X_grid, Y_grid = np.meshgrid(X_values, Y_values)

# Ocen funkcję referencyjną
Z_values_ref = f5(X_grid, Y_grid)

# Inicjalizuj wykres
fig = plt.figure(figsize=(12, 8))
ax = fig.add_subplot(111, projection='3d')

# Rysuj funkcję referencyjną
ref_surface = ax.plot_surface(X_grid, Y_grid, Z_values_ref, color='blue', alpha=0.5)
# Ustawienie legendy dla wykresu (funkcja referencyjna)
ref_surface.set_label('Reference Function (f6(x, y))')

# Tworzenie zmiennych symbolicznych
x, y = sp.symbols('x y')

# Zdefiniuj wyrażenie najlepszego osobnika symbolicznie
try:
    expr = sp.sympify(best_individual_expr.replace("X1", "x").replace("X2", "y"))
    print(f"Parsed Expression: {expr}")  # Dodajemy debugowanie
except Exception as e:
    print(f"Error parsing expression: {e}")
    expr = None  # Jeśli wystąpi błąd, ustaw wyrażenie na None

# Ocen najlepszy osobnik z ostatniej generacji
Z_values_best = np.zeros_like(X_grid)  # Inicjalizuj tablicę dla wyników najlepszego osobnika

# Funkcja do bezpiecznej ewaluacji
def safe_eval(expr, x_val, y_val):
    try:
        # Podstaw x i y do wyrażenia
        value = expr.subs({x: x_val, y: y_val})

        # Zastosowanie limity, jeśli funkcja jest nieokreślona (np. dzielenie przez zero)
        if value.is_real:
            return float(value)
        else:
            return 0.0  # Zwróć 0 dla wartości nie-rzeczywistych
    except ZeroDivisionError:
        # Obsługuje dzielenie przez zero
        print(f"ZeroDivisionError at x={x_val}, y={y_val}")
        return 0.0  # Zwróć wartość 0, jeśli wystąpi dzielenie przez zero
    except Exception as e:
        print(f"Error evaluating expression at x = {x_val}, y = {y_val}: {e}")
        return 0.0  # Zwróć 0 w przypadku innych błędów

# Jeśli wyrażenie jest poprawnie sparsowane, wykonaj obliczenia
if expr:
    Z_values_best = np.zeros_like(X_grid)  # Inicjalizuj tablicę dla wyników najlepszego osobnika

    for i in range(X_grid.shape[0]):
        for j in range(X_grid.shape[1]):
            x_val = X_grid[i, j]
            y_val = Y_grid[i, j]

            # Bezpieczna ewaluacja wyrażenia przy użyciu SymPy
            Z_values_best[i, j] = safe_eval(expr, x_val, y_val)

    # Rysuj oceniane wartości funkcji najlepszego osobnika
    best_surface = ax.plot_surface(X_grid, Y_grid, Z_values_best, color='red', alpha=0.5)
    # Ustawienie legendy dla wykresu (najlepszy osobnik)
    best_surface.set_label('Best Individual - Last Generation')
else:
    print("Expression parsing failed. Skipping the best individual plotting.")


# Jeśli wyrażenie jest poprawnie sparsowane, wykonaj obliczenia
if expr:
    for i in range(X_grid.shape[0]):
        for j in range(X_grid.shape[1]):
            x_val = X_grid[i, j]
            y_val = Y_grid[i, j]

            # Bezpieczna ewaluacja wyrażenia przy użyciu SymPy
            Z_values_best[i, j] = safe_eval(expr, x_val, y_val)

    # Rysuj oceniane wartości funkcji najlepszego osobnika
    best_surface = ax.plot_surface(X_grid, Y_grid, Z_values_best, color='red', alpha=0.5)
    # Ustawienie legendy dla wykresu (najlepszy osobnik)
    best_surface.set_label('Best Individual - Last Generation')

else:
    print("Expression parsing failed. Skipping the best individual plotting.")

# Dostosowanie wykresu
ax.set_title("Comparison of Last Generation's Best Individual with Reference Function")
ax.set_xlabel("X")
ax.set_ylabel("Y")
ax.set_zlabel("Function Value")

# Ustawienie legendy w 3D
# Aby dodać legendę w wykresie 3D, musimy utworzyć "proxy artists"
# Możemy to zrobić przez utworzenie pseudo-artystów z odpowiednimi etykietami

from matplotlib.lines import Line2D
legend_elements = [
    Line2D([0], [0], color='blue', lw=4, label='Reference Function (f6(x, y))'),
    Line2D([0], [0], color='red', lw=4, label='Best Individual - Last Generation')
]
ax.legend(handles=legend_elements, loc='upper right')

# Określenie katalogu i nazwy pliku do zapisania wykresu
directory = "..\\plotting2"
filename = "f6_comp_domain_1.png"
full_path = os.path.join(directory, filename)

# Upewnij się, że katalog istnieje
os.makedirs(directory, exist_ok=True)

# Zapisz wykres jako plik PNG
plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')

# Wyświetl wykres
plt.show()

print(f"Plot saved at: {full_path}")