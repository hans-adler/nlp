package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.implementation.SparseMatrix;
import com.github.hans_adler.nlp.la2.internal.MoV;
import com.github.hans_adler.nlp.la2.unused.DenseVectorIteration;

public interface Matrix<A1 extends Axis, A2 extends Axis> extends MoV<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
    * NON-CHAINING ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public default void checkIndex(int i, int j) {
        getAxis1().checkIndex(i);
        getAxis2().checkIndex(j);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * MATRIX FACTORY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public static <I extends Axis, J extends Axis> MutableMatrix<I, J> create(I vertical, J horizontal) {
        return new SparseMatrix<I, J>(vertical, horizontal);
    }

}
