package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1_1 {
    public static void main(String[] args) {
        int previous = -1;
        int numberOfIncreases = 0;
        try (Stream<String> stream = Files.lines(Paths.get("inputs/input1.text"))) {
            List<Integer> values = stream.map(Integer::parseInt)
                    .collect(Collectors.toList());
            for (int value : values) {
                if (previous != -1) {
                    if (previous < value) numberOfIncreases++;
                }
                previous = value;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println(numberOfIncreases);
    }
}
