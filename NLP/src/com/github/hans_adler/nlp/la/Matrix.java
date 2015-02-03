package com.github.hans_adler.nlp.la;

import com.github.hans_adler.nlp.la.implementation.SparseMatrix;
import com.github.hans_adler.nlp.la.internal.MoV;
import com.github.hans_adler.nlp.la.unused.DenseVectorIteration;

/**
 * General method naming scheme
 * 
 * Properties:
 * 
 * Get/set as usual. Unusually, most setters return a reference to the object
 * itself to permit chaining.
 * 
 * Peek/poke are internal variants of get/set that do not check their arguments
 * and will not change any (substantial) object state if they can't find
 * something, returning invalid values (index -1, value NaN) instead.
 * 
 * Aspects (such as rows of a matrix, or a transposed matrix):
 * 
 * view - read-only (in general not enforced); state undefined from the next
 *        write operation to the owning object or any of its views.
 *        Maximally efficient aspect.
 * take - mutable; state undefined from the next write operation to the
 *        owning object or any of its aspects.
 * keep - mutable, keeping a long-term connection to the owning object.
 * copy - mutable copy of the current state of the object; changes won't be
 *        written back.
 *        
 * Some special cases: viewAll, takeAll, keepAll, copyAll are iterables for
 * the object's constituents as returned by view(i), take(i), keep(i), copy(i).
 * 
 * view(), take(), keep() without arguments make no sense, hence don't exist.
 * But copy() makes sense and returns a deep copy of the object.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com)
 *
 * @param <A1>
 * @param <A2>
 */

public interface Matrix<A1 extends Axis, A2 extends Axis> extends MoV<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * VIEWERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract Matrix<A2, A1> viewT();
    
    @Override
    public abstract Vector<A2> view(int i);
    
    @Override
    @SuppressWarnings("rawtypes")
    public default Iterable viewAll(boolean sparse) {
        return new DenseVectorIteration<A1, A2>(this);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default double getValue(int i, int j) {
        checkIndex(i);
        return view(i).getValue(j);
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
    * FACTORY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public static <I extends Axis, J extends Axis> MutableMatrix<I, J> create(I vertical, J horizontal) {
        return new SparseMatrix<I, J>(vertical, horizontal);
    }

}
