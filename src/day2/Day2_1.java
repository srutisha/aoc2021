package day2;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2_1 {
    public static void main(String[] args) {
        int horizontalDelta = 0;
        int verticalDelta = 0;
        try (Stream<String> stream = Files.lines(Paths.get("inputs/input2.text"))) {
            List<Pair<String,Integer>> values = stream.map(line -> {
                String[] parts = line.split(" ");
                return Pair.of(parts[0], Integer.parseInt(parts[1]));
            }).collect(Collectors.toList());
            for (Pair<String, Integer> move : values) {
                switch (move.getLeft()) {
                    case "forward" -> horizontalDelta += move.getRight();
                    case "down" -> verticalDelta += move.getRight();
                    case "up" -> verticalDelta -= move.getRight();
                    default -> throw new IllegalStateException("?");
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println(horizontalDelta * verticalDelta);

    }

}
