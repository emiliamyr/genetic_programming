import json
import numpy as np
import os
import matplotlib.pyplot as plt
import sympy as sp

def f5(x):
    return np.sin(x) + np.cos(x)

file_path = "..\\simp2\\f2_d2_s.json"
with open(file_path, "r") as file:
    data = json.load(file)

#last_generation = data[-1]
#best_individual_expr = last_generation["best_individual"]
best_individual_expr = data["simplified"]

print("Best Individual Expression:", best_individual_expr)

# dziedzina
X_values = np.linspace(-3.14, 3.14, 100)

Y_values_ref = f5(X_values)

fig, ax = plt.subplots(figsize=(10, 6))

ax.plot(X_values, Y_values_ref, color='blue', label='Reference Function (f2(x))')

x = sp.symbols('x')

try:
    expr = sp.sympify(best_individual_expr.replace("X1", "x"))
    print(f"Parsed Expression: {expr}")
except Exception as e:
    print(f"Error parsing expression: {e}")
    expr = None

def safe_eval(expr, x_val):
    try:
        value = expr.subs(x, x_val)
        if value.is_real:
            return float(value)
        else:
            return 0.0
    except ZeroDivisionError:
        print(f"ZeroDivisionError at x={x_val}")
        return 0.0
    except Exception as e:
        print(f"Error evaluating expression at x = {x_val}: {e}")
        return 0.0

if expr:
    Y_values_best = np.array([safe_eval(expr, x_val) for x_val in X_values])

    ax.plot(X_values, Y_values_best, color='red', linestyle='--', label='Best Individual - Last Generation')
else:
    print("Expression parsing failed. Skipping the best individual plotting.")

ax.set_title("Comparison of Last Generation's Best Individual with Reference Function")
ax.set_xlabel("X")
ax.set_ylabel("Function Value")
ax.legend()

directory = "..\\plotting2"
filename = "f2_comp_domain_2.png"
full_path = os.path.join(directory, filename)

os.makedirs(directory, exist_ok=True)
plt.savefig(full_path, format='png', dpi=300, bbox_inches='tight')
plt.show()
print(f"Plot saved at: {full_path}")
