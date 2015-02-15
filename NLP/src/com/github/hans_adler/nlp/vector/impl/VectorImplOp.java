package com.github.hans_adler.nlp.vector.impl;

import lombok.NonNull;
import com.github.hans_adler.nlp.vector.Axis;
import com.github.hans_adler.nlp.vector.Vector;

public interface VectorImplOp<A extends Axis>
                 extends Vector<A> {
    

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Addition
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public default Vector<A> add(
            double lambdaOther, @NonNull Vector<A> other,
            double b) {
        return combine(1.0, lambdaOther, other, b);
    }
    
    public default Vector<A> add(
            double lambdaOther, @NonNull Vector<A> other) {
        return combine(1.0, lambdaOther, other, 0.0);
    }
    
    public default Vector<A> add(
            @NonNull Vector<A> other, double b) {
        return combine(1.0, 1.0, other, b);
    }

    public default Vector<A> add(
            @NonNull Vector<A> other) {
        return combine(1.0, 1.0, other, 0.0);
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Linear combinations
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public default Vector<A> combine(double lambda,
            double lambdaOther, @NonNull Vector<A> other,
            double b) {
        VectorOp2Combine<A> op = new VectorOp2Combine<>(getAxis());
        op.load0(this);
        op.load1(this);
        op.load2(other);
        op.setParameters(lambda, lambdaOther, b);
        op.compute();
        op.unload0(this);
        return this;
    }
    
    public default Vector<A> combine(double lambda,
            double lambdaOther, @NonNull Vector<A> other) {
        return combine(lambda, lambdaOther, other, 0.0);
    }
    
    public default Vector<A> combine(double lambda,
            @NonNull Vector<A> other,
            double b) {
        return combine(lambda, 1.0, other, b);
    }

    public default Vector<A> combine(double lambda,
            @NonNull Vector<A> other) {
        return combine(lambda, 1.0, other, 0.0);
    }

}
