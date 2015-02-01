package com.github.hans_adler.nlp.la.iteration;

import java.util.Iterator;

public class IntersectionIterable implements Iterable<EntryPair> {

    public final Iterable<Entry> one;
    public final Iterable<Entry> two;
    
    public IntersectionIterable(Iterable<Entry> one, Iterable<Entry> two) {
        this.one = one;
        this.two = two;
    }
    
    @Override
    public Iterator<EntryPair> iterator() {
        return new IntersectionIterator(one.iterator(), two.iterator());
    }

}
