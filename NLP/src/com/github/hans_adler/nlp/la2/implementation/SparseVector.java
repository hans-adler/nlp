package com.github.hans_adler.nlp.la2.implementation;

import com.github.hans_adler.nlp.la2.Axis;
import com.github.hans_adler.nlp.la2.Scalar;
import com.github.hans_adler.nlp.la2.Vector;

public class SparseVector<A1 extends Axis> implements Vector<A1> {
    //     Implementation notes:
    //     To avoid confusion, internal indices in the arrays storing the actual
    //     information are not referred to as indices but as keys.
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Fields and constructors
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    protected static final int[]     INITIAL_INDEX_ARRAY = new int[0]; 
    protected static final double[]  INITIAL_VALUE_ARRAY = new double[0];
    
    protected final A1 axis1;
    
    protected SparseVector<A1> transposed;
    
    protected int[]    indexArray;
    protected double[] valueArray;
    protected int start;
    protected int ceiling;
    
    public SparseVector(A1 axis1) {
        this.axis1 = axis1;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public Vector<A1> seeTransposed() {
        if (transposed == null) {
            transposed = new SparseVector<>(axis1   );
            transposed.transposed = this;
            transposed.indexArray = this.indexArray;
            transposed.valueArray = this.valueArray;
            transposed.start      = this.start;
            transposed.ceiling    = this.ceiling;
        }
        return transposed;
    }

    @Override
    public Iterable<Entry<Scalar>> seeAll(boolean sparse) {
        return new MyIteration(sparse);
    }

    @Override
    public Scalar see(int j) {
        return new ConcreteScalar(getValue(j));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public double getValue(int j) {
        checkIndex(j);
        int key = key(j);
        if (key < 0) return getDefaultValue();
        return valueArray[key];
    }
    
    @Override
    public A1 getAxis1() {
        return axis1;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Private methods
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * Returns the key corresponding to the index, or -1 if there is none.
     * 
     * @param j
     * @return
     */
    private int key(int j) {
        if (ceiling == start) return -1;
        int key = start + j;
        if (key >= ceiling) key = ceiling-1;
        int jj = indexArray[key];
        while (jj > j) {
            key--;
            jj = indexArray[key];
        }
        if (jj < j) return -1;
        return key;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * INNER CLASSES
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private class MyIteration extends Entry<Scalar> implements Iteration<Entry<Scalar>> {
        
        boolean sparse;
        int key = start;
        int bound = getAxis1().bound;

        public MyIteration(boolean sparse) {
            this.sparse = sparse;
            content = new ConcreteScalar(Double.NaN);
            index = 0;
        }
        
        @Override
        public boolean hasNext() {
            if (sparse) {
                return key < ceiling;
            } else {
                return index < bound;
            }
        }

        @Override
        public Entry<Scalar> next() {
            if (sparse) {
                index = indexArray[key];
                ((ConcreteScalar) content).value = valueArray[key++];
            } else {
                if (index == indexArray[key]) {
                    ((ConcreteScalar) content).value = valueArray[key++];
                } else {
                    ((ConcreteScalar) content).value = getDefaultValue();
                }
            }
            return this;
        }
    }
    
}
