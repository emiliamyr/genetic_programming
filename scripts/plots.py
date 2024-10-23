import os
import json
import pandas as pd
import matplotlib.pyplot as plt

# Define the folder where the JSON files are located and where to save the plots
OUTPUT_FOLDER = "C:\\Users\\emili\\Desktop\\studia\\5 sem\\programowanie genetyczne\\output"
PLOTS_FOLDER = "C:\\Users\\emili\\Desktop\\studia\\5 sem\\programowanie genetyczne\\plots"

# Function to create the plots folder if it doesn't exist
def create_plots_folder(plots_folder):
    if not os.path.exists(plots_folder):
        os.makedirs(plots_folder)
        print(f"Created directory: {plots_folder}")

# Function to load JSON files into a pandas DataFrame
def load_json_data(output_folder):
    data = []

    # Iterate over all JSON files in the output folder
    for file_name in os.listdir(output_folder):
        if file_name.endswith(".json"):
            file_path = os.path.join(output_folder, file_name)
            with open(file_path, 'r') as file:
                json_data = json.load(file)
                for generation_data in json_data:
                    generation_data["file"] = file_name  # Add file name as a column
                    data.append(generation_data)

    # Convert list of JSON data to a DataFrame
    df = pd.DataFrame(data)
    return df

# Function to save plot to file
def save_plot(plt, filename):
    save_path = os.path.join(PLOTS_FOLDER, filename)
    plt.savefig(save_path)
    print(f"Saved plot to {save_path}")
    plt.close()

# Function to plot average fitness over generations for each file and save it
def plot_avg_fitness_for_each_file(df):
    for file_name in df['file'].unique():
        file_data = df[df['file'] == file_name]

        plt.figure(figsize=(10, 6))
        plt.plot(file_data['generation'], file_data['avg_fitness'], label='Avg Fitness')

        plt.title(f'Average Fitness Across Generations ({file_name})')
        plt.xlabel('Generation')
        plt.ylabel('Average Fitness')
        plt.legend(loc="best")
        plt.grid(True)

        # Save plot to file
        save_plot(plt, f"{file_name}_avg_fitness.png")

# Function to plot best fitness over generations for each file and save it
def plot_best_fitness_for_each_file(df):
    for file_name in df['file'].unique():
        file_data = df[df['file'] == file_name]

        plt.figure(figsize=(10, 6))
        plt.plot(file_data['generation'], file_data['best_fitness'], label='Best Fitness')

        plt.title(f'Best Fitness Across Generations ({file_name})')
        plt.xlabel('Generation')
        plt.ylabel('Best Fitness')
        plt.legend(loc="best")
        plt.grid(True)

        # Save plot to file
        save_plot(plt, f"{file_name}_best_fitness.png")

# Function to plot average program length over generations for each file and save it
def plot_avg_program_length_for_each_file(df):
    for file_name in df['file'].unique():
        file_data = df[df['file'] == file_name]

        plt.figure(figsize=(10, 6))
        plt.plot(file_data['generation'], file_data['avg_program_length'], label='Avg Program Length')

        plt.title(f'Average Program Length Across Generations ({file_name})')
        plt.xlabel('Generation')
        plt.ylabel('Average Program Length')
        plt.legend(loc="best")
        plt.grid(True)

        # Save plot to file
        save_plot(plt, f"{file_name}_avg_program_length.png")

# Create plots folder
create_plots_folder(PLOTS_FOLDER)

# Load data into a DataFrame
df = load_json_data(OUTPUT_FOLDER)

# Plotting and saving separate plots for each file
plot_avg_fitness_for_each_file(df)
plot_best_fitness_for_each_file(df)
plot_avg_program_length_for_each_file(df)
