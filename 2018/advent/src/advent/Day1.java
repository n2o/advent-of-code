import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class Day1 {
    private static int[] handleInput() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("./../../resources/day1"))) {
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (Exception ignored) {

        }
 //       System.out.println(sb.toString());
        String[] foo = sb.toString().split("\n");
      //  foo[0] = foo[0].substring(1, foo[0].length() -1);
        int[] data = new int[foo.length];
        for (int i = 0; i < foo.length; i++) {
            data[i] = Integer.parseInt(foo[i].trim());
        }
        return data;
    }

    private static int partOne(int[] data) {
        int erg = 0;
        for (int i : data) erg += i;
        return erg;
    }

    private static int partTwo(int[] data) {
        HashSet<Integer> set = new HashSet<>();
        int freq = 0;

        while (true) {
            for (int i : data) {
                if (set.contains(freq)) {
                    return freq;
                } else {
                    set.add(freq);
                }
                freq += i;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Teil eins: " + partOne(handleInput()));
        System.out.println("Teil zwei: " + partTwo(handleInput()));
    }
}
