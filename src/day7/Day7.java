package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day7 {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(readFile("inputs/input7.text"));
        int[] positions = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        int startPos = Arrays.stream(positions).sum() / positions.length;
        int minFuel = getFuel2(positions, startPos), oldFuel;

        do {
            oldFuel = minFuel;
            int lower = getFuel2(positions, startPos-1);
            int higher = getFuel2(positions, startPos+1);

            if (lower < minFuel) {
                minFuel = lower; startPos--;
            }
            if (higher < minFuel) {
                 minFuel = higher; startPos++;
            }

        } while (oldFuel != minFuel);

        System.out.println(minFuel);
    }

    private static int getFuel2(int[] positions, int curPosition) {
        int fuel = 0;
        for (int pos : positions) {
            int d = Math.abs(pos - curPosition);
            fuel += d * (d + 1) / 2;
        }
        return fuel;
    }

    private static List<String> readFile(String filename) {
        final List<String> values = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEach(values::add);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return values;
    }
}
