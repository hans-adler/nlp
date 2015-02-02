package com.github.hans_adler.nlp.la2;

import java.util.Iterator;

/**
 * Implementing classes must inherit from Entry<Scalar> or override the default
 * methods iterator() appropriately!
 * 
 * @author Hans Adler (johannes.aquila@gmail.com)
 *
 */
public interface Scalar extends Vector.RowVector, Vector.ColVector,
                                                    Iterable<Entry<Scalar>> {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public default Scalar seeTransposed() {
        return this;
    }

    @Override
    public default Dimension seeDimension() {
        return Dimension.ONE;
    }
    
    @Override
    public default Scalar see(int index) {
        assert index == 0;
        return this;
    }
    
    @Override
    public default Iterable<Entry<Scalar>> seeAll() {
        return this;
    }
    @Override
    public default Iterable<Entry<Scalar>> seeSparse() {
        return this;
    }
    @SuppressWarnings("unchecked")
    @Override
    public default Iterator<Entry<Scalar>> iterator() {
        return (Iterator<Entry<Scalar>>) this;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public default boolean matchesOrientation(Orientation orientation) {
        return true;
    }
    
    public abstract double getValue();
}
