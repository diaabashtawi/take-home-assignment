import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        String configurationFilePath = "json\\config.json";
        Scanner input = new Scanner(System.in);
        JSONParser parser = new JSONParser();
        JSONObject data;
        try (FileReader reader = new FileReader(configurationFilePath)) {

            data = (JSONObject) parser.parse(reader);

            long  rows = (long) data.get("rows");
            long columns = (long) data.get("columns");

            JSONObject symbols = (JSONObject) data.get("symbols");
            JSONObject probabilities = (JSONObject) data.get("probabilities");

            String[][] matrix = generateMatrix(rows, columns, probabilities);
            System.out.println("Generated Matrix:");
            printMatrix(matrix);

            System.out.print("Enter your betting amount: ");
            int bettingAmount = input.nextInt();

            // Check winning combinations and calculate reward
            JSONObject winCombinations = (JSONObject) data.get("win_combinations");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[][] generateMatrix(long rows, long columns, JSONObject probabilities) {
        String[][] matrix = new String[(int) rows][(int) columns];
        JSONArray standardProbabilities = (JSONArray) probabilities.get("standard_symbols");
        Map<String, Integer> symbolsProbabilities = new HashMap<>();
        for (Object obj : standardProbabilities) {
            JSONObject cell = (JSONObject) obj;
            JSONObject symbols = (JSONObject) cell.get("symbols");
            int totalProbability = 0;
            for (Object key : symbols.keySet()) {
                totalProbability += ((Long) symbols.get(key)).intValue();
                symbolsProbabilities.put(key.toString(), totalProbability);
            }
        }

        JSONObject bonus_symbols = (JSONObject) probabilities.get("bonus_symbols");
        JSONObject bonusValue = (JSONObject) bonus_symbols.get("symbols");

        Map<String, Integer> bonusValues = new HashMap<>();
        for (Object key : bonusValue.keySet()){

            bonusValues.put( key.toString(), ((Long) bonusValue.get(key)).intValue());
        }
        int bonusRows = random.nextInt((int) rows);
        int bonusColumns = random.nextInt((int) columns);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix[row][col] = getRandomSymbol(symbolsProbabilities);

            }
        }

        matrix[bonusRows][bonusColumns] = generateBonusValue(bonusValues);

        return matrix;
    }

    private static void generateMatrixWithBonus(long rows, long columns, JSONObject bonusValue) {
        char[][] matrix = new char[(int) rows][(int) columns];
        int bonusRows = random.nextInt((int) rows);
        int bonusColumns = random.nextInt((int) columns);

        matrix[bonusRows][bonusColumns] = generateBonusValue(bonusValue).charAt(0);
    }

    private static String getRandomSymbol(Map<String, Integer> standardSymbolsProbabilities) {
        int total = Collections.max(standardSymbolsProbabilities.values());
        int randomValue = random.nextInt(total) + 1;
        for (String key : standardSymbolsProbabilities.keySet()) {
            if (randomValue <= standardSymbolsProbabilities.get(key)) {
                return key;
            }
        }
        return "";
    }

    public static String generateBonusValue(Map<String, Integer> bonus) {
        int bonusMaxValue = Collections.max(bonus.values());
        int randomBonus = random.nextInt(bonusMaxValue) + 1;
        for (String key : bonus.keySet()) {
            if (randomBonus == bonus.get(key)) {
                return key;
            }
        }
        return " ";

    }

    private static void printMatrix(String[][] matrix) {
        for (String [] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static double calculateReward(
            JSONObject winCombinations,
            int bettingAmount,
            int matchingSymbols,
            double rewardForSymbol
    ) {
        int totalReward = 0;
        for (Object key : winCombinations.keySet()) {
            JSONObject condition = (JSONObject) winCombinations.get(key);

        }
        System.out.println(totalReward);
        return totalReward;
    }
}