package com.github.hans_adler.nlp.vector.impl;

import lombok.Getter;
import com.github.hans_adler.nlp.vector.Axis;

public final class ScalarValuedVectorOp1Multiply<A extends Axis> extends VectorOp1<A> {

    @Getter double result;
    
    protected ScalarValuedVectorOp1Multiply(Axis axis) {
        super(axis);
    }

    @Override
    public VectorOp0<A> compute() {
        // TODO Auto-generated method stub
        
    }

}
