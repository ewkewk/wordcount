package com.example.wordcount.controller;

import com.example.wordcount.model.WordFrequency;
import com.example.wordcount.model.WordFrequencyCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordFrequencyCalculatorController {

    @Value("text")
    private String text;

    @Value("word")
    private String word;

    @Value("n")
    private String n;

    @GetMapping(value = "/calculate-highest-frequency-in-text/{text}")
    int calculateHighest(@PathVariable String text) {
        WordFrequencyCalculator wordFrequencyCalculator = new WordFrequencyCalculator();
        return wordFrequencyCalculator.calculateHighestFrequency(text);
    }

    @GetMapping(value = "/calculate-frequency-in-text-for-word/{text}/{word}")
    int calculateWord(@PathVariable String text, @PathVariable String word) {
        WordFrequencyCalculator wordFrequencyCalculator = new WordFrequencyCalculator();
        return wordFrequencyCalculator.calculateFrequencyForWord(text, word);
    }

    @GetMapping(value = "/calculate-frequency-in-text-for-most-frequent-n-words/{text}/{n}")
    List<WordFrequency> calculateHeadN(@PathVariable String text, @PathVariable int n) {
        WordFrequencyCalculator wordFrequencyCalculator = new WordFrequencyCalculator();
        return wordFrequencyCalculator.calculateMostFrequentNWords(text, n);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    String exceptionHandler(IllegalArgumentException illegalArgumentException) {
        // Return the error message.
        return illegalArgumentException.getMessage();
    }
}
