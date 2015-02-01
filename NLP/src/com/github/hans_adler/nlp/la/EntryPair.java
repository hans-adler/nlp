package com.github.hans_adler.nlp.la;

public class EntryPair<E extends AbstractEntry> {
    public int index;
    public AbstractEntry one;
    public AbstractEntry two;
    
    public EntryPair() {
        one = two = new AbstractEntry();
    }
    
    @SuppressWarnings("unchecked")
    public E one() {
        return (E) one;
    }
    @SuppressWarnings("unchecked")
    public E two() {
        return (E) two;
    }
}
