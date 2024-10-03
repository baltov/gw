package org.gw;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    private static final int TARGET_LENGTH = 9;
    private static final String URL_STRING = "https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt";
    private static final List<String> START_WORDS = Arrays.asList("I", "A");

    public static void main(String[] args) throws IOException {
        WordParser wordParser = new WordParser(URI.create(URL_STRING).toURL(), START_WORDS, TARGET_LENGTH);
        //mark the time after the data is loaded
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> result = wordParser.findValidWords();

        long endTime = System.currentTimeMillis();
        long timeTakenMillis = endTime - startTime;

        double timeTakenSeconds = timeTakenMillis / 1000.0;
        result.forEach((word, extensions) -> System.out.println(word + " -> " + extensions.reversed()));
        System.out.println("Total words: " + result.keySet().size());
        System.out.println("Execution time: " + timeTakenSeconds + " seconds (" + timeTakenMillis + " milliseconds)");
    }
}
