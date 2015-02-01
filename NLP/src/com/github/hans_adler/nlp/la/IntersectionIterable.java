package com.github.hans_adler.nlp.la;

import java.util.Iterator;

public class IntersectionIterable <E extends AbstractEntry> implements Iterable<EntryPair<E>> {

    public final Iterable<E> one;
    public final Iterable<E> two;
    
    public IntersectionIterable(Iterable<E> one, Iterable<E> two) {
        this.one = one;
        this.two = two;
    }
    
    @Override
    public Iterator<EntryPair<E>> iterator() {
        return new IntersectionIterator<>(one.iterator(), two.iterator());
    }

}
