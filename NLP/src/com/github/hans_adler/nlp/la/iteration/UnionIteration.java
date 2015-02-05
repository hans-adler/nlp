package com.github.hans_adler.nlp.la.iteration;

import static com.github.hans_adler.nlp.la.iteration.Entry.EMPTY;
import java.util.Iterator;
import java.util.Objects;
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
             extends EntryPair<T1, T2>
             implements Iteration<EntryPair<T1, T2>> {
    
    Iterator<Entry<T1>> i1;
    Iterator<Entry<T2>> i2;
    @SuppressWarnings("unchecked")
    Entry<T1> buffer1 = EMPTY;
    @SuppressWarnings("unchecked")
    Entry<T2> buffer2 = EMPTY;
    boolean nextLoaded = false;
    
    public UnionIteration(Iterator<Entry<T1>> i1, Iterator<Entry<T2>> i2) {
        this.i1 = i1;
        this.i2 = i2;
        fillBuffers();
    }

    @Override
    public boolean hasNext() {
        assert buffer1 != null && buffer2 != null;
        loadNext();
        return nextLoaded;
    }

    @Override
    public EntryPair<T1, T2> next() {
        assert nextLoaded;
        if (!nextLoaded) hasNext();
        nextLoaded = false;
        loadNext();
        return this;
    }
    
    @SuppressWarnings("unchecked")
    private void loadNext() {
        if (nextLoaded) return;
        
        // No next element if buffers are both still empty.
        if (buffer1 == EMPTY && buffer2 == EMPTY) return;
        
        // At this point at least one buffer element is not null, so we can
        // start loading the result of the next next() call.
        one = null;
        two = null;
        index = Axis.UNBOUNDED;
        final int diff = Long.signum(buffer1.index - buffer2.index);
        if (diff <= 0) { // buffer1.index <= buffer2.index
            index = buffer1.index;
            one = buffer1;
            buffer1 = EMPTY;
            assert one != null;
        }
        if (diff >= 0) { // buffer2.index <= buffer1.index held earlier
            index = buffer2.index;
            two = buffer2;
            buffer2 = EMPTY;
            assert two != null;
        }
        assert (one == null || two == null) ? true : (index == one.index && index == two.index);  
        fillBuffers();
        nextLoaded = true;
    }
    
    private void fillBuffers() {
        // Buffers must be re-filled before next() returns.
        // Otherwise an individual iterator may return the current index
        // again!
        if (buffer1 == EMPTY && i1.hasNext()) buffer1 = i1.next();
        Objects.requireNonNull(buffer1);
        if (buffer2 == EMPTY && i2.hasNext()) buffer2 = i2.next();
        Objects.requireNonNull(buffer2);
    }
    
    @Override
    public String toString() {
        return "UnionIteration/" + super.toString();
    }

}
