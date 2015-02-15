package com.github.hans_adler.nlp.vector.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import com.github.hans_adler.nlp.la.internal.FastMath;
import com.github.hans_adler.nlp.vector.Axis;
import com.github.hans_adler.nlp.vector.Vector;

public class VectorImpl<A extends Axis>
       implements Vector<A>,
                  VectorImplOp<A>,
                  Cloneable {
    
    //     Implementation note:
    //     To avoid confusion, internal indices in the arrays storing the actual
    //     information are not referred to as indices but as keys.
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Static fields
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    protected static int            GUARD  = Integer.MAX_VALUE;
    protected static double         NAN    = Double.NaN;
    
    protected static final int[]    EMPTY_INDEX_ARRAY 
                                    = new int[]    { GUARD };
    protected static final double[] EMPTY_VALUE_ARRAY
                                    = new double[] { NAN };
    
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Constructor and axis
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Getter protected final A axis;
    
    public VectorImpl(@NonNull A axis, int initialCapacity) {
        this.axis = axis;

        if (initialCapacity == DENSE) {
            if (!axis.bounded) throw new IllegalArgumentException("Dense unbounded vectors not allowed");
        } else {
            if (initialCapacity < 0) throw new IllegalArgumentException(String.format("Requested capacity %d not in range 0 ... %d", initialCapacity, axis.bound-1));
            assert initialCapacity >= 0;
            initialCapacity = Math.min(initialCapacity, axis.bound);
        }
        initValues(initialCapacity);
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Values and default
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Getter @Setter public double defaultValue;

    protected double[] value;
    protected int[]    index;
    protected int      ceiling = 0;
    
    
    protected void initValues(int initialCapacity) {
        if (initialCapacity == DENSE) {
            initialCapacity = axis.bound;
        } else{
            assert 0 <= initialCapacity && initialCapacity <= axis.bound;
            if (initialCapacity == 0) {
                index = EMPTY_INDEX_ARRAY;
            } else {
                index = new int[initialCapacity+1]; 
                index[0] = GUARD;
            }
        }

        value = new double[initialCapacity+1];
        value[initialCapacity] = Double.NaN;
    }
    
    
    @Override
    public double getValue(int i) {
        axis.checkRange(i);
        int k = key(i, false);
        if (isKey(i,k)) return value[k];
        else return getDefaultValue();
    }
    @Override
    public void setValue(int i, double x) {
        axis.checkRange(i);
        int k = key(i, true);
        value[k] = x;
    }
    @Override
    public void addValue(int i, double b) {
        axis.checkRange(i);
        if (b == 0.0) return;
        int k = key(i, true);
        value[k] += b;
    }
    @Override
    public void multiplyValue(int i, double lambda) {
        axis.checkRange(i);
        if (lambda == 1.0) return;
        int k = key(i, true);
        value[k] *= lambda;
    }
    @Override
    public void transformValue(int i, double lambda, double b) {
        axis.checkRange(i);
        int k = key(i, true);
        value[k] = lambda*value[k] + b;
    }
    
    
    /**
     * Macro for testing if k is in fact the key for index i.
     * 
     * @param i
     * @param k
     * @return
     */
    protected final boolean isKey(int i, int k) {
        axis.assertRange(i);
        return index[k] == i;
    }
    
    /**
     * Returns the key for index i or approximates it from below.
     * Optionally creates it if it does not exist.
     * 
     * @param i
     * @return the greatest k such that indexArray[k] &lt;= i.
     */
    protected int key(int i, boolean create) {
        axis.assertRange(i);
        return key(i, create, i);
    }
    /**
     * Returns the key for index i or approximates it from below, using the
     * hint as to where it might be found.
     * 
     * @param i
     * @param hint
     * @return
     */
    protected int key(int i, boolean create, int hint) {
        axis.assertRange(i);
        
        // Locate index
        int k;
        if (index == null) return i;
        if (i == ceiling || i <= index[0]) {
            // We treat result 0 as a special case now so that we can just use
            // 0 as a guard later on.
            k = 0;
        } else {
            k = FastMath.ranged(0, hint, i+1);
            k = Math.min(k,  ceiling);
            int ii = index[k];

            assert i > index[0];
            while (ii > i) {
                ii = index[--k];
            }
            
            assert index[ceiling] == GUARD;
            while (ii < i) {
                ii = index[++k];
            }
        }
        if (!create || index[k] == i) return k;
        
        // Expand arrays if necessary
        int[]    index1 = index;
        double[] value1 = value;
        if (++ceiling == index.length) {
            int newLength = index.length+32;
            index = new int   [newLength];
            value = new double[newLength];
            System.arraycopy(index1, 0, index, 0, k);
            System.arraycopy(value1, 0, value, 0, k);
        }
        
        // Create new non-default cell
        System.arraycopy(index1, k, index, k+1, ceiling-k);
        System.arraycopy(value1, k, value, k+1, ceiling-k);
        index[k] = i;
        
        return k;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Methods inherited from Object
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Override
    public String toString() {
        final int limit = 12;
        StringBuilder result = new StringBuilder();
        result.append(String.format("SparseVector@%x(", hashCode()));
        for (int i = 0; i < Math.min(ceiling, limit); i++) {
            result.append(String.format("%d:%.3f|", index[i], value[i]));
        }
        if (ceiling <= limit) {
            result.delete(result.length()-1, result.length());
            if (ceiling > 0) {
                result.append(')');
            }
        } else {
            result.append("...)");
        }
        return result.toString();
    }
    
    @Override
    @SneakyThrows
    public VectorImpl<A> clone() {
        @SuppressWarnings("unchecked")
        VectorImpl<A> result = (VectorImpl<A>) super.clone();
        if (index != null) result.index = index.clone();
        result.value = value.clone();
        return result;
    }
}
