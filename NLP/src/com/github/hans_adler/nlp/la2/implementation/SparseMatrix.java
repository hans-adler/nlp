package com.github.hans_adler.nlp.la2.implementation;

import com.github.hans_adler.nlp.la2.Axis;
import com.github.hans_adler.nlp.la2.Matrix;
import com.github.hans_adler.nlp.la2.Vector;

public class SparseMatrix<A1 extends Axis, A2 extends Axis>
                                         implements Matrix<A1, A2> {

    @SuppressWarnings("rawtypes")
    protected static final SparseVector[] INITIAL_VALUE_ARRAY = new SparseVector[0];
    
    protected final A1 axis1;
    protected final A2 axis2;
    
    protected SparseMatrix<A2, A1> transposed;
    
    protected SparseVector<A2>[] contentArray;
    int start;
    int ceiling;
    
    @SuppressWarnings("unchecked")
    public SparseMatrix(A1 axis1, A2 axis2) {
        this.axis1 = axis1;
        this.axis2 = axis2;
        if (axis1.bounded) {
            contentArray = new SparseVector[axis1.bound];
        } else {
            contentArray = INITIAL_VALUE_ARRAY;
        }
    }
    
    @Override
    public Vector<A2> see(int i) {
        checkIndex(i);
        return contentArray[start + i];
    }
    @Override
    public Matrix<A2, A1> seeTransposed() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Iterable<Entry<Vector<A2>>> seeAll(boolean sparse) {
        return new MyIteration(sparse);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public double getValue(int i, int j) {
        checkIndex(i);
        SparseVector<A2> row = null;
        if (start+i < ceiling) row = contentArray[start + i];
        if (row == null) return getDefaultValue();
        return row.getValue(j);
    }
    
    @Override
    public A1 getAxis1() {
        return axis1;
    }
    
    @Override
    public A2 getAxis2() {
        return axis2;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * INNER CLASSES
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    private class MyIteration extends Entry<Vector<A2>> implements Iteration<Entry<Vector<A2>>> {
        
        private final boolean sparse;
        private int key = start;
        
        public MyIteration(boolean sparse) {
            this.sparse = sparse;
            if (sparse) {
                while (key < ceiling && contentArray[key] == null) key++;
            }
        }

        @Override
        public boolean hasNext() {
            return key < ceiling;
        }

        @Override
        public Entry<Vector<A2>> next() {
            index   = key - start;
            content = contentArray[key++];
            if (sparse) {
                while (key < ceiling && contentArray[key] == null) key++;
            }
            return this;
        }
    }
}
