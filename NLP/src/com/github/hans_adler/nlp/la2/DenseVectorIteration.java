package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.core.Axis;
import com.github.hans_adler.nlp.la2.core.Matrix;
import com.github.hans_adler.nlp.la2.core.Vector;


public class DenseVectorIteration<A1 extends Axis, A2 extends Axis>
            extends Entry<Vector<A2>> implements Iteration<Entry<Vector<A2>>>{

    private Matrix<A1, A2> matrix;

    public DenseVectorIteration(Matrix<A1, A2> matrix) {
        assert matrix.getAxis1().bounded;
        this.matrix = matrix;
    }
    
    @Override
    public boolean hasNext() {
        return index < matrix.getAxis1().bound;
    }

    @Override
    public Entry<Vector<A2>> next() {
        content = matrix.see(index++);
        return this;
    }

    
}
