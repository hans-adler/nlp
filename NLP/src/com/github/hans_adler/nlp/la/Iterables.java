package com.github.hans_adler.nlp.la;

public final class Iterables {
    
    private Iterables() {
    }
    
    public static <E extends AbstractEntry> Iterable<EntryPair<E>> intersect(
            Iterable<E> one, Iterable<E> two) {
        return new IntersectionIterable<E>(one, two);
    }
    
}
