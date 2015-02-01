package com.github.hans_adler.nlp.la.vector;

import com.github.hans_adler.nlp.la.iteration.Entry;

/**
 * Interface for 0-based vectors of doubles.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 */
public interface Vector extends VectorView {

    /**
     * <p>Sets value at position index. For sparse vectors, if index did not
     * have a non-default value before, it will become non-default even if
     * value is actually the default value. (For sparse vectors with fixed
     * structure, an exception is thrown in this case.)</p>
     * 
     * <p>If index==ALL and value==0.0, all indices are affected and the
     * default value is set to 0.0.
     * If index==ALL and value!=0.0, behaviour is undefined.</p>
     * 
     * @param index
     * @param newValue
     */
	public abstract Vector set(int index, double newValue);

    /**
     * <p>Adds to value at position index. For sparse vectors, if index did not
     * have a non-default value before, it will become non-default even if
     * value is actually the default value. (For sparse vectors with fixed
     * structure, an exception is thrown in this case.)</p>
     * 
     * <p>If index==ALL, behaviour is undefined.</p>
     * 
     * @param index
     * @param summand
     */
	public default Vector add(int index, double summand) {
	    assert index >= 0;
	    set(index, get(index)+summand);
	    return this;
	}

    /**
     * <p>In-place addition of another vector.</p>
     * 
     * <p>The default implementation is appropriate if and only if it is
     * acceptable to successively change each element returned by an iterator
     * during the iteration. If not (seems unlikely), the implementation will
     * have to override this method. Implementations may also choose to
     * override this method for efficiency.</p>
     * 
     * @param other
     */
	public default Vector add(Vector other) {
        for (Entry entry: other) {
            add(entry.index, entry.value);
        }
        return this;
	}
	
    /**
     * <p>Multiplies value at position index with value.
     * For sparse vectors, if index did not
     * have a non-default value before, it will become non-default even if
     * value is actually the default value. (For sparse vectors with fixed
     * structure, an exception is thrown in this case.)</p>
     * 
     * <p>If index==ALL, behaviour is undefined.</p>
     * 
     * @param index
     * @param factor
     */
    public default Vector multiply(int index, double factor) {
        if (factor == 1.0) return this;
        if (index >= 0) {
            assert hasDimension(index);
            set(index, get(index)*factor);
        } else {
            assert index == ALL;
            for (Entry entry: this) {
                if (entry.value != 0.0) set(entry.index, entry.value*factor);
            }
        } 
        assert index >= 0;
        return this;
    }

    public default Vector multiplyDiagonal(VectorView other) {
        for (Entry entry: other) {
            multiply(entry.index, entry.value);
        }
        return this;
    }
    
}