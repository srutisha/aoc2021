package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day10 {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(readFile("inputs/input10.text"));
        List<Long> scoresp2 = new ArrayList<>();

        int scorep1 = 0;

        outer:
        for (String line : lines) {
            Deque<Character> chars = new ArrayDeque<>();
            Function<Character, Boolean> check = c -> chars.pop() == c;

            for (int ci=0; ci<line.length(); ci++) {
                char c = line.charAt(ci);
                switch (c) {
                    case '<', '(', '[', '{' -> chars.push(c);
                    case ')' -> { if (!check.apply('(')) { scorep1 += 3; continue outer; } }
                    case ']' -> { if (!check.apply('[')) { scorep1 += 57; continue outer; } }
                    case '}' -> { if (!check.apply('{')) { scorep1 += 1197; continue outer; } }
                    case '>' -> { if (!check.apply('<')) { scorep1 += 25137; continue outer; } }
                }
            }

            long linescore = 0;
            while (!chars.isEmpty()) {
                char c = chars.pop();
                linescore *= 5;
                switch (c) {
                    case '(' -> linescore += 1;
                    case '[' -> linescore += 2;
                    case '{' -> linescore += 3;
                    case '<' -> linescore += 4;
                }
            }

            scoresp2.add(linescore);
        }
        System.out.println(scorep1);
        Collections.sort(scoresp2);
        System.out.println(scoresp2.get(scoresp2.size() / 2));
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
