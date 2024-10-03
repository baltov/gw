package org.gw;

import org.gw.trie.Trie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrieTest {

    @Test
    public void testInsert() {
        Trie trie = new Trie();
        trie.insert("hello");
        Assertions.assertTrue(trie.search("hello"));
    }

    @Test
    public void testInsert_noWord() {
        Trie trie = new Trie();
        trie.insert("world");
        Assertions.assertFalse(trie.search("hello"));
    }

    @Test
    public void testInsert_prefix() {
        Trie trie = new Trie();
        trie.insert("programming");
        Assertions.assertFalse(trie.search("program"));
    }

    @Test
    public void testSearch_existing() {
        Trie trie = new Trie();
        trie.insert("world");
        Assertions.assertTrue(trie.search("world"));
    }

    @Test
    public void testSearch_notExisting() {
        Trie trie = new Trie();
        trie.insert("hello");
        Assertions.assertFalse(trie.search("world"));
    }

    @Test
    public void testSearch_empty() {
        Trie trie = new Trie();
        Assertions.assertFalse(trie.search(""));
    }

}