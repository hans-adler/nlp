package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.implementation.Entry;

public interface MutableVector<A1 extends Axis> extends Vector<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract MutableScalar see(int i);
    
    /**
     * Although it seems impossible to express this with Java generics, the
     * Scalars returned by the iterator are actually Mutable and it is safe
     * to cast and use them accordingly (without keeping a reference, as
     * usual).
     */
    @Override
    public abstract Iterable<Entry<Scalar>> seeAll(boolean sparse);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract MutableVector<A1> setValue(int j, double value);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}
