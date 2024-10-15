import csv


# Funkcja do przetwarzania surowych danych z TinyGP
def convert_raw_data_to_csv(input_file, csv_output_file):
    # Otwieramy plik wejściowy z surowymi danymi
    with open(input_file, 'r') as infile, open(csv_output_file, 'w', newline='') as outfile:
        writer = csv.writer(outfile)

        # Zapisujemy nagłówki kolumn do pliku CSV
        writer.writerow(["X", "Y"])  # Nagłówki dla wartości X (wejściowej) i Y (wynikowej)

        # Przetwarzanie każdej linii pliku wejściowego
        for line in infile:
            # Pomijamy linie, które mogą zawierać nagłówek lub inne niepotrzebne dane (np. pierwsza linia)
            if line.strip().split()[0].replace('.', '', 1).isdigit():
                # Dzielimy linię na wartości X i Y, oddzielone spacją
                data = line.strip().split()
                # Zapisujemy dane do pliku CSV
                writer.writerow(data)


# Przykład użycia skryptu:
convert_raw_data_to_csv("f1_domain_1.dat", "f1_domain_1.csv")
