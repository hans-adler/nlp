package com.github.hans_adler.nlp.vector.impl;

import lombok.NonNull;
import com.github.hans_adler.nlp.vector.Axis;
import com.github.hans_adler.nlp.vector.Vector;

public abstract class VectorOp2<A extends Axis> extends VectorOp1<A> {
    
    protected VectorOp2(Axis axis) {
        super(axis);
    }

    protected double[] value2;
    protected double   defval2;
    protected int   [] index2;
    protected int      ceiling2;
    
    @SuppressWarnings({ "rawtypes" })
    public void load2(@NonNull Vector<A> result) {
        VectorImpl vector = (VectorImpl) result;
        assert vector.axis == axis;
        value2   = vector.value;
        defval2  = vector.defaultValue;
        index2   = vector.index;
        ceiling2 = vector.ceiling;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void unload2(@NonNull Vector<A> result) {
        VectorImpl vector = (VectorImpl) result;
        assert vector.axis == axis;
        vector.value         = value2;
        vector.defaultValue  = defval2;
        vector.index         = index2;
        vector.ceiling       = ceiling2;
    }
    
}
