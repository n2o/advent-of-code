import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Day2 {
    private static String[] handleInput() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("./../../resources/day2"))) {
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (Exception ignored) {

        }
        return sb.toString().split(System.lineSeparator());
    }

    private static int getChecksum(String[] data) {
        int twoSameCounter = 0;
        int threeSameCounter = 0;

        for (String s : data) {
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char key = s.charAt(i);
                int count = (map.getOrDefault(key, 0) + 1);
                map.put(key, count);
            }

            if (map.containsValue(2)) twoSameCounter++;
            if (map.containsValue(3)) threeSameCounter++;
        }
        return twoSameCounter * threeSameCounter;
    }

    private static boolean doStringsDifferByOne(String a, String b) {
        if (a.length() != b.length()) return false;
        int diffChars = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diffChars++;
                if (diffChars > 1) return false;
            }
        }
        return true;
    }

    private static String getCommonLetters(String[] data) {
        int indexFirst = -1;
        int indexSecond = -1;
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (doStringsDifferByOne(data[i], data[j])) {
                    indexFirst = i;
                    indexSecond = j;
                    break;
                }
            }
            if (indexFirst != -1) break;
        }
        StringBuilder sol = new StringBuilder();

        for (int i = 0; i < data[indexFirst].length(); i++) {
            if (data[indexFirst].charAt(i) == data[indexSecond].charAt(i)) {
                sol.append(data[indexFirst].charAt(i));
            }
        }
        return sol.toString();
    }

    public static void main(String[] args) {
        System.out.println("Part one: " + getChecksum(handleInput()));
        System.out.println("Part two: " + getCommonLetters(handleInput()));
    }
}
