package com.github.hans_adler.nlp.la.iteration;

import com.github.hans_adler.nlp.la.internal.VoS;

public class EntryPair<T1 extends VoS, T2 extends VoS> {
    public int index;
    public Entry<T1> one;
    public Entry<T2> two;
    
    public EntryPair() {
        one = new Entry<T1>();
        two = new Entry<T2>();
    }
    
    public Entry<T1> one() {
        return one;
    }
    public Entry<T2> two() {
        return two;
    }
    
    @Override
    public String toString() {
        return String.format("EntryPair(%d → %s, %s)", index,
                one == null ? "null" : one.content,
                two == null ? "null" : two.content);
    }
}
