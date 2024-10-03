package org.gw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class WordParserTest {

    @Mock
    private URL mockUrl;

    private String data = """
            M
            MA
            AM
            TM
            MAT
            TAM
            DDD
            DDM
            """;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() throws IOException {
        //if we don't call below, we will get NullPointerException
        closeable = MockitoAnnotations.openMocks(this);
        when(mockUrl.openStream()).thenAnswer(e -> new ByteArrayInputStream(data.getBytes()));
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testFindValidWordsExpectEmptyMapGivenEmptyStartWords() throws IOException {
        WordParser wordParser = new WordParser(mockUrl, List.of(), 3);
        assertTrue(wordParser.findValidWords().isEmpty(), "Expect an empty map when start words are empty");
    }

    @Test
    public void testFindValidWordsExpectSingleValidWordIsFound() throws IOException {
        List<String> startWords = List.of("M");
        data = """
                MAT
                MA
                M
                """;
        WordParser wordParser = new WordParser(mockUrl, startWords, 3);

        Map<String, List<String>> validWords = wordParser.findValidWords();
        assertEquals(1, validWords.size(), "Expect a map with single valid word");
        assertTrue(validWords.containsKey("MAT"), "Expect the word 'MAT' to be present");
    }

    @Test
    public void testFindValidWordsExpectIsFoundWhenStartWordNotExists() throws IOException {
        List<String> startWords = List.of("A");
        data = """
                MAT
                MA
                M
                """;
        WordParser wordParser = new WordParser(mockUrl, startWords, 3);

        Map<String, List<String>> validWords = wordParser.findValidWords();
        assertEquals(1, validWords.size(), "Expect one valid word");
    }

    @Test
    public void testFindValidWordsExpectMultipleValidWordsAreFound() throws IOException {
        List<String> startWords = List.of("M");

        WordParser wordParser = new WordParser(mockUrl, startWords, 3);

        Map<String, List<String>> validWords = wordParser.findValidWords();
        assertEquals(2, validWords.keySet().size(), "Expect the map to have 2 valid words");
        assertEquals(3, validWords.get("MAT").size(), "Expect the map to have one valid word MAT with 3 extensions");
        assertEquals(3, validWords.get("TAM").size(), "Expect the map to have one valid word TAM with 3 extensions");
    }
}