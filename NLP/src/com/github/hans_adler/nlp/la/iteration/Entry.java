package com.github.hans_adler.nlp.la.iteration;

import java.util.Iterator;
import com.github.hans_adler.nlp.la.vector.VectorView;

public class Entry {
	
    public int index;
	public double value;
	
	public Entry() {
	    this(-1, Double.NaN);
	}
	public Entry(int index, double value) {
		this.index = index;
		this.value = value;
	}
	
	public static class DoubleEntryIterator implements Iterator<Entry> {

        final int dimension;
	    final VectorView vector;
	    final Entry entry;
	    
	    public DoubleEntryIterator(VectorView vector, int dimension) {
	        assert dimension >= 0;
            this.dimension = dimension;
	        this.vector = vector;
	        this.entry = new Entry(-1, Double.NaN);
	    }
	    public DoubleEntryIterator(VectorView vector) {
	        this(vector, vector.getDimension());
	    }
	    
        @Override
        public boolean hasNext() {
            return entry.index < dimension;
        }

        @Override
        public Entry next() {
            assert entry.index+1 < dimension;
            entry.value = vector.get(++entry.index);
            return entry;
        }
	    
	}
	
}
