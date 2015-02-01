package hans_adler.linear_algebra.vector;

import hans_adler.linear_algebra.AbstractEntry;
import java.util.Iterator;

public class Entry extends AbstractEntry {
	
	public double value;
	
	public Entry() {
	    super(-1);
	    this.value = Double.NaN;
	}
	public Entry(int index, double value) {
		super();
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
