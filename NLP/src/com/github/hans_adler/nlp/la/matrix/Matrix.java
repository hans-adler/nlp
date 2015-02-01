package com.github.hans_adler.nlp.la.matrix;

import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.vector.VectorView;

/**
 * <p>Interface for 0-based matrices of doubles.
 * Any method whose name starts with a verb that is not "get" or "copy" returns
 * the matrix itself for easy chaining of methods.</P>
 * 
 * <p>TODO: Vector view of matrices. Linearisation of the two indices differs
 * depending on which direction, if any, is open.</p>
 * 
 * <p>@author Hans Adler (johannes.aquila@gmail.com) 2015.</p>
 */
public interface Matrix extends MatrixView {
    
    /**
     * <p>Performs A[i,j] = value. If i == ALL, this is done for all i, and
     * similarly for j == ALL. If i == j == ALL, this amounts to
     * A = value.</p>
     * 
     * <p>For sparse matrices special restrictions may apply regarding the use
     * of ALL.</p>
     * 
     * <p>If the matrix is sparse and i, j are both not ALL, A[i,j] is made a
     * non-default cell even if value happens to be the offset of the
     * matrix.
     * If both are ALL, then for flexible structure sparse matrices,
     * all non-default cells are reset to default status, whereas for fixed
     * structure sparse matrices the structure is unaffected.
     * If only one is ALL, this aspect of the behaviour is defined by the
     * implementation.</p>
     * 
     * @param i
     * @param j
     * @param value
     */
    public abstract Matrix set(int i, int j, double value);

    /**
     * <p>Performs A[i,j] += value. If i == ALL, this is done for all i, and
     * similarly for j == ALL. If i == j == ALL, this amounts to
     * A = A + value.</p>
     * 
     * <p>For sparse matrices special restrictions may apply regarding the use
     * of ALL.</p>
     * 
     * <p>If the matrix is sparse and i, j are both not ALL, A[i,j] is made a
     * non-default cell even if value happens to be the offset of the
     * matrix. If both are ALL, this cannot happen. If only one is ALL, this
     * aspect of the behaviour is defined by the implementation.</p>
     * 
     * @param i
     * @param j
     * @param value
     */
    public abstract Matrix add(int i, int j, double value);
    
    public default Matrix addDiagonal(VectorView vector) {
        throw new UnsupportedOperationException();
    }
    
    public default Matrix add(Matrix other) {
        throw new UnsupportedOperationException();
    }

    public abstract Matrix multiply(int i, int j, double value);
    
    public default Matrix multiplyDiagonal(
            VectorView leftFactor, VectorView rightFactor) {
        if (leftFactor != null) {
            for (Entry entry: leftFactor) {
                multiply(entry.index, ALL, entry.value);
            }
        }
        if (rightFactor != null) {
            for (Entry entry: rightFactor) {
                multiply(ALL, entry.index, entry.value);
            }
        }
        return this;
    }
    
    public abstract Matrix multiplySlice(int i, int j, VectorView vector);
    
    public default Matrix makeRowStochastic() {
        return multiplyDiagonal(getRowSumColVector(1.0, -1.0), null);
    }
    public default Matrix makeColStochastic() {
        return multiplyDiagonal(null, getColSumRowVector(1.0, -1.0));
    }
}
