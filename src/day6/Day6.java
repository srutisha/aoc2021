package day6;

import day5.Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(readFile("inputs/input6.text"));
        int[] fishes = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        long[] ages = new long[9];

        for (int fish : fishes) {
            ages[fish] ++;
        }

        int evolution = 256;

        for (int day = 0; day < evolution; day ++) {
            long zerocount = ages[0];
            for (int mv = 0; mv < ages.length - 1; mv++) {
                ages[mv] = ages[mv+1];
            }
            ages[8] = zerocount;
            ages[6] += zerocount;
        }

        long sumFish = Arrays.stream(ages).sum();

        System.out.println(sumFish);
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
