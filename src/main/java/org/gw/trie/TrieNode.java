package org.gw.trie;


import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;

    public void addChild(Character c, TrieNode node) {
        children.putIfAbsent(c, node);
    }
    public TrieNode getChild(Character c) {
        return children.get(c);
    }
    public boolean hasChild(Character c) {
        return children.containsKey(c);
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }
    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}
