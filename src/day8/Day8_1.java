package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day8_1 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(readFile("inputs/inputs8.text"));

        int digcnt = 0;

        for (String line : lines) {
            String[] outputs = line.split(" \\| ")[1].split(" ");
            for (String output : outputs) {
                int ol = output.length();
                if (ol == 2 || ol == 4 || ol == 3 || ol == 7) digcnt ++;
            }
        }
        System.out.println(digcnt);

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
