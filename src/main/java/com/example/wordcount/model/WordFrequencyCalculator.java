package com.example.wordcount.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFrequencyCalculator implements WordFrequencyAnalyzer {

    String REGEX = "[a-zA-Z]*"; // A (too) basic regex to identify words with.

    @Override
    public int calculateHighestFrequency(String text) {
        // Fill word frequency list.
        List<WordFrequency> textWordFrequencyList = fillWordFrequencyList(text, REGEX, new ArrayList<>());

        // Check if the parsed text is there.
        if (textWordFrequencyList.isEmpty()) {
            throw new IllegalArgumentException("The text should contain at least one word.");
        }

        // Sort the data, most to least frequent.
        textWordFrequencyList
                .sort(Comparator.comparing(WordFrequency::getFrequency)
                        .reversed());

        // Return the value of the first element.
        return textWordFrequencyList.get(0).getFrequency();
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        // Normalize and validate argument word to find frequency for.
        word = normalizeAndValidateWordToFindFrequencyFor(word);

        // Fill word frequency list.
        List<WordFrequency> textWordFrequencyList = fillWordFrequencyList(text, REGEX, new ArrayList<>());

        // Check if the parsed text is there.
        if (textWordFrequencyList.isEmpty()) {
            throw new IllegalArgumentException("The text should contain at least one word.");
        }

        // Return the frequency of a Word.
        int index = findIndexOfWord(textWordFrequencyList, word);
        return index > -1 ? textWordFrequencyList.get(index).getFrequency() : 0;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        // Check if 'n' is a non zero positive number.
        if (n < 1) {
            throw new IllegalArgumentException("The number 'n' should be a non zero positive value. The value provided is '" + n + "'.");
        }

        // Fill word frequency list.
        List<WordFrequency> textWordFrequencyList = fillWordFrequencyList(text, REGEX, new ArrayList<>());

        // Check if the textWordFrequencyList contains at least 'n' entries.
        if (textWordFrequencyList.size() < n) {
            throw new IllegalArgumentException("The number 'n' should be at most the number of different words in the text. The current number of different words is '" + textWordFrequencyList.size() + "'.");
        }

        // Sort the data, most to least frequent, and if needed alphabetically per frequency.
        textWordFrequencyList
                // Sort highest frequency to lowest.
                .sort(Comparator.comparing(WordFrequency::getFrequency).reversed()
                        // For equal frequencies sort alphabetically.
                        .thenComparing(WordFrequency::getWord));

        // Return a sublist with the requested 'n' entries.
        return new ArrayList<>(textWordFrequencyList.subList(0, n));
    }

    /**
     * Searches the {@param wordFrequencyList} for the {@param wordToAdd}.
     *
     * @param wordFrequencyList List where the filling is in progress.
     * @param wordToAdd         String containing the word to add.
     * @return the number of the index if found, or -1 otherwise.
     */
    private int findIndexOfWord(List<WordFrequency> wordFrequencyList, String wordToAdd) {
        int index = 0;
        // Browse the list.
        for (WordFrequency wordFrequency : wordFrequencyList) {
            if (wordFrequency.getWord().equals(wordToAdd)) {
                // Return the index of the word found.
                return index;
            }
            index++;
        }
        // Return the first available negative value as a 'not found'.
        return -1;
    }

    /**
     * Ensures that a word is filled, in lowercase, is a single word only, composed by regular a-z characters.
     *
     * @param word the word String provided
     * @return a normalized and validated word String.
     * @throws IllegalArgumentException if the word cannot be validated.
     */
    private String normalizeAndValidateWordToFindFrequencyFor(String word) {
        if (word.isBlank()) {
            throw new IllegalArgumentException("The word should be non blank.");
        }

        // Ensure the word to search for is in lowercase (the List will also be filled with lowercase words).
        word = word.toLowerCase();

        // Check if word to find frequency for is a single word.
        String regex = "[a-z]*"; // A (too) basic regex to identify words with.
        List<WordFrequency> wordWordFrequencyList = fillWordFrequencyList(word, regex, new ArrayList<>());
        if (wordWordFrequencyList.size() != 1 || wordWordFrequencyList.get(0).getFrequency() > 1) {
            throw new IllegalArgumentException("The word to calculate frequency for argument should be filled with a single word.");
        }

        // Check if there are invalid characters in word to find frequency for.
        regex = "((?![a-zA-Z]).)*"; // Regex to find invalid characters with. Inversion of "[a-zA-Z]*".
        List<WordFrequency> invalidCharacterWordList = fillWordFrequencyList(word, regex, new ArrayList<>());
        if (!invalidCharacterWordList.isEmpty()) {
            throw new IllegalArgumentException("Invalid characters in word to calculate frequency for argument. First matched invalid character(s) '" + invalidCharacterWordList.get(0).getWord() + "'.");
        }

        // Return the normalized and validated word argument.
        return word;
    }

    private List<WordFrequency> fillWordFrequencyList(String text, String regex, List<WordFrequency> wordFrequencyList) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        // Find the 'words' within a text line.
        while (matcher.find()) {
            // Deal with empty Strings.
            if (matcher.group().isEmpty()) {
                continue;
            }

            // Add the word to the word frequency list.
            String wordToAdd = matcher.group().toLowerCase();
            // See if the word is present.
            int index = findIndexOfWord(wordFrequencyList, wordToAdd);
            if (index > -1) {
                // Raise the frequency.
                Word wordInList = (Word) wordFrequencyList.get(index);
                wordFrequencyList.set(index, new Word(wordInList.getWord(), wordInList.getFrequency() + 1));
            } else {
                // Insert a new entry.
                wordFrequencyList.add(new Word(wordToAdd));
            }
        }
        return wordFrequencyList;
    }
}
