package com.example.wordcount.model;

public class Word implements WordFrequency {

    private String word;
    private int frequency;

    public Word(String word) {
        this.word = word;
        this.frequency = 1;
    }

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    /**
     * Raises the frequency with one.
     */
    public void addOne() {
        this.frequency = this.getFrequency() + 1;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }
}
