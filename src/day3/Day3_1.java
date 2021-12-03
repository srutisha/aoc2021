package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day3_1 {
    public static void main(String[] args) {
        final int[] counts = new int[12];
        try (Stream<String> stream = Files.lines(Paths.get("inputs/input3.text"))) {
            stream.forEach(line -> {
                for (int i=0; i<12; i++) {
                    if (line.charAt(i) =='1') counts[i] ++;
                }
            });
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        int eps = 0;
        int gamma = 0;
        for (int i=0; i<12; i++) {
            if (counts[i] > 500) { eps |= 1; } else { gamma |=1; }
            eps <<= 1;
            gamma <<= 1;
        }
        eps >>= 1;
        gamma >>= 1;

        System.out.println(eps * gamma);
    }
}
