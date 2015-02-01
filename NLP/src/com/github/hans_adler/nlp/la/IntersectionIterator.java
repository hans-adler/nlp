package com.github.hans_adler.nlp.la;

import java.util.Iterator;

public class IntersectionIterator<E extends AbstractEntry> implements Iterator<EntryPair<E>> {
    
    Iterator<E> i1;
    Iterator<E> i2;
    EntryPair<E> next;
    boolean nextLoaded = false;
    
    public IntersectionIterator(Iterator<E> i1, Iterator<E> i2) {
        this.i1 = i1;
        this.i2 = i2;
        next = new EntryPair<>();
    }

    @Override
    public boolean hasNext() {
        if (nextLoaded) return true;
        if (!i1.hasNext() || !i2.hasNext()) return nextLoaded = false;
        next.one = i1.next();
        next.two = i2.next();
        while (next.one.index != next.two.index) {
            if (next.one.index < next.two.index) {
                if (!i1.hasNext()) return nextLoaded = false;
                next.one = i1.next();
            } else {
                if (!i2.hasNext()) return nextLoaded = false;
                next.two = i2.next();
            }
        }
        assert next.one.index == next.two.index;
        next.index = next.one.index;
        return nextLoaded = true;
    }

    @Override
    public EntryPair<E> next() {
        if (!nextLoaded) hasNext();
        return next;
    }

}
