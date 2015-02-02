package com.github.hans_adler.nlp.la2.implementation;

import com.github.hans_adler.nlp.la2.Axis;
import com.github.hans_adler.nlp.la2.MutableScalar;
import com.github.hans_adler.nlp.la2.MutableVector;
import com.github.hans_adler.nlp.la2.Scalar;

public class SafeTableRow<A1 extends Axis> implements MutableVector<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * FIELDS / CONSTRUCTORS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    private final SparseMatrix<A1, ?> wrappeeMatrix;
    private final MutableVector<A1> wrappeeRow;
    private final int wrappeeRowIndex;
    
    private final SparseMatrix<?, A1> targetMatrix;
    private final int targetColumnIndex;
    
    public SafeTableRow(SparseMatrix<A1, ?> wrappeeMatrix, MutableVector<A1> wrappeeRow,
            int wrappeeRowIndex) {
        this.wrappeeMatrix = wrappeeMatrix;
        this.wrappeeRow = wrappeeRow;
        this.wrappeeRowIndex = wrappeeRowIndex;
        
        this.targetMatrix = wrappeeMatrix.transposed;
        this.targetColumnIndex = wrappeeRowIndex;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public MutableScalar see(int i) {
        return see(i);
    }

    @Override
    public Iterable<Entry<Scalar>> seeAll(boolean sparse) {
        throw new UnsupportedOperationException();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public A1 getAxis1() {
        return wrappeeRow.getAxis1();
    }

    @Override
    public double getValue(int j) {
        return wrappeeRow.getValue(j);
    }
    
    @Override
    public double getDefaultValue() {
        return wrappeeRow.getDefaultValue();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public MutableVector<A1> setValue(int j, double value) {
        wrappeeRow.setValue(j, value);
        targetMatrix.createRowVector(j).setValue(targetColumnIndex, value);
        return this;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public void checkIndex(int i) {
        getAxis1().checkIndex(i);
    }
    
}
