package com.example.wordcount.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyCalculatorTest {

    WordFrequencyCalculator wordFrequencyCalculator = new WordFrequencyCalculator();

    @Test
    void calculateHighestFrequency() {
        assertEquals(7, wordFrequencyCalculator.calculateHighestFrequency("a b c The THe THE tHE thE the tHe an An AN aN a"));
    }

    @Test
    void calculateHighestFrequency_EmptyText() {
        short returnValue = -1;
        try{
            wordFrequencyCalculator.calculateHighestFrequency("");
        } catch (IllegalArgumentException iae) {
            returnValue++;
        }
        assertEquals(0, returnValue);
    }

    @Test
    void calculateHighestFrequency_TextWithNewLines() {
        assertEquals(28, wordFrequencyCalculator.calculateHighestFrequency("What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n" +
            "Why do we use it?\n" +
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
            "\n" +
            "\n" +
            "Where does it come from?\n" +
            "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
            "\n" +
            "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
            "\n" +
            "Where can I get some?\n" +
            "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."));
    }

    @Test
    void calculateFrequencyForWord_TestNonInitialEntry_NonDefaultFrequency() {
        // Test case wit a non initial entry (not 'a') with a non default (not '1') frequency value.
        assertEquals(4, wordFrequencyCalculator.calculateFrequencyForWord("a b c The THe THE tHE thE the tHe an An AN aN a" , "an"));
    }

    @Test
    void calculateFrequencyForWord_TestCaseInsensitiveEntries() {
        // Test case for case insensitivity.
        assertEquals(2, wordFrequencyCalculator.calculateFrequencyForWord("a b c The THe THE tHE thE the tHe an An AN aN a" , "A"));
    }

    @Test
    void calculateFrequencyForWord_TestAbsentEntry() {
        // Test case for an absent word.
        assertEquals(0, wordFrequencyCalculator.calculateFrequencyForWord("a b c The THe THE tHE thE the tHe an An AN aN a" , "bla"));
    }

    @Test
    void calculateFrequencyForWord_TestSpecialCharactersInText() {
        // Test case for special characters (non alphabetical) in text.
        // Note that special characters as infix in a word, divide the word into new words.
        assertEquals(6, wordFrequencyCalculator.calculateFrequencyForWord("$#@ a b c The ()THe THE tH^&E thE the tHe an78 An AN aN a" , "tHe"));
    }

    @Test
    void calculateFrequencyForWord_TestSpecialCharactersInWord() {
        // Test case for invalid characters in word.
        short returnValue = -1;
        try{
            wordFrequencyCalculator.calculateFrequencyForWord("$#@ a b c The ()THe THE tH^&E thE the tHe an78 An AN aN a" , "an78");
        } catch (IllegalArgumentException iae) {
            returnValue++;
        }
        assertEquals(0, returnValue);
    }

    @Test
    void calculateFrequencyForWord_TestEmptyWord() {
        // Test case for empty word.
        short returnValue = -1;
        try{
            wordFrequencyCalculator.calculateFrequencyForWord("a b c The THe THE tHE thE the tHe an An AN aN a" , "");
        } catch (IllegalArgumentException iae) {
            returnValue++;
        }
        assertEquals(0, returnValue);
    }

    @Test
    void calculateFrequencyForWord_TestMultipleWords() {
        // Test case for multiple words.
        short returnValue = -1;
        try{
            wordFrequencyCalculator.calculateFrequencyForWord("a b c The THe THE tHE thE the tHe an An AN aN a" , "an the");
        } catch (IllegalArgumentException iae) {
            returnValue++;
        }
        assertEquals(0, returnValue);
    }

    @Test
    void calculateMostFrequentNWords_WithNZero() {
        // Test case for n=0.
        short returnValue = -1;
        try{
            wordFrequencyCalculator.calculateMostFrequentNWords("a b c The THe THE tHE thE the tHe an An AN aN a" , 0);
        } catch (IllegalArgumentException iae){
            returnValue++;
        }
        assertEquals(0, returnValue);
    }

    @Test
    void calculateMostFrequentNWords_WithNGreaterThanWordList() {
        // Test case for n=0.
        short returnValue = -1;
        try{
            wordFrequencyCalculator.calculateMostFrequentNWords("a b c The THe THE tHE thE the tHe an An AN aN a" , 6);
        } catch (IllegalArgumentException iae){
            returnValue++;
        }
        assertEquals(0, returnValue);
    }

    @Test
    void calculateMostFrequentNWords_testFrequencyOrder() {
        List<WordFrequency> wordFrequencies = wordFrequencyCalculator.calculateMostFrequentNWords("a a a b b b b c d d", 3);
        assertEquals(wordFrequencies.get(0).getWord(), "b");
        assertEquals(wordFrequencies.get(1).getWord(), "a");
        assertEquals(wordFrequencies.get(2).getWord(), "d");
    }

    @Test
    void calculateMostFrequentNWords_testWordOrder() {
        List<WordFrequency> wordFrequencies = wordFrequencyCalculator.calculateMostFrequentNWords("d d a a c c b b ", 3);
        assertEquals(wordFrequencies.get(0).getWord(), "a");
        assertEquals(wordFrequencies.get(1).getWord(), "b");
        assertEquals(wordFrequencies.get(2).getWord(), "c");
    }

    @Test
    void calculateMostFrequentNWords_testBothFrequencyAndWordOrder() {
        List<WordFrequency> wordFrequencies = wordFrequencyCalculator.calculateMostFrequentNWords("w zz zz zz d x d a a c y c b b ", 3);
        assertEquals(wordFrequencies.get(0).getWord(), "zz");
        assertEquals(wordFrequencies.get(1).getWord(), "a");
        assertEquals(wordFrequencies.get(2).getWord(), "b");
    }
}