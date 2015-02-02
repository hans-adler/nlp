package com.github.hans_adler.nlp.la2.unused;

import com.github.hans_adler.nlp.la2.Axis;
import com.github.hans_adler.nlp.la2.Matrix;
import com.github.hans_adler.nlp.la2.Vector;
import com.github.hans_adler.nlp.la2.implementation.Entry;
import com.github.hans_adler.nlp.la2.implementation.Iteration;


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
