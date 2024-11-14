import json
import numpy as np
import os
import matplotlib.pyplot as plt
import sympy as sp

def f5(x):
    return np.sin(x / 2) + 2 * np.cos(x)

# Wczytanie pliku JSON
file_path = "..\\output\\f5_domain_4.json"
with open(file_path, "r") as file:
    data = json.load(file)

last_generation = data[-1]
best_individual_expr = last_generation["best_individual"]

print("Best Individual Expression:", best_individual_expr)

# Dziedzina
X_values = np.linspace(-100, 100, 100)
Y_values_ref = f5(X_values)

fig, ax = plt.subplots(figsize=(10, 6))

# Wykres funkcji referencyjnej
ax.plot(X_values, Y_values_ref, color='blue', label='Reference Function (f5(x))')

x = sp.symbols('x')

try:
    expr = sp.sympify(best_individual_expr.replace("X1", "x"))
    print(f"Parsed Expression: {expr}")
except Exception as e:
    print(f"Error parsing expression: {e}")
    expr = None

# Funkcja pomocnicza do bezpiecznego obliczania wartości z obsługą dzielenia przez zero
def safe_eval(expr, x_val):
    try:
        numerator, denominator = sp.fraction(expr)
        den_val = denominator.subs(x, x_val)
        if den_val == 0:
            # Jeśli mamy dzielenie przez zero, zwróć tylko licznik
            num_val = numerator.subs(x, x_val)
            return float(num_val) if num_val.is_real else 0.0
        else:
            # Jeśli nie ma dzielenia przez zero, zwróć wartość całego wyrażenia
            value = expr.subs(x, x_val)
            return float(value) if value.is_real else 0.0
    except ZeroDivisionError:
        print(f"ZeroDivisionError at x={x_val}")
        return 0.0
    except Exception as e:
        print(f"Error evaluating expression at x = {x_val}: {e}")
        return 0.0

if expr:
    Y_values_best = np.array([safe_eval(expr, x_val) for x_val in X_values])

    # Wykres najlepszego osobnika ostatniej generacji
    ax.plot(X_values, Y_values_best, color='red', linestyle='--', label='Best Individual - Last Generation')
else:
    print("Expression parsing failed. Skipping the best individual plotting.")

ax.set_title("Comparison of Last Generation's Best Individual with Reference Function")
ax.set_xlabel("X")
ax.set_ylabel("Function Value")
ax.legend()

# Zapis wykresu do pliku
directory = "..\\plotting"
filename = "f5_comp_domain_4.png"
full_path = os.path.join(directory, filename)

os.makedirs(directory, exist_ok=True)
plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')
plt.show()
print(f"Plot saved at: {full_path}")
