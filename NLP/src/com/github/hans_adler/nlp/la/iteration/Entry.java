package com.github.hans_adler.nlp.la.iteration;

import com.github.hans_adler.nlp.la.internal.VoS;
import com.github.hans_adler.nlp.vector.Axis;

public class Entry<T extends VoS> {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Entry EMPTY = new Entry() {
        {
            index = Axis.UNBOUNDED;
        }
        @Override
        public String toString() {
            return "Entry(EMPTY)";
        }
    };
    
    public int index;
    public T content;
    
    @Override
    public String toString() {
        return String.format("Entry(%d → %s)", index, content);
    }
}