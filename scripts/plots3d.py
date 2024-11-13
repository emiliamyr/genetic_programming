import json
import numpy as np
import os
import matplotlib.pyplot as plt
import sympy as sp
from matplotlib.lines import Line2D


def f5(x, y):
    return x ** 2 + 3 * x * y - 7 * y + 1

file_path = "..\\output2\\f6_domain_1.json"
with open(file_path, "r") as file:
    data = json.load(file)

last_generation = data[-1]
best_individual_expr = last_generation["best_individual"]

print("Best Individual Expression:", best_individual_expr)

X_values = np.linspace(-10, 10, 50)
Y_values = np.linspace(-10, 10, 50)
X_grid, Y_grid = np.meshgrid(X_values, Y_values)

Z_values_ref = f5(X_grid, Y_grid)
fig = plt.figure(figsize=(12, 8))
ax = fig.add_subplot(111, projection='3d')

ref_surface = ax.plot_surface(X_grid, Y_grid, Z_values_ref, color='blue', alpha=0.5)
ref_surface.set_label('Reference Function (f6(x, y))')

x, y = sp.symbols('x y')

try:
    expr = sp.sympify(best_individual_expr.replace("X1", "x").replace("X2", "y"))
    print(f"Parsed Expression: {expr}")
except Exception as e:
    print(f"Error parsing expression: {e}")
    expr = None

Z_values_best = np.zeros_like(X_grid)

def safe_eval(expr, x_val, y_val):
    try:
        value = expr.subs({x: x_val, y: y_val})

        if value.is_real:
            return float(value)
        else:
            return 0.0
    except ZeroDivisionError:
        print(f"ZeroDivisionError at x={x_val}, y={y_val}")
        return 0.0
    except Exception as e:
        print(f"Error evaluating expression at x = {x_val}, y = {y_val}: {e}")
        return 0.0
if expr:
    Z_values_best = np.zeros_like(X_grid)

    for i in range(X_grid.shape[0]):
        for j in range(X_grid.shape[1]):
            x_val = X_grid[i, j]
            y_val = Y_grid[i, j]
            Z_values_best[i, j] = safe_eval(expr, x_val, y_val)

    best_surface = ax.plot_surface(X_grid, Y_grid, Z_values_best, color='red', alpha=0.5)
    best_surface.set_label('Best Individual - Last Generation')
else:
    print("Expression parsing failed. Skipping the best individual plotting.")

if expr:
    for i in range(X_grid.shape[0]):
        for j in range(X_grid.shape[1]):
            x_val = X_grid[i, j]
            y_val = Y_grid[i, j]
            Z_values_best[i, j] = safe_eval(expr, x_val, y_val)

    best_surface = ax.plot_surface(X_grid, Y_grid, Z_values_best, color='red', alpha=0.5)
    best_surface.set_label('Best Individual - Last Generation')

else:
    print("Expression parsing failed. Skipping the best individual plotting.")

ax.set_title("Comparison of Last Generation's Best Individual with Reference Function")
ax.set_xlabel("X")
ax.set_ylabel("Y")
ax.set_zlabel("Function Value")

legend_elements = [
    Line2D([0], [0], color='blue', lw=4, label='Reference Function (f6(x, y))'),
    Line2D([0], [0], color='red', lw=4, label='Best Individual - Last Generation')
]
ax.legend(handles=legend_elements, loc='upper right')

directory = "..\\plotting2"
filename = "f6_comp_domain_1.png"
full_path = os.path.join(directory, filename)


os.makedirs(directory, exist_ok=True)
plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')
plt.show()
print(f"Plot saved at: {full_path}")