package com.github.hans_adler.nlp.la.unused;

import com.github.hans_adler.nlp.la.Axis;
import com.github.hans_adler.nlp.la.Matrix;
import com.github.hans_adler.nlp.la.Vector;
import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.iteration.Iteration;


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
        content = matrix.view(index++);
        return this;
    }

    
}
