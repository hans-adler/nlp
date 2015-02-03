package com.github.hans_adler.nlp.la;

import com.github.hans_adler.nlp.la.implementation.Entry;
import com.github.hans_adler.nlp.la.implementation.SparseVector;
import com.github.hans_adler.nlp.la.internal.MoV;
import com.github.hans_adler.nlp.la.internal.VoS;

public interface Vector<A1 extends Axis> extends MoV<A1>, VoS {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * VIEWERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract Scalar view(int i);
    
    @Override
    public abstract Iterable<Entry<Scalar>> viewAll(boolean sparse);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default double getValue(int j) {
        checkIndex(j);
        return view(j).getValue();
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * FACTORY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public static <I extends Axis> MutableVector<I> create(I axis) {
        return new SparseVector<>(axis);
    }

}
