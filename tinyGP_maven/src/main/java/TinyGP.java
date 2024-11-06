import java.util.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class TinyGP {
    private final double[] fitness;
    private final char[][] population;
    private static final Random random = new Random();

    private static final int ADD = 110, SUB = 111, MUL = 112, DIV = 113, SIN = 114, COS = 115;
    private static final int FSET_START = ADD, FSET_END = COS;

    private static final int MAX_LEN = 100, POP_SIZE = 1000, DEPTH = 2, GENERATIONS = 100, TOURNAMENT_SIZE = 2;
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
        char primitive = (position == 0) ? 1 : (char) random.nextInt(2);

        if (primitive == 0 || depth == 0) {
            primitive = (char) random.nextInt(variableCount + randomConstantCount);
            buffer[position] = primitive;
            return position + 1;
        } else {
            primitive = (char) (random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
            buffer[position] = primitive;


            int childPos = growProgram(position + 1, maxLength, depth + 1);
            if (primitive == ADD || primitive == MUL || primitive == SUB) {

                if (primitive == ADD) {
                    if (buffer[position + 1] == 0) {
                        return childPos;
                    }
                }
                if (primitive == MUL) {
                    if (buffer[position + 1] == 1) {
                        return childPos;
                    }
                }
            }
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


        if (primitive < FSET_START) {
            return inputs[primitive];
        }

        switch (primitive) {
            case ADD:
                double leftAdd = evaluateProgram();
                double rightAdd = evaluateProgram();
                if (rightAdd == 0) return leftAdd;
                if (leftAdd == 0) return rightAdd;
                return leftAdd + rightAdd;

            case SUB:
                double leftSub = evaluateProgram();
                double rightSub = evaluateProgram();
                if (rightSub == 0) return leftSub;
                return leftSub - rightSub;

            case MUL:
                double leftMul = evaluateProgram();
                double rightMul = evaluateProgram();
                if (leftMul == 0 || rightMul == 0) return 0;
                if (leftMul == 1) return rightMul;
                if (rightMul == 1) return leftMul;
                return leftMul * rightMul;

            case DIV:
                double leftDiv = evaluateProgram();
                double rightDiv = evaluateProgram();
                if (Math.abs(rightDiv) <= 0.001) {
                    return leftDiv;
                }
                return leftDiv / rightDiv;


            case SIN:
                return Math.sin(evaluateProgram());
            case COS:
                return Math.cos(evaluateProgram());

            default:
                return 0.0;
        }
    }


    private int traverseProgram(char[] program, int index){
        if (program[index] < FSET_START) return ++index;
        switch (program[index]){
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                return traverseProgram(program, traverseProgram(program, ++index));
            case SIN:
            case COS:
                return traverseProgram(program, ++index); // Funkcje jednoargumentowe
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

    private char[] crossover(char[] parent1, char[] parent2){
        int crossoverPoint = random.nextInt(parent1.length);
        char[] offspring = new char[parent1.length];
        System.arraycopy(parent1, 0, offspring, 0, crossoverPoint);
        System.arraycopy(parent2, crossoverPoint, offspring, crossoverPoint, parent2.length - crossoverPoint);
        return offspring;
    }

    private char[] mutate(char[] program, double mutationProbability) {
        char[] mutatedProgram = Arrays.copyOf(program, program.length);
        for (int i = 0; i < mutatedProgram.length; i++) {
            if (random.nextDouble() < mutationProbability) {
                int newOp = random.nextInt(FSET_END - FSET_START + 1 + variableCount);
                if (newOp == ADD || newOp == MUL) {
                    int operand = random.nextInt(variableCount + randomConstantCount);
                    if (operand == 0) continue;
                    if (newOp == MUL && operand == 1) continue;
                }
                mutatedProgram[i] = (char) newOp;
            }
        }
        return mutatedProgram;
    }


    void saveOutputToJson(String fileName){
        try (PrintWriter writer = new PrintWriter(new File(fileName))){
            writer.println(generationsData.toString());
        } catch (FileNotFoundException e){
            System.out.println("ERROR: File cannot be saved.");
        }
    }

    private void reportPopulationStatistics(int generation){
        double sumFitness = 0.0;
        double sumProgramLength = 0.0;
        for (int i = 0; i < POP_SIZE; i++){
            sumFitness += fitness[i];
            sumProgramLength += population[i].length;
        }
        averageFitnessInPopulation = sumFitness / POP_SIZE;
        averageProgramLength = sumProgramLength / POP_SIZE;

        if (generation % 10 == 0){
            generationsData.put(createGenerationStats(generation));
        }

        System.out.printf("Generation %d -> Best: %.2f, Average: %.2f, AvgLength: %.2f%n",
                generation, bestFitnessInPopulation, averageFitnessInPopulation, averageProgramLength);
    }

    private JSONObject createGenerationStats(int generation){
        JSONObject stats = new JSONObject();
        stats.put("generation", generation);
        stats.put("bestFitness", bestFitnessInPopulation);
        stats.put("averageFitness", averageFitnessInPopulation);
        stats.put("averageProgramLength", averageProgramLength);
        return stats;
    }
}
