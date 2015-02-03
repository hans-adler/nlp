package com.github.hans_adler.nlp.la2.implementation;

import java.util.Arrays;
import com.github.hans_adler.nlp.la2.Axis;
import com.github.hans_adler.nlp.la2.Matrix;
import com.github.hans_adler.nlp.la2.MutableMatrix;
import com.github.hans_adler.nlp.la2.Vector;

public class SparseMatrix<A1 extends Axis, A2 extends Axis>
                                         implements MutableMatrix<A1, A2> {

    @SuppressWarnings("rawtypes")
    protected static final SparseVector[] INITIAL_VALUE_ARRAY = new SparseVector[0];
    
    protected final A1 axis1;
    protected final A2 axis2;
    
    protected SparseMatrix<A2, A1> transposed;
    
    protected SparseVector<A2>[] contentArray;
    int start;
    int ceiling;
    
    @SuppressWarnings("unchecked")
    public SparseMatrix(A1 axis1, A2 axis2, boolean buildTransposed) {
        this.axis1 = axis1;
        this.axis2 = axis2;
        if (axis1.bounded) {
            contentArray = new SparseVector[axis1.bound];
        } else {
            contentArray = INITIAL_VALUE_ARRAY;
        }
        if (!buildTransposed) return;
        assert transposed == null;
        transposed = new SparseMatrix<A2, A1>(axis2, axis1, false);
        transposed.transposed = this;
    }
    public SparseMatrix(A1 axis1, A2 axis2) {
        this(axis1, axis2, true);
    }
    
    @Override
    public Vector<A2> view(int i) {
        checkIndex(i);
        return contentArray[start + i];
    }
    @Override
    public Matrix<A2, A1> viewT() {
        return transposed;
    }
    
    @Override
    public Iterable<Entry<Vector<A2>>> viewAll(boolean sparse) {
        return new MyIteration(sparse);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public double getValue(int i, int j) {
        checkIndex(i, j);
        SparseVector<A2> row = rowVector(i);
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
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public MutableMatrix<A1, A2> setValue(int i, int j, double value) {
        // Do it here
        checkIndex(i, j);
        createRowVector(i).setValue(j, value);
        // Do it over there
        transposed.createRowVector(j).setValue(i, value);
        return this;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Protected methods
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    protected SparseVector<A2> rowVector(int i) {
        if (start+i >= ceiling) return null;
        return contentArray[start + i];
    }
    
    protected SparseVector<A2> createRowVector(int i) {
        checkIndex(i);
        // First grow list if necessary
        if (start+i >= ceiling) {
            int newCapacity = start+i+32;
            contentArray = Arrays.copyOf(contentArray, newCapacity);
            ceiling = start + newCapacity;
        } else if (contentArray[start+i] != null) {
            return contentArray[start+i];
        }
        return contentArray[start+i] = new SparseVector<A2>(axis2);
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
