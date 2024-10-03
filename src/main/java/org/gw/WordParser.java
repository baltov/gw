package org.gw;

import org.gw.trie.Trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;


public class WordParser {

    private final Trie trie;
    private final int targetLength;
    private final List<String> startWords;

    public WordParser(URL url, List<String> startWords, int targetLength) throws IOException {
        this.targetLength = targetLength;
        this.startWords = startWords;
        this.trie = populateTrieFromURL(url);
    }

    public Map<String, List<String>> findValidWords() {
        Map<String, List<String>> validWords = new HashMap<>();
        for (String startWord : this.startWords) {
            //uncomment if start words should exist in list
            // change a test testFindValidWordsExpectIsFoundWhenStartWordNotExists
            if (isValidWord(startWord) /* && this.trie.search(startWord) */) {
                validWords.put(startWord, new ArrayList<>(Collections.singletonList(startWord)));
            }
        }

        for (int length = 2; length <= this.targetLength; length++) {
            Map<String, List<String>> newValidWords = new HashMap<>();
            validWords.forEach((word, wordList) -> {
                List<String> extensions = getOneLetterExtensions(word);
                extensions.forEach(ext -> {
                    List<String> newList = new ArrayList<>(wordList);
                    newList.add(ext);
                    newValidWords.put(ext, newList);
                });
            });
            validWords = newValidWords;
        }
        return validWords;
    }

    private Trie populateTrieFromURL(URL url) throws IOException {
        Trie trie = new Trie();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim().toUpperCase();
                if (isValidWord(trimmedLine)) {
                    trie.insert(trimmedLine);
                }
            }
        }
        return trie;
    }

    private boolean isValidWord(String word) {
        return word != null && !word.isEmpty() && word.length() <= this.targetLength;
    }

    private List<String> getOneLetterExtensions(String word) {
        List<String> extensions = new ArrayList<>();
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'A'; c <= 'Z'; c++) {
                String newWord = word.substring(0, i) + c + word.substring(i);
                if (this.trie.search(newWord)) {
                    extensions.add(newWord);
                }
            }
        }
        return extensions;
    }

}