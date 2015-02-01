package com.github.hans_adler.nlp.la.iteration;


public final class Iterables {
    
    private Iterables() {
    }
    
    public static Iterable<EntryPair> intersect(
            Iterable<Entry> one, Iterable<Entry> two) {
        return new IntersectionIterable(one, two);
    }
    
}
