package com.github.hans_adler.nlp.la.iteration;


public class EntryPair {
    public int index;
    public Entry one;
    public Entry two;
    
    public EntryPair() {
        one = two = new Entry();
    }
    
    public Entry one() {
        return one;
    }
    public Entry two() {
        return two;
    }
}
