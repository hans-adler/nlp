package com.github.hans_adler.nlp.vector.impl;

import lombok.NonNull;
import com.github.hans_adler.nlp.vector.Axis;
import com.github.hans_adler.nlp.vector.Vector;

public abstract class VectorOp0<A extends Axis> {
    
    protected final Axis axis;
    
    protected double[] value0;
    protected double   defval0;
    protected int   [] index0;
    protected int      ceiling0;
    
    protected VectorOp0(@NonNull Axis axis) {
        this.axis = axis;
    }
    
    @SuppressWarnings({ "rawtypes" })
    public void load0(@NonNull Vector<A> result) {
        VectorImpl vector = (VectorImpl) result;
        assert vector.axis == axis;
        value0   = vector.value;
        defval0  = vector.defaultValue;
        index0   = vector.index;
        ceiling0 = vector.ceiling;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void unload0(@NonNull Vector<A> result) {
        VectorImpl vector = (VectorImpl) result;
        assert vector.axis == axis;
        vector.value         = value0;
        vector.defaultValue  = defval0;
        vector.index         = index0;
        vector.ceiling       = ceiling0;
    }
    
    public abstract VectorOp0<A> compute();

}
