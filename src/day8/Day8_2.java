package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8_2 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(readFile("inputs/input8.text"));

        int digsum = 0;

        for (String line : lines) {
            String[] linesplit = line.split(" \\| ");
            String[] inputs = linesplit[0].split(" ");
            String[] outputs = linesplit[1].split(" ");
            int solve = solve(inputs, outputs);
            System.out.println(solve);
            digsum += solve;
        }

        System.out.println(digsum);
    }

    private static int solve(String[] inputs, String[] outputs) {
        Map<Character, Integer> inputStats = new HashMap<>();
        Map<String, Integer> numbersMap = new HashMap<>();
        Map<Character,Character> charMap = new HashMap<>();

        Set<String> cand5 = new HashSet<>();
        Set<String> cand6 = new HashSet<>();


        for (String input : inputs) {
            for (char inc : input.toCharArray()) {
                inputStats.compute(inc, (k, v) -> (v == null) ? 1 : v+1);
            }
            switch (input.length()) {
                case 2 -> numbersMap.put(sort(input), 1);
                case 3 -> numbersMap.put(sort(input), 7);
                case 4 -> numbersMap.put(sort(input), 4);
                case 7 -> numbersMap.put(sort(input), 8);

                case 5 -> cand5.add(sort(input));
                case 6 -> cand6.add(sort(input));
            }
        }

        Function<Integer, Set<Character>> findFreq = i -> inputStats.entrySet().stream()
                                                                 .filter(e -> i == e.getValue()).map(Map.Entry::getKey)
                                                                 .collect(Collectors.toSet());
        charMap.put('b', findFreq.apply(6).iterator().next());
        charMap.put('e', findFreq.apply(4).iterator().next());
        charMap.put('f', findFreq.apply(9).iterator().next());

        String five = cand5.stream().filter(s -> s.indexOf(charMap.get('b')) != -1).findFirst().get();
        numbersMap.put(five, 5);
        cand5.remove(five);

        String two = cand5.stream().filter(s -> s.indexOf(charMap.get('e')) != -1).findFirst().get();
        numbersMap.put(two, 2);
        cand5.remove(two);

        numbersMap.put(cand5.iterator().next(), 3);

        String nine = cand6.stream().filter(s -> s.indexOf(charMap.get('e')) == -1).findFirst().get();
        numbersMap.put(nine, 9);
        cand6.remove(nine);

        char[] oneChars = numbersMap.entrySet().stream()
                .filter(e -> 1 == e.getValue()).map(Map.Entry::getKey).findFirst().get().toCharArray();

        String zero = cand6.stream().filter(c -> c.indexOf(oneChars[0]) > -1 && c.indexOf(oneChars[1]) > -1).findFirst().get();
        numbersMap.put(zero, 0);
        cand6.remove(zero);

        numbersMap.put(cand6.iterator().next(), 6);

        return 1000 * numbersMap.get(sort(outputs[0])) +
                100 * numbersMap.get(sort(outputs[1])) +
                 10 * numbersMap.get(sort(outputs[2])) +
                      numbersMap.get(sort(outputs[3]));
    }

    private static String sort(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
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
