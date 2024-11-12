import java.io.*;
import java.nio.file.*;
import java.util.List;

public class TinyGPBatchRunner {

    private static final String SCRIPTS_FOLDER = "..\\scripts\\data";
    private static final String OUTPUT_FOLDER = "..\\output2";
    private static final long DEFAULT_SEED = -1;

    public static void main(String[] args) {
        try {
            Path outputDir = Paths.get(OUTPUT_FOLDER);
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            List<Path> datFiles = Files.list(Paths.get(SCRIPTS_FOLDER))
                    .filter(path -> path.toString().endsWith(".dat"))
                    .toList();

            for (Path datFile : datFiles) {
                String fileNameWithoutExtension = datFile.getFileName().toString().replaceFirst("[.][^.]+$", "");
                String inputFilePath = datFile.toString();
                String outputFilePath = OUTPUT_FOLDER + "\\" + fileNameWithoutExtension + ".json";

                System.out.println("Processing file: " + inputFilePath);
                TinyGP gp = new TinyGP(inputFilePath, DEFAULT_SEED);
                gp.evolve();

                gp.saveOutputToJson(outputFilePath);
                System.out.println("Output saved to: " + outputFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
