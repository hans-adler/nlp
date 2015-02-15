package com.github.hans_adler.nlp.vector.impl;

import lombok.NonNull;
import com.github.hans_adler.nlp.vector.Axis;
import com.github.hans_adler.nlp.vector.Vector;

public abstract class VectorOp1<A extends Axis> extends VectorOp0<A> {
    
    protected VectorOp1(Axis axis) {
        super(axis);
    }

    protected double[] value1;
    protected double   defval1;
    protected int   [] index1;
    protected int      ceiling1;
    
    @SuppressWarnings({ "rawtypes" })
    public void load1(@NonNull Vector<A> result) {
        VectorImpl vector = (VectorImpl) result;
        assert vector.axis == axis;
        value1   = vector.value;
        defval1  = vector.defaultValue;
        index1   = vector.index;
        ceiling1 = vector.ceiling;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void unload1(@NonNull Vector<A> result) {
        VectorImpl vector = (VectorImpl) result;
        assert vector.axis == axis;
        vector.value         = value1;
        vector.defaultValue  = defval1;
        vector.index         = index1;
        vector.ceiling       = ceiling1;
    }
    
}
