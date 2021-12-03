package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3_2 {

    private static final char ONE = '1';
    private static final char ZERO = '0';

    public static void main(String[] args) {
        final List<String> values = readFile("inputs/input3.text");

        List<String> oxy = new ArrayList<>(values);
        List<String> co2 = new ArrayList<>(values);

        for (int i=0; i<12; i++) {
            final char oxyfilter = countKind(oxy, ONE, i) >= countKind(oxy, ZERO, i) ? ONE : ZERO;
            final char co2filter = countKind(co2, ZERO, i) <= countKind(co2, ONE, i) ? ZERO : ONE;

            oxy = filter(oxy, i, oxyfilter);
            co2 = filter(co2, i, co2filter);
        }

        System.out.println(Integer.parseInt(oxy.get(0), 2) * Integer.parseInt(co2.get(0), 2));
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

    private static int countKind(List<String> oxy, char c, int i) {
        return (int) oxy.stream().filter(v -> v.charAt(i) == c).count();
    }

    private static List<String> filter(List<String> values, int pos, char filter) {
        if (values.size() > 1)
            values = values.stream().filter(v -> v.charAt(pos) == filter).collect(Collectors.toList());
        return values;
    }
}
