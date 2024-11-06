import java.io.*;
import java.nio.file.*;
import java.util.List;

public class TinyGPBatchRunner {

    private static final String SCRIPTS_FOLDER = "../scripts/data";  // Folder z danymi
    private static final String OUTPUT_FOLDER = "../output";  // Folder na wyniki
    private static final long DEFAULT_SEED = -1;  // Domyślny seed

    public static void main(String[] args) {
        try {
            Path outputDir = Paths.get(OUTPUT_FOLDER);
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);  // Tworzenie folderu wyjściowego, jeśli nie istnieje
            }

            List<Path> datFiles = Files.list(Paths.get(SCRIPTS_FOLDER))
                    .filter(path -> path.toString().endsWith(".dat") && path.getFileName().toString().contains("f6"))
                    .toList();

            for (Path datFile : datFiles) {
                String fileNameWithoutExtension = datFile.getFileName().toString().replaceFirst("[.][^.]+$", "");  // Usuwanie rozszerzenia .dat
                String inputFilePath = datFile.toString();  // Pełna ścieżka pliku wejściowego
                String outputFilePath = OUTPUT_FOLDER + "\\" + fileNameWithoutExtension + "v2.json";  // Ścieżka do pliku wyjściowego .json

                System.out.println("Processing file: " + inputFilePath);
                TinyGP gp = new TinyGP(inputFilePath, DEFAULT_SEED);  // Utworzenie instancji TinyGP
                gp.evolve();  // Uruchomienie ewolucji

                gp.saveOutputToJson(outputFilePath);  // Zapisanie wyników do pliku JSON
                System.out.println("Output saved to: " + outputFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();  // Obsługa wyjątków związanych z IO
        }
    }
}

