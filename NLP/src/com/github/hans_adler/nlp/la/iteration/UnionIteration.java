package com.github.hans_adler.nlp.la.iteration;

import java.util.Iterator;
import com.github.hans_adler.nlp.la.Axis;
import com.github.hans_adler.nlp.la.internal.VoS;

/**
 * Turns two Entry Iterator objects into a single EntryPair Iteration that
 * produces the following sequence (by increasing index):
 * 
 * For each index that appears in both iterations, there is an EntryPair
 * combining them. For each index that appears only in one iteration,
 * there is an EntryPair having the corresponding Entry and null as its
 * partner.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com)
 *
 * @param <T1>
 * @param <T2>
 */
public class UnionIteration<T1 extends VoS, T2 extends VoS>
                            implements Iteration<EntryPair<T1, T2>> {
    
    @SuppressWarnings("rawtypes")
    private static Entry EMPTY = new Entry();

    Iterator<Entry<T1>> i1;
    Iterator<Entry<T2>> i2;
    Entry<T1> buffer1;
    Entry<T2> buffer2;
    EntryPair<T1, T2> next;
    boolean nextLoaded = false;
    
    @SuppressWarnings("unchecked")
    public UnionIteration(Iterator<Entry<T1>> i1, Iterator<Entry<T2>> i2) {
        this.i1 = i1;
        this.i2 = i2;
        next = new EntryPair<T1, T2>();
        EMPTY.index = Axis.UNBOUNDED;
    }

    @Override
    public boolean hasNext() {
        // If this is a redundant call to hasNext(), return cached result.
        if (nextLoaded) return true;

        // Fill buffer to the extent possible.
        if (buffer1 == EMPTY && i1.hasNext()) buffer1 = i1.next();
        if (buffer2 == EMPTY && i2.hasNext()) buffer2 = i2.next();
        
        // No next element if buffer is still empty.
        if (buffer1 == EMPTY && buffer2 == EMPTY) return nextLoaded = false;
        
        // At this point at least one buffer element is not null, so we can
        // start loading the result of the next next() call.
        next.one = null;
        next.two = null;
        next.index = Axis.UNBOUNDED;
        if (buffer1.index <= buffer2.index) {
            next.index = buffer1.index;
            next.one = buffer1;
        }
        if (buffer2.index <= buffer1.index) {
            next.index = buffer2.index;
            next.two = buffer2;
        }
        return nextLoaded = true;
    }

    @Override
    public EntryPair<T1, T2> next() {
        if (!nextLoaded) hasNext();
        return next;
    }

}
