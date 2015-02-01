package com.github.hans_adler.nlp.la.iteration;


public class Entry {
    
    public int index;
    public double value;
    
    public Entry() {
        this.index = -1;
        this.value = Double.NaN;
    }
    public Entry(int index, double value) {
        this.index = index;
        this.value = value;
    }
    
}
