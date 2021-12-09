package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day9_2 {

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

        List<Integer> basinSizes = new ArrayList<>();

        BiFunction<Integer, Integer, Boolean> isLowPoint = (x, y) -> {
            int pointheight = heightmap[x][y];
            boolean top = x == 0 || heightmap[x-1][y] > pointheight;
            boolean bottom = x == maxx-1 || heightmap[x+1][y] > pointheight;
            boolean left = y == 0 || heightmap[x][y-1] > pointheight;
            boolean right = y == maxy-1 || heightmap[x][y+1] > pointheight;
            return top && bottom && left && right;
        };

        BiFunction<Integer, Integer, Integer> findBasinSize = (x, y) -> {
            final Set<Point> visited = new HashSet<>();
            final Deque<Point> tovisit = new ArrayDeque<>();
            tovisit.push(new Point(x, y));
            int cnt = 0;
            while (tovisit.size() > 0) {
                Point cp = tovisit.pop();
                if (visited.contains(cp)) continue;

                visited.add(cp);
                cnt++;

                BiFunction<Integer, Integer, Boolean> addNotNine = (xl, yl) -> {
                    if (heightmap[xl][yl] < 9) tovisit.push(new Point(xl, yl));
                    return false;
                };

                if (cp.x > 0) addNotNine.apply(cp.x-1, cp.y );
                if (cp.x < maxx-1) addNotNine.apply(cp.x+1, cp.y );
                if (cp.y > 0) addNotNine.apply(cp.x, cp.y-1 );
                if (cp.y < maxy-1) addNotNine.apply(cp.x, cp.y+1 );
            }
            return cnt;
        };

        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (isLowPoint.apply(x, y)) {
                    riskLevelSum += heightmap[x][y] + 1;
                    basinSizes.add(findBasinSize.apply(x, y));
                }
            }
        }

        Collections.sort(basinSizes);
        int bz = basinSizes.size();

        System.out.println(riskLevelSum);
        System.out.println(basinSizes.get(bz-1) * basinSizes.get(bz-2) * basinSizes.get(bz-3));
    }

    static class Point {
        final int x;
        final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
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
