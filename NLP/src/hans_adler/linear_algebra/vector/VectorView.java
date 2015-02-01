package hans_adler.linear_algebra.vector;

import static hans_adler.linear_algebra.vector.Entry.DoubleEntryIterator;
import hans_adler.linear_algebra.EntryPair;
import hans_adler.linear_algebra.Iterables;
import hans_adler.linear_algebra.matrix.DiagonalMatrixView;
import hans_adler.linear_algebra.matrix.MatrixView;
import java.util.Iterator;

/**
 * Interface for 0-based read-only vectors of doubles.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 */
public interface VectorView  extends Iterable<Entry> {

	public static int ALL = -1;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Iterable<Entry> default implementation
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * <p>An iterator over all non-default (index,value) pairs for this vector.
     * The DoubleEntry object may be recycled by the iterator and only its
     * content changed between calls to next().</p>
     * 
     * <p>The default implementation is appropriate for dense vectors.
     * It is incorrect for unbounded vectors and generally inappropriate
     * for other sparse vectors.</p>
     * 
     * @return An iterator.
     */
    @Override
    public default Iterator<Entry> iterator() {
        return new DoubleEntryIterator(this);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Additional methods
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Returns the value of the vector that corresponds to index.
	 * 
	 * @param index
	 * @return The value at index.
	 */
	public abstract double get(int index);
	
	/**
	 * <p>Adds up all entries in the vector.</p>
	 * 
	 * <p>Except for numerical stability issues and possibly slow speed of
	 * access via the iterator, default implementation should be adequate in
	 * all cases.</p>
	 * @param individualExponent TODO
	 * @param sumExponent TODO
	 * 
	 * @return Sum of all entries.
	 */
	public default double getSum(double individualExponent, double sumExponent) {
        double result = 0.0;
        int count = 0;
	    for (Entry entry: this) {
	        result += Math.pow(entry.value, individualExponent);
	        count++;
	    }
	    int skipped = getDimension() - count;
	    if (skipped > 0) {
	        result += Math.pow(getDefaultValue(), individualExponent) * skipped;
	    }
	    return Math.pow(result, sumExponent);
	}
	
	/**
	 * The value that should be assumed for entries not returned by an
	 * iterator.
	 * 
	 * @return The default value of this vector.
	 */
	public default double getDefaultValue() {
	    return 0.0;
	}
	
	/**
	 * Checks if argument d matches the dimension of this vector view.
	 * This is the case if they are equal or if one of them is ALL.
	 *  
	 * @param d
	 * @return True if d equals the dimension of this vector view, or if d or
	 * this vector view equals ALL.
	 */
	public default boolean hasDimension(int d) {
	    if (d < -1) return false;
	    if (d == ALL) return true;
	    if (getDimension() == ALL) return true;
	    return d == getDimension();
	}
	
	public default boolean fitsDimension(int index) {
        if (index < -1) return false;
        if (index == ALL) return true;
        if (getDimension() == ALL) return true;
        return index < getDimension();
	}
	
	/**
	 * The dimension of this vector. For unbounded vectors this is the constant
	 * ALL.
	 * 
	 * @return The dimension of this vector.
	 */
	public default int getDimension() {
	    return ALL;
	}
	
	/**
	 * Computes the scalar product of this vector with another vector.
	 * The default implementation is appropriate for all subclasses. 
	 * 
	 * @param other
	 * @return Scalar product of this and other.
	 */
	public default double scalarProduct(VectorView other) {
	    double result = 0.0;
	    for (EntryPair<Entry> pair: Iterables.intersect(this, other)) {
	        result += pair.one().value * pair.two().value;
	    }
	    return result;
	}
	
    public default MatrixView asDiagonalMatrix() {
        return new DiagonalMatrixView(this);
    }
    
    public default MatrixView asColMatrix() {
        throw new UnsupportedOperationException();
    }

    public default MatrixView asRowMatrix() {
        throw new UnsupportedOperationException();
    }
    
}
