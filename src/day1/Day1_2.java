package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1_2 {
    public static void main(String[] args) {
        int numberOfIncreases = 0;
        try (Stream<String> stream = Files.lines(Paths.get("inputs/input1.text"))) {
            List<Integer> values = stream.map(Integer::parseInt)
                    .collect(Collectors.toList());
            int previous = values.get(0) + values.get(1) + values.get(2);

            for (int i=3; i<values.size(); i++) {
                int newValue = previous - values.get(i-3) + values.get(i);
                if (previous < newValue) numberOfIncreases++;
                previous = newValue;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println(numberOfIncreases);
    }
}
