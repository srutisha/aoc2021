package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String[] args) {
        List<Line> lines = readFile("inputs/input5.text").stream().map(Line::new).collect(Collectors.toList());
        int mx = 1000, my = 1000;
        boolean isFirstStep = false;
        int[][] board = new int[mx][my];

        for (Line line : lines) {
            if (isFirstStep && ! (line.p1[0] == line.p2[0] || line.p1[1] == line.p2[1])) continue;
            int xd = line.p1[0] == line.p2[0] ? 0 : (line.p1[0] >= line.p2[0] ? -1 : 1);
            int yd = line.p1[1] == line.p2[1] ? 0 : (line.p1[1] >= line.p2[1] ? -1 : 1);
            int x = line.p1[0];
            int y = line.p1[1];
            board[x][y] ++;
            while (x != line.p2[0] || y != line.p2[1]) {
                x += xd;
                y += yd;
                board[x][y] ++;
            }
        }

        int overlaps = 0;

        for (int x = 0; x < mx; x++) {
            for (int y = 0; y < mx; y++) {
                if (board[x][y] > 1) overlaps ++;
            }
        }

        System.out.println(overlaps);
    }

    static class Line {
        final int[] p1;
        final int[] p2;

        public Line(String s) {
            String[] parts = s.split(" -\\> ");
            p1 = top(parts[0]);
            p2 = top(parts[1]);
        }

        private int[] top(String part) {
            return Arrays.stream(part.split(",")).mapToInt(Integer::parseInt).toArray();
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
