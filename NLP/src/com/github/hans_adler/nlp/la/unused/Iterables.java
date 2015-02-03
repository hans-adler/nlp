package com.github.hans_adler.nlp.la.unused;

import com.github.hans_adler.nlp.la.implementation.Entry;
import com.github.hans_adler.nlp.la.implementation.EntryPair;


public final class Iterables {
    
    private Iterables() {
    }
    
    public static Iterable<EntryPair> intersect(
            Iterable<Entry> one, Iterable<Entry> two) {
        return new IntersectionIterable(one, two);
    }
    
}
