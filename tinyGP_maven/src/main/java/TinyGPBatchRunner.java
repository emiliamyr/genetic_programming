import java.io.*;
import java.nio.file.*;
import java.util.List;

public class TinyGPBatchRunner {

    private static final String SCRIPTS_FOLDER = "C:\\Users\\emili\\Desktop\\studia\\5 sem\\programowanie genetyczne\\scripts\\data";
    private static final String OUTPUT_FOLDER = "C:\\Users\\emili\\Desktop\\studia\\5 sem\\programowanie genetyczne\\output";
    private static final long DEFAULT_SEED = -1;

    public static void main(String[] args) {
        try {
            // Create output folder if it doesn't exist
            Path outputDir = Paths.get(OUTPUT_FOLDER);
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            // Get all .dat files in the scripts folder
            List<Path> datFiles = Files.list(Paths.get(SCRIPTS_FOLDER))
                    .filter(path -> path.toString().endsWith(".dat"))
                    .toList();

            // Process each .dat file
            for (Path datFile : datFiles) {
                String fileNameWithoutExtension = datFile.getFileName().toString().replaceFirst("[.][^.]+$", ""); // Remove .dat extension
                String inputFilePath = datFile.toString();
                String outputFilePath = OUTPUT_FOLDER + "\\" + fileNameWithoutExtension + ".json";

                // Run TinyGP for each .dat file
                System.out.println("Processing file: " + inputFilePath);
                TinyGP gp = new TinyGP(inputFilePath, DEFAULT_SEED);
                gp.evolve();

                // Save the output JSON
                gp.saveOutputToJson(outputFilePath);
                System.out.println("Output saved to: " + outputFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
