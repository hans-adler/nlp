package com.github.hans_adler.nlp.la2.core;

import com.github.hans_adler.nlp.la2.internal.MoV;
import com.github.hans_adler.nlp.la2.unused.DenseVectorIteration;

public interface Matrix<A1 extends Axis, A2 extends Axis> extends MoV<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract Matrix<A2, A1> seeTransposed();
    
    @Override
    public abstract Vector<A2> see(int i);
    
    @Override
    @SuppressWarnings("rawtypes")
    public default Iterable seeAll(boolean sparse) {
        return new DenseVectorIteration<A1, A2>(this);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default double getValue(int i, int j) {
        checkIndex(i);
        return see(i).getValue(j);
    }
    
    @Override
    public default double getDefaultValue() {
        return 0.0;
    }
    
    public abstract A2 getAxis2();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}
