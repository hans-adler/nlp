package com.github.hans_adler.nlp.la.matrix;

/**
 * Sparse matrix with fixed sparseness structure and fixed dimensions.
 */
public class SparseMatrix extends OpenMatrix {

    public SparseMatrix(int idim, int jdim, double offset) {
        super(idim, jdim, offset);
    }
    public SparseMatrix(int idim, int jdim) {
        this(idim, jdim, 0.0);
    }
    
}
