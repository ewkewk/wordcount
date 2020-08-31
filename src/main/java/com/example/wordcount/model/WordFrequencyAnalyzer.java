package com.example.wordcount.model;

import java.util.List;

public interface WordFrequencyAnalyzer {

    /**
     * Finds the number of times the most frequent word (defined by pattern a-zA-Z) occurs within a text.
     *
     * @param text String containing the text to analyze.
     * @return an int containing the number of times the most frequent word (defined by pattern a-zA-Z) occurs within the {@param text}.
     */
    int calculateHighestFrequency(String text);

    /**
     * Finds the number of times a given word occurs within a given text.
     *
     * @param text String containing the text to analyze.
     * @param word String containing the word to find the frequency for.
     * @return an integer containing the number of times the {@param word} occurs within the {@param text}.
     */
    int calculateFrequencyForWord(String text, String word);

    /**
     * Finds the head with a given size of a list of the top frequent words in a given text.
     *
     * @param text String containing the text to analyze.
     * @param n    integer containing the number of entries te return.
     * @return a {@link List<WordFrequency>} containing the number of entries requested for.
     */
    List<WordFrequency> calculateMostFrequentNWords(String text, int n);
}
