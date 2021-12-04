package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

    private static final int FIELD_SZ = 5;

    public static void main(String[] args) {
        final List<String> values = readFile("inputs/input4.text");
        List<Integer> draws = Arrays.stream(values.get(0).split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
        List<Board> boards = new ArrayList<>();

        for (int i=2; i<values.size(); i+= FIELD_SZ + 1) {
            Board board = new Board(values.subList(i, i + FIELD_SZ));
            boards.add(board);
        }

        int winningBoard = -1;
        int winningDraw = -1;

        Set<Integer> wonBoards = new HashSet<>();

        outer:
        for (int i=0; i<draws.size(); i++) {
            for (int j=0; j<boards.size(); j++) {
                if (wonBoards.contains(j)) continue;

                boolean isWinning = boards.get(j).mark(draws.get(i));

                if (isWinning) {
                    winningBoard = j;
                    winningDraw = i;
                    wonBoards.add(j);
                    // Step 1
//                    break outer;
                }

                if (wonBoards.size() == boards.size()) break outer;
            }
        }

        System.out.println(boards.get(winningBoard).score() * draws.get(winningDraw));
    }

    static class Field {
        int nb;
        boolean isMarked;

        public Field(int nb, boolean isMarked) {
            this.nb = nb;
            this.isMarked = isMarked;
        }

        public boolean isMarked() {
            return isMarked;
        }
    }

    static class Board {
        private final HashMap<Integer, Field> lookup = new HashMap<>();
        private final Field[][] fields = new Field[FIELD_SZ][FIELD_SZ];

        public Board(List<String> lines) {
            for (int y = 0; y< FIELD_SZ; y++) {
                List<Integer> lineNumbers = Arrays.stream(lines.get(y).split(" +"))
                        .map(String::trim).filter(s -> s.length() > 0)
                        .map(Integer::parseInt).collect(Collectors.toList());
                for (int x = 0; x< FIELD_SZ; x++) {
                    Integer value = lineNumbers.get(x);
                    Field field = new Field(value, false);
                    lookup.put(value, field);
                    fields[x][y] = field;
                }
            }
        }

        public boolean mark(int value) {
            Field field = lookup.get(value);
            if (field == null) return false;

            field.isMarked = true;

            for (int idx = 0; idx < FIELD_SZ; idx++) {
                if (lineCount(idx, false) == FIELD_SZ
                        || lineCount(idx, true) == FIELD_SZ) return true;
            }

            return false;
        }

        private int lineCount(int ln, boolean direction) {
            int count = 0;
            int x = 0, y = 0, dx = 0, dy = 0;
            if (direction) x = ln; else y = ln;
            if (direction) dy = 1; else dx = 1;
            for (int step = 0; step < FIELD_SZ; step++) {
                if (fields[x + (step * dx)][y + (step * dy)].isMarked) count ++;
            }
            return count;
        }

        public int score() {
            int sum = 0;
            for (int y = 0; y < FIELD_SZ; y++) {
                for (int x = 0; x< FIELD_SZ; x++) {
                    if (! fields[x][y].isMarked) sum += fields[x][y].nb;
                }
            }
            return sum;
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
