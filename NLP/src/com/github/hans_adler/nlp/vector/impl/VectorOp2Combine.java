package com.github.hans_adler.nlp.vector.impl;

import static com.github.hans_adler.nlp.vector.impl.VectorImpl.GUARD;
import com.github.hans_adler.nlp.vector.Axis;

public final class VectorOp2Combine<A extends Axis> extends VectorOp2<A> {
    
    private double lambda1;
    private double lambda2;
    private double b;

    VectorOp2Combine(Axis axis) {
        super(axis);
    }
    
    public void setParameters(double lambda1, double lambda2, double b) {
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.b = b;
    }
    
    @Override
    public VectorOp2Combine<A> compute() {
        if (index1 == null) {
            if (index2 == null) computeDD();
            else                computeDS();
        } else {
            if (index2 == null) computeSD();
            else                computeSS();
        }
        return this;
    }
    
    private void computeDD() {
        assert index1   == index2;
        assert ceiling1 == ceiling2;
        assert value0.length > axis.bound;
        if (index0 != index1) {
            if (index1 == null) {
                index0 = null;
            }
        }
        ceiling0 = ceiling1;
        defval0 = lambda1 * defval1 + lambda2 * defval2 + b;
        for (int k = 0; k < ceiling0; k++) {
            value0[k] = lambda1 * value1[k] + lambda2 * value2[k] + b;
        }
    }
    
    private void computeSS() {
        if (index1 == index2 && ceiling1 == ceiling2) {
            if (index0.length < ceiling1 + 1) {
                index0 = new int   [ceiling1+1];
                value0 = new double[ceiling1+1];
            }
            System.arraycopy(index1, 0, index0, 0, ceiling1+1);
            computeDD();
            return;
        }
        double lambda1Defval1 = lambda1*defval1; 
        double lambda2Defval2 = lambda2*defval2;
        defval0 = lambda1Defval1 + lambda2Defval2 + b;
        int k0 = 0, k1 = 0, k2 = 0;
        int i0, i1, i2;
        do {
            i0 = Math.min(i1 = index1[k1], i2 = index2[k2]);
            if (i1 > i0) {
                assert i2 == i0;
                index0[k0] = i0;
                value0[k0++] = lambda1Defval1 + lambda2*value2[k2++] + b;
            } else if (i2 > i0) {
                assert i1 == i0;
                index0[k0] = i0;
                value0[k0++] = lambda1*value1[k1++] + lambda2Defval2 + b;
            } else {
                assert i1 == i0 && i2 == i0;
                index0[k0] = i0;
                value0[k0++] = lambda1*value1[k1++] + lambda2*value2[k2++] + b;
            }
        } while (i0 < GUARD);
    }
    
    private void computeSD() {
        throw new UnsupportedOperationException();
    }
    
    private void computeDS() {
        throw new UnsupportedOperationException();
    }
}
