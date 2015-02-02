package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.implementation.Entry;
import com.github.hans_adler.nlp.la2.implementation.SparseVector;
import com.github.hans_adler.nlp.la2.internal.MoV;
import com.github.hans_adler.nlp.la2.internal.VoS;

public interface Vector<A1 extends Axis> extends MoV<A1>, VoS {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract Vector<A1> seeTransposed();

    @Override
    public abstract Scalar see(int i);
    
    @Override
    public abstract Iterable<Entry<Scalar>> seeAll(boolean sparse);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default double getValue(int j) {
        checkIndex(j);
        return see(j).getValue();
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * VECTOR FACTORY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public static <I extends Axis> MutableVector<I> create(I axis) {
        return new SparseVector<>(axis);
    }

}
