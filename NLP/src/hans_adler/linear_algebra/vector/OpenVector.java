package hans_adler.linear_algebra.vector;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Sparse vector implementation that should be particularly efficient for
 * vectors that tend to become increasingly sparse as the index increases.
 * 
 * Due to sparseness, the dimension of the vector space can be left open.
 * Arbitrary indices from 0 (inclusive) to Integer.MAX_VALUE (inclusive) are
 * allowed.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 */
public class OpenVector implements Vector {
	//	 Implementation notes:
	//	 To avoid confusion, internal indices in the arrays storing the actual
	//	 information are not referred to as indices but as keys.
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
	* Fields and constructors
	\* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	protected static int[] 	INITIAL_INDEX_ARRAY = new int[0]; 
	protected static double[] INITIAL_VALUE_ARRAY = new double[0];
	
	protected int start;
	protected int ceiling;
	protected int[] 	 indexArray;
	protected double[] valueArray;
	protected boolean ownsArray;
	protected int dimension;

	public OpenVector() {
		start   = 0;
		ceiling = 0;
		indexArray = INITIAL_INDEX_ARRAY;
		valueArray = INITIAL_VALUE_ARRAY;
		ownsArray = true;
		dimension = ALL;
	}
	public OpenVector(int dimension) {
		this();
		this.dimension = dimension;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
	* VectorView implementation
	\* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * @see VectorView#get(int)
     */
    @Override
    public double get(int index) {
        int key = key(index);
        if (key < 0) return getDefaultValue();
        return valueArray[key];
    }

    @Override
    public double getSum(double individualExponent, double sumExponent) {
        // Slightly more efficient than default implementation.
        double result = 0.0;
        int count = 0;
        for (int index = start; index < ceiling; index++) {
            result += valueArray[index];
            count++;
        }
        result += getDefaultValue() * (getDimension() - count);
        return result;
    }
    
    @Override
    public Iterator<Entry> iterator() {
        return new SparseIterator();
    }

    @Override
    public boolean hasDimension(int d) {
        return dimension == d || dimension < 0 || d < 0;
    }
    @Override
    public int getDimension() {
        return dimension;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Vector implementation
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * @see Vector#set(int, double)
	 */
	@Override
	public OpenVector set(int index, double newValue) {
        assert fitsDimension(index);
	    if (index >= 0) {
            int key = obtainKey(index);
            valueArray[key] = newValue;
	    } else {
            if (newValue != getDefaultValue()) throw new RuntimeException();
            Arrays.fill(valueArray, start, ceiling, newValue);
	    }
	    return this;
	}
	
	/**
	 * @see Vector#add(int, double)
	 */
	@Override
	public OpenVector add(int index, double summand) {
	    assert fitsDimension(index);
	    assert index >= 0;
	    int key = obtainKey(index);
	    valueArray[key] += summand;
	    return this;
	}
	
    @Override
    public OpenVector multiply(int index, double factor) {
        assert fitsDimension(index);
        if (factor == 1.0) return this;
        if (index >= 0) {
            int key = obtainKey(index);
            valueArray[key] *= factor;
        } else {
            for (int key = start; key < ceiling; key++) {
                valueArray[key] *= factor;
            }
        } 
        assert index >= 0;
        return this;
    }

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
	* Private methods
	\* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Returns the key corresponding to the index, or -1 if there is none.
	 * 
	 * @param index
	 * @return
	 */
	private int key(int index) {
		if (ceiling == start) return -1;
		int key = start + index;
		if (key >= ceiling) key = ceiling-1;
		int i = indexArray[key];
		while (i > index) {
			key--;
			i = indexArray[key];
		}
		if (i < index) return -1;
		return key;
	}

	/**
	 * <p>Returns the key corresponding to the index. If no such key exists,
	 * the structure is updated.</p>
	 * 
	 * <p><em>Note:</em> Since this method sometimes changes indexArray and
	 * valueArray (because they have to grow), it is unsafe to call
	 * indexArray[obtainKey[index]] or valueArray[obtainKey[index]].
	 * Instead, store the key in a variable and <em>then</em> index the array
	 * with that variable.</p> 
	 * 
	 * @param index
	 * @return
	 */
	private int obtainKey(int index) {
		// Handling the special case when array not yet allocated.
		if (ceiling == start) {
			if (!ownsArray) throw new RuntimeException();
			assert start == 0;
			indexArray = new int[20];
			valueArray = new double[20];
			indexArray[0] = 0;
			valueArray[0] = 0.0;
			return 0;
		}
		
		// Looking for the key and returning it if successful.
		int key = start + index;
		if (key >= ceiling) key = ceiling-1;
		int i = indexArray[key];
		while (i > index) {
			key--;
			i = indexArray[key];
		}
		if (i == index) return key;
		
		// Key not found.
		assert i < index;
		key++;
		if (!ownsArray) throw new RuntimeException();
		assert start == 0;
		if (ceiling == indexArray.length) {
			int newCapacity = indexArray.length+32;
			indexArray = Arrays.copyOf(indexArray, newCapacity);
			valueArray = Arrays.copyOf(valueArray, newCapacity);
		}
		System.arraycopy(indexArray, key, indexArray, key+1, ceiling-key);
		System.arraycopy(valueArray, key, valueArray, key+1, ceiling-key);
		indexArray[key] = index;
		valueArray[key] = 0.0;
		return key;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
	* Inner class for entry iterator.
	\* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	protected class SparseIterator implements Iterator<Entry> {
		
		protected final Entry entry = new Entry();
		protected int k = start-1;
		
		public SparseIterator() {
			entry.index = -1;
			entry.value = Double.NaN;
		}
		
		@Override
		public boolean hasNext() {
			return start+1 < ceiling;
		}

		@Override
		public Entry next() {
			if ((start+1) >= ceiling) throw new NoSuchElementException();
			entry.index = indexArray[++k];
			entry.value = get(entry.index);
			return entry;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
