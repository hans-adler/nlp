package com.github.hans_adler.nlp.la.implementation;

import java.util.Arrays;
import java.util.Iterator;
import com.github.hans_adler.nlp.la.Axis;
import com.github.hans_adler.nlp.la.MutableScalar;
import com.github.hans_adler.nlp.la.MutableVector;
import com.github.hans_adler.nlp.la.Scalar;
import com.github.hans_adler.nlp.la.interation.AbstractInteration;
import com.github.hans_adler.nlp.la.interation.Interation;
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
    
    protected int[]    indexArray = INITIAL_INDEX_ARRAY;
    protected double[] valueArray = INITIAL_VALUE_ARRAY;
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
    * OTHERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public String toString() {
        final int limit = 12;
        StringBuilder result = new StringBuilder();
        result.append(String.format("SparseVector@%x(", hashCode()));
        for (int i = start; i < Math.min(ceiling, limit); i++) {
            result.append(String.format("%d:%.3f|", indexArray[i], valueArray[i]));
        }
        if (ceiling <= limit) {
            result.delete(result.length()-1, result.length());
            if (ceiling > start) {
                result.append(')');
            }
        } else {
            result.append("...)");
        }
        return result.toString();
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Private methods
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * Returns the key corresponding to the index, or a negative number if
     * there is none.
     * 
     * If there is no key corresponding to index j, let jj be the smallest
     * index > j which has a key (or jj = ceiling if there is none).
     * Then -(jj+1) is returned. (Not a very nice value, but we must guarantee
     * it is always negatives and carries the full information of the original,
     * which may be anything from 0 to bound-1.)
     * 
     * @param j
     * @return
     */
    private int key(int j) {
        return key(j, start+j);
    }

    /**
     * Same as {link {@link SparseVector#key(int, int)}, but start looking
     * near nearbyKey. Useful e.g. in iterators.
     * 
     * @param j
     * @param nearbyKey
     * @return
     */
    private int key(int j, int nearbyKey) {
        if (ceiling == start) return -(ceiling + 1);
        int key = nearbyKey;
        // Fix if key is in implausible range.
        if (key >= start + j) key = start+j;
        // Fix if key is in forbidden range.
        if (key < start) key = start;
        if (key >= ceiling) key = ceiling-1;
        int jj = indexArray[key];
        // After the following loop, jj is guaranteed to be <= j.
        // Usually we will start with a key that is too big, so the loop will
        // be used.
        while (jj > j) {
            key--;
            if (key < start) {
                jj = -1;
            } else {
                jj = indexArray[key];
            }
        }
        // After the following loop, jj is guaranteed to be >= j.
        // This is only relevant if we started with a key that was too small.
        // This tends to happen when iterators call this method.
        while (jj < j) {
            key++;
            if (key >= ceiling) {
                jj = Axis.UNBOUNDED;
            } else {
                jj = indexArray[key];
            }
        }
        if (jj == j) return key;
        assert jj > j;
        return -(key+1);
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
        int key;
        boolean nextLoaded;

        public MyIteration(boolean sparse) {
            this.sparse = sparse;
            content = new MyScalar();
            index = -1;
            key = start -1;
            nextLoaded = false;
        }
        
        @Override
        public boolean hasNext() {
            if (!nextLoaded) loadNext();
            return index < getAxis1().bound;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Entry<S> next() {
            assert nextLoaded;
            assert index < getAxis1().bound;
            if (!nextLoaded) loadNext();
            nextLoaded = false;
            return (Entry<S>) this;
        }
        
        private void loadNext() {
            if (nextLoaded) return;
            index++;
            key++;
            if (sparse) {
                key = key(index, key);
                if (key < 0) {
                    // We got -(key+1) instead, where key belongs to the smallest
                    // existing index that is greater than what we asked for.
                    // Unpack and find the corresponding index.
                    key = -key - 1;
                    assert key >= start;
                    if (key < ceiling) {
                        index = indexArray[key];
                    }
                }
            }
            if (key < ceiling) {
                //((MyScalar) content).index = index;
                content = new MyScalar(index);
            } else {
                key = Integer.MAX_VALUE;
                index = Axis.UNBOUNDED;
                //((MyScalar) content).invalidate();
                content = new MyScalar();
            }
            nextLoaded = true;
        }
    }
    
    private class MyInteration extends AbstractInteration<MyInteration> 
        implements Interation<MyInteration> {
        
        private boolean sparse;
        protected int key = Integer.MAX_VALUE;
        protected int futureKey = Integer.MAX_VALUE;
        
        public MyInteration(boolean sparse) {
            this.sparse = sparse;
        }

        @Override
        public void advance() {
            if (index == Integer.MAX_VALUE) throw new IllegalStateException();
            key = futureKey++;
            index = futureIndex++;
            if (sparse) {
                futureKey = key(index, futureKey);
                if (futureKey < 0) {
                    // We got -(key+1) instead, where key belongs to the smallest
                    // existing index that is greater than what we asked for.
                    // Unpack and find the corresponding index.
                    futureKey = -futureKey - 1;
                    assert futureKey >= start;
                    if (futureKey < ceiling) {
                        futureIndex = indexArray[futureKey];
                    }
                }
            }
        }
    }
    
    private class MyScalar implements MutableScalar {
        
        // TODO: Implement caching of key for better performance.
        
        private int index; // changed from outside the class
        
        MyScalar(int index) {
            this.index = index;
        }
        MyScalar() {
            invalidate();
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
        
        void invalidate() {
            index = Axis.UNBOUNDED;
        }
        boolean valid() {
            return 0 < index && index < axis1.bound;
        }
        
        @Override
        public String toString() {
            if (valid()) {
                return String.format("Scalar/SparseVector@%x(%d:%f)", SparseVector.this.hashCode(), index, getValue());
            } else {
                return String.format("Scalar/SparseVector@%x(%d)", SparseVector.this.hashCode(), index);
            }
        }
               
    }
    
}
