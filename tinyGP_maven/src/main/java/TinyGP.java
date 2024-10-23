import java.util.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class TinyGP {
    private final double[] fitness;
    private final char[][] population;
    private static final Random random = new Random();

    private static final int ADD = 110, SUB = 111, MUL = 112, DIV = 113;
    private static final int FSET_START = ADD, FSET_END = DIV;

    private static final int MAX_LEN = 100, POP_SIZE = 10000, DEPTH = 2, GENERATIONS = 100, TOURNAMENT_SIZE = 2;
    private static final double PMUT_PER_NODE = 0.05, CROSSOVER_PROB = 0.9;
    private static final double []inputs = new double[FSET_START];
    private static double minRandom, maxRandom;
    private static char [] program;
    private static int programCounter;
    private static int variableCount, fitnessCases, randomConstantCount;
    private static double bestFitnessInPopulation = 0.0, averageFitnessInPopulation = 0.0;
    private static long seed;
    private static double averageProgramLength;
    private static double[][] targetData;

    // Obiekt JSON do przechowywania wszystkich generacji
    private JSONArray generationsData;

    public TinyGP(String fileName, long seedValue){
        fitness = new double[POP_SIZE];
        seed = seedValue;
        generationsData = new JSONArray();

        if (seed >= 0){
            random.setSeed(seed);
        }

        loadFitnessCases(fileName);
        initializeRandomConstants();
        population = createInitialPopulation(POP_SIZE, DEPTH);
    }

    public static void main(String[] args){
        String fileName = "C:\\Users\\emili\\Desktop\\studia\\5 sem\\programowanie genetyczne\\scripts\\data";
        long seedValue = -1;

        if (args.length == 2){
            seedValue = Long.parseLong(args[0]);
            fileName = args[1];
        } else if (args.length == 1){
            fileName = args[0];
        }

        TinyGP gp = new TinyGP(fileName, seedValue);
        gp.evolve();

        // Zapis danych do pliku JSON po zakończeniu ewolucji
        gp.saveOutputToJson("output.json");
    }

    public void evolve(){
        printParameters();
        reportPopulationStatistics(0);

        for (int generation = 1; generation < GENERATIONS; generation++){
            if (bestFitnessInPopulation > -0.1){
                System.out.println("PROBLEM SOLVED");
                return;
            }

            for (int i = 0; i < POP_SIZE; i++){
                char[] offspring = performEvolutionaryOperation();
                double offspringFitness = calculateFitness(offspring);
                int worstIndividualIndex = performNegativeTournament();
                population[worstIndividualIndex] = offspring;
                fitness[worstIndividualIndex] = offspringFitness;
            }

            reportPopulationStatistics(generation);
        }

        System.out.println("PROBLEM *NOT* SOLVED");
    }

    void printParameters() {
        System.out.printf("-- TINY GP --%nSEED=%d%nMAX_LEN=%d%nPOPSIZE=%d%nDEPTH=%d%nCROSSOVER_PROB=%.2f%n" +
                        "PMUT_PER_NODE=%.2f%nMIN_RANDOM=%.2f%nMAX_RANDOM=%.2f%nGENERATIONS=%d%nTOURNAMENT_SIZE=%d%n" +
                        "----------------------------------%n",
                seed, MAX_LEN, POP_SIZE, DEPTH, CROSSOVER_PROB, PMUT_PER_NODE, minRandom, maxRandom, GENERATIONS, TOURNAMENT_SIZE);
    }

    private char[] performEvolutionaryOperation(){
        if (random.nextDouble() < CROSSOVER_PROB) {
            int parent1 = performTournament();
            int parent2 = performTournament();
            return crossover(population[parent1], population[parent2]);
        } else {
            int parent = performTournament();
            return mutate(population[parent], PMUT_PER_NODE);
        }
    }

    void loadFitnessCases(String fname) {
        try {
            int i, j;
            String line;

            BufferedReader in = new BufferedReader(new FileReader(fname));
            line = in.readLine();
            StringTokenizer tokens = new StringTokenizer(line);

            variableCount = (int) Double.parseDouble(tokens.nextToken().trim());
            randomConstantCount = (int) Double.parseDouble(tokens.nextToken().trim());
            minRandom = Double.parseDouble(tokens.nextToken().trim());
            maxRandom = Double.parseDouble(tokens.nextToken().trim());
            fitnessCases = (int) Double.parseDouble(tokens.nextToken().trim());

            targetData = new double[fitnessCases][variableCount + 1];

            for (i = 0; i < fitnessCases; i++) {
                line = in.readLine();
                tokens = new StringTokenizer(line);
                for (j = 0; j <= variableCount; j++) {
                    targetData[i][j] = Double.parseDouble(tokens.nextToken().trim());
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Please provide a data file");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("ERROR: Incorrect data format");
            System.exit(0);
        }
    }

    private void initializeRandomConstants(){
        for (int i = 0; i < FSET_START; i++){
            inputs[i] = (maxRandom - minRandom) * random.nextDouble() + minRandom;
        }
    }

    private char[][] createInitialPopulation(int size, int maxDepth){
        char[][] population = new char[size][];
        for (int i = 0; i < size; i++){
            population[i] = createRandomIndividual(maxDepth);
            fitness[i] = calculateFitness(population[i]);
        }
        return population;
    }

    private char[] createRandomIndividual(int maxDepth){
        int length = growProgram(0, MAX_LEN, maxDepth);
        while (length < 0){
            length = growProgram(0, MAX_LEN, maxDepth);
        }
        char[] individual = new char[length];
        System.arraycopy(buffer, 0, individual, 0, length);
        return individual;
    }

    int growProgram(int position, int maxLength, int depth) {
        if (position >= maxLength) return -1;
        char primitive = (position == 0) ? 1: (char) random.nextInt(2);

        if ( primitive == 0 || depth == 0 ) {
            primitive = (char) random.nextInt(variableCount + randomConstantCount);
            buffer[position] = primitive;
            return position + 1;
        }
        else  {
            primitive = (char) (random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
            buffer[position] = primitive;
            int childPos = growProgram(position + 1, maxLength, depth + 1);
            if (childPos < 0) return -1;
            return growProgram(childPos, maxLength, depth - 1);
        }
    }

    private double calculateFitness(char[] program){
        double fitnessScore = 0.0;
        int length = traverseProgram(program, 0);
        for (int i = 0; i < fitnessCases; i++){
            for (int j = 0; j < variableCount; j++){
                inputs[j] = targetData[i][j];
            }
            TinyGP.program = program;
            programCounter = 0;
            double result = evaluateProgram();
            fitnessScore += Math.abs(result - targetData[i][variableCount]);
        }
        return -fitnessScore;
    }

    private double evaluateProgram() {
        char primitive = program[programCounter++];
        if ( primitive < FSET_START )
            return inputs[primitive];

        return switch (primitive) {
            case ADD -> evaluateProgram() + evaluateProgram();
            case SUB -> evaluateProgram() - evaluateProgram();
            case MUL -> evaluateProgram() * evaluateProgram();
            case DIV -> {
                double num = evaluateProgram(), den = evaluateProgram();
                yield (Math.abs(den) <= 0.001) ? num : num / den;
            }
            default -> 0.0;
        };
    }

    private int traverseProgram(char[] program, int index){
        if (program[index] < FSET_START) return ++index;
        switch (program[index]){
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                return traverseProgram(program, traverseProgram(program, ++index));
            default:
                return 0;
        }
    }

    private int performTournament(){
        return tournamentSelection(fitness, TOURNAMENT_SIZE, true);
    }

    private int performNegativeTournament(){
        return tournamentSelection(fitness, TOURNAMENT_SIZE, false);
    }

    private int tournamentSelection(double[] fitness, int size, boolean isBest){
        int selected = random.nextInt(POP_SIZE);
        double selectedFitness = isBest ? -Double.MAX_VALUE: Double.MAX_VALUE;

        for (int i = 0; i < size; i++){
            int competitor = random.nextInt(POP_SIZE);
            if ((isBest && fitness[competitor] > selectedFitness) ||
                    (!isBest && fitness[competitor] < selectedFitness)){
                selectedFitness = fitness[competitor];
                selected = competitor;
            }
        }
        return selected;
    }

    private void reportPopulationStatistics(int generation){
        int nodeCount = 0;
        int bestIndividualIndex = random.nextInt(POP_SIZE);
        bestFitnessInPopulation = fitness[bestIndividualIndex];
        averageFitnessInPopulation = 0.0;

        for (int i = 0; i < POP_SIZE; i++){
            nodeCount += traverseProgram(population[i], 0);
            averageFitnessInPopulation += fitness[i];
            if (fitness[i] > bestFitnessInPopulation){
                bestIndividualIndex = i;
                bestFitnessInPopulation = fitness[i];
            }
        }

        averageProgramLength = (double) nodeCount / POP_SIZE;
        averageFitnessInPopulation /= POP_SIZE;

        // Konstruujemy obiekt JSON dla bieżącej generacji
        JSONObject generationData = new JSONObject();
        generationData.put("generation", generation);
        generationData.put("avg_fitness", -averageFitnessInPopulation);
        generationData.put("best_fitness", -bestFitnessInPopulation);
        generationData.put("avg_program_length", averageProgramLength);

        String bestIndividualString = individualToString(population[bestIndividualIndex], 0);
        generationData.put("best_individual", bestIndividualString);

        // Dodajemy dane generacji do głównej listy
        generationsData.put(generationData);
    }

    private String individualToString(char[] program, int index) {
        StringBuilder sb = new StringBuilder();

        if (program[index] < FSET_START) {
            if (program[index] < variableCount) {
                sb.append("X").append(program[index] + 1).append(" ");
            } else {
                sb.append(inputs[program[index]]).append(" ");
            }
            return sb.toString(); // Zwróć ciąg znaków z odpowiednim indeksem zmiennej lub stałej
        }

        switch (program[index]) {
            case ADD:
                sb.append("(");
                sb.append(individualToString(program, index + 1)); // Rekurencyjne generowanie dla lewej strony
                sb.append(" + ");
                break;
            case SUB:
                sb.append("(");
                sb.append(individualToString(program, index + 1));
                sb.append(" - ");
                break;
            case MUL:
                sb.append("(");
                sb.append(individualToString(program, index + 1));
                sb.append(" * ");
                break;
            case DIV:
                sb.append("(");
                sb.append(individualToString(program, index + 1));
                sb.append(" / ");
                break;
        }

        sb.append(individualToString(program, traverseProgram(program, index + 1))); // Rekurencyjne generowanie dla prawej strony
        sb.append(")");
        return sb.toString();
    }

    private char[] crossover(char[] parent1, char[] parent2) {
        int parent1Start = random.nextInt(traverseProgram(parent1, 0));
        int parent1End = traverseProgram(parent1, parent1Start);
        int parent2Start = random.nextInt(traverseProgram(parent2, 0));
        int parent2End = traverseProgram(parent2, parent2Start);

        int offspringLength = parent1Start + (parent2End - parent2Start) + (traverseProgram(parent1, 0) - parent1End);
        char[] offspring = new char[offspringLength];

        System.arraycopy(parent1, 0, offspring, 0, parent1Start);
        System.arraycopy(parent2, parent2Start, offspring, parent1Start, (parent2End - parent2Start));
        System.arraycopy(parent1, parent1End, offspring, parent1Start + (parent2End - parent2Start), (traverseProgram(parent1, 0) - parent1End));

        return offspring;
    }

    private char[] mutate(char[] parent, double mutationProbability) {
        int length = traverseProgram(parent, 0);
        char[] offspring = new char[length];
        System.arraycopy(parent, 0, offspring, 0, length);

        for (int i = 0; i < length; i++) {
            if (random.nextDouble() < mutationProbability) {
                if (offspring[i] < FSET_START) {
                    offspring[i] = (char) random.nextInt(variableCount + randomConstantCount);
                } else {
                    offspring[i] = (char) (random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
                }
            }
        }

        return offspring;
    }

    private static char[] buffer = new char[MAX_LEN];

    // Zapis danych do pliku JSON
    public void saveOutputToJson(String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(generationsData.toString(4)); // Formatowanie z wcięciem dla lepszej czytelności
            System.out.println("Zapisano dane do pliku " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
};
