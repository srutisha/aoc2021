package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day9_1 {


    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(readFile("inputs/input9.text"));

        int maxy = lines.size();
        int maxx = lines.get(0).length();

        int[][] heightmap = new int[maxx][maxy];

        for (int y = 0; y < maxy; y++) {
            String line = lines.get(y);
            // JDK FAIL
            // int[] heights = Arrays.stream(line.toCharArray())
            for (int x=0; x < maxx; x++) {
                heightmap[x][y] = Integer.parseInt("" + line.charAt(x));
            }
        }

        int riskLevelSum = 0;

        BiFunction<Integer, Integer, Boolean> isLowPoint = (x, y) -> {
            int pointheight = heightmap[x][y];
            boolean top = x == 0 || heightmap[x-1][y] > pointheight;
            boolean bottom = x == maxx-1 || heightmap[x+1][y] > pointheight;
            boolean left = y == 0 || heightmap[x][y-1] > pointheight;
            boolean right = y == maxy-1 || heightmap[x][y+1] > pointheight;
            return top && bottom && left && right;
        };

        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (isLowPoint.apply(x, y)) {
                    System.out.println(heightmap[x][y]);
                    riskLevelSum += heightmap[x][y] + 1;
                }
            }
        }

        System.out.println(riskLevelSum);
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
