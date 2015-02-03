package com.github.hans_adler.nlp.la.iteration;

import java.util.Iterator;
import com.github.hans_adler.nlp.la.internal.VoS;

public class IntersectionIteration<T1 extends VoS, T2 extends VoS>
                            implements Iteration<EntryPair<T1, T2>> {
    
    Iterator<Entry<T1>> i1;
    Iterator<Entry<T2>> i2;
    EntryPair<T1, T2> next;
    boolean nextLoaded = false;
    
    public IntersectionIteration(Iterator<Entry<T1>> i1, Iterator<Entry<T2>> i2) {
        this.i1 = i1;
        this.i2 = i2;
        next = new EntryPair<T1, T2>();
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
    public EntryPair<T1, T2> next() {
        if (!nextLoaded) hasNext();
        return next;
    }

}
