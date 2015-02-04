package com.github.hans_adler.nlp.la.implementation;

import java.util.Arrays;
import com.github.hans_adler.nlp.la.Axis;
import com.github.hans_adler.nlp.la.MutableScalar;
import com.github.hans_adler.nlp.la.MutableVector;
import com.github.hans_adler.nlp.la.Scalar;
import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.iteration.Iteration;

/**
 * TODO: Refactor so that at the core there is an ArrayList variant whose
 * array is exposed for quick direct access without unnecessary creation of
 * wrapper objects. Then a sparse List built on top of that.
 * This architecture will make it easier to support most primitive datatypes
 * by starting with a generic version (for objects) and gradually adding
 * special code that circumvents the wrapper objects.
 * 
 * But note that remove(4) is ambiguous for List<Integer>!!!
 * 
 * @author Hans Adler (johannes.aquila@gmail.com)
 *
 * @param <A1>
 */
public class SparseVector<A1 extends Axis> implements MutableVector<A1> {
    //     Implementation notes:
    //     To avoid confusion, internal indices in the arrays storing the actual
    //     information are not referred to as indices but as keys.
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Fields and constructors
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    protected static final int[]     INITIAL_INDEX_ARRAY = new int[0]; 
    protected static final double[]  INITIAL_VALUE_ARRAY = new double[0];
    
    protected final A1 axis1;
    
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
    public Iterable<Entry<Scalar>> viewAll(boolean sparse) {
        return new MyIteration<Scalar>(sparse);
    }

    @Override
    public Iterable<Entry<MutableScalar>> takeAll(boolean sparse) {
        return new MyIteration<MutableScalar>(sparse);
    }

    @Override
    public MutableScalar view(int j) {
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
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public MutableVector<A1> setValue(int j, double value) {
        checkIndex(j);
        int key = createKey(j); // createKey() can change arrays!!!
        valueArray[key] = value;
        return this;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

    /**
     * <p>Returns the key corresponding to the index. Creates it first if it
     * does not exist yet.</p>
     * 
     * <p><em>Note:</em> Since this method sometimes changes indexArray and
     * valueArray (because they have to grow), it is unsafe to call
     * indexArray[obtainKey[index]] or valueArray[obtainKey[index]].
     * Instead, store the key in a variable and <em>then</em> index the array
     * with that variable.</p> 
     * 
     * @param j
     * @return
     */
    private int createKey(int j) {
        checkIndex(j);
        // Handling the special case when array not yet allocated.
        if (ceiling == start) {
            assert start == 0;
            indexArray = new int[20];
            valueArray = new double[20];
            indexArray[0] = j;
            valueArray[0] = 0.0;
            ceiling = 1;
            return 0;
        }
        
        // Looking for the key and returning it if successful.
        int key = start + j;
        if (key >= ceiling) key = ceiling-1;
        int jj = indexArray[key];
        while (jj > j && key > start) {
            key--;
            jj = indexArray[key];
        }
        if (jj == j) return key;
        
        // Key not found.
        if (jj < j) key++;
        assert start == 0;
        if (ceiling == indexArray.length) {
            int newCapacity = indexArray.length+32;
            indexArray = Arrays.copyOf(indexArray, newCapacity);
            valueArray = Arrays.copyOf(valueArray, newCapacity);
        }
        System.arraycopy(indexArray, key, indexArray, key+1, ceiling-key);
        System.arraycopy(valueArray, key, valueArray, key+1, ceiling-key);
        indexArray[key] = j;
        valueArray[key] = 0.0;
        ceiling++;
        return key;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * INNER CLASSES
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private class MyIteration<S extends Scalar> extends Entry<MyScalar> implements Iteration<Entry<S>> {
        
        boolean sparse;
        int key = start;
        int bound = getAxis1().bound;

        public MyIteration(boolean sparse) {
            this.sparse = sparse;
            content = new MyScalar(-1);
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

        @SuppressWarnings("unchecked")
        @Override
        public Entry<S> next() {
            if (sparse) {
                index = indexArray[key];
                key++;
            } else {
                if (index == indexArray[key]) {
                    key++;
                }
            }
            ((MyScalar) content).index = index;
            return (Entry<S>) this;
        }
    }
    
    private class MyScalar implements MutableScalar {
        
        // TODO: Implement caching of key for better performance.
        
        private int index; // changed from outside the class
        
        MyScalar(int index) {
            this.index = index;
        }

        @Override
        public double getValue() {
            return SparseVector.this.getValue(index);
        }

        @Override
        public MutableScalar setValue(double value) {
            SparseVector.this.setValue(index, value);
            return this;
        }
               
    }
    
}
