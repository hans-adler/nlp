package com.github.hans_adler.nlp.la.matrix;

import com.github.hans_adler.nlp.la.vector.*;

/**
 * Interface for 0-based read-only matrices of doubles.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 */
public interface MatrixView {

    /**
     * Many methods accept this constant instead of a row or column index.
     */
    public static int ALL = -1;

    /**
     * <p>Returns A[i,j].</p>
     * 
     * <p>If i == ALL and/or j == ALL, returns the sum of the relevant
     * entries.</p>
     * 
     * @param i
     * @param j
     * @return T
     */
    public abstract double get(int i, int j);
    
    /**
     * <p>Provide a row or column vector for this matrix. Exactly one of i
     * and j must be ALL, indicating the orientation of the vector. The other
     * indicates the index at which the column vector (i==ALL) or row vector
     * (j==ALL) is to be taken.</p>
     * 
     * <p>As specified by the implementation, the vector may or may not be a
     * live view that changes when the matrix changes. The vector may or may not
     * actually be modifiable.</p>
     * 
     * @param i
     * @param j
     * @return VectorView
     */
    public abstract VectorView getSlice(int i, int j);
    
    /**
     * <p>Returns a vector containing the sums of the rows of the matrix.
     * TODO: Update documentation for exponent.</p>
     * 
     * <p>As specified by the implementation, the vector may or may not be a
     * live view that changes when the matrix changes. The vector may or may not
     * actually be modifiable.</p>
     * 
     * @param individualExponent
     * @param sumExponent
     * @return VectorView A column vector containing the row sums.
     */
    public abstract VectorView getRowSumColVector(
            double individualExponent, double sumExponent);
    /**
     * <p>Returns a vector containing the sums of the columns of the matrix.
     * TODO: Update documentation for exponent.</p>
     * 
     * <p>As specified by the implementation, the vector may or may not be a
     * live view that changes when the matrix changes. The vector may or may not
     * actually be modifiable.</p>
     * 
     * @param individualExponent
     * @param sumExponent
     * @return VectorView A row vector containing the column sums.
     */
    public abstract VectorView getColSumRowVector(
            double individualExponent, double sumExponent);
    
//    /**
//     * <p><em>Note:</em> The default implementation aggressively makes
//     * cells non-default if they happen to be zero. Sometimes this may
//     * not be desirable.</p>
//     * 
//     * @param colVector
//     * @return
//     */
//    public default Vector times(VectorView colVector) {
//        OpenVector result = new OpenVector();
//        for (Entry entry: colVector) {
//            if (entry.value == 0.0) continue;
//            VectorView rowVector = slice(entry.index, ALL);
//        }
//    }
    
    /**
     * <p>If this matrix is a row, column or diagonal matrix, return the
     * obvious underlying vector. Otherwise the behaviour is undefined, though
     * if the problem is easy to detect a RuntimeException should be
     * thrown.</p>
     * 
     * @return A vector that represents the contents of this matrix.
     * @throws RuntimeException if the matrix does not have the required shape.
     */
    public default VectorView copyAsVector() {
        throw new UnsupportedOperationException();
    }
    
//    public static Matrix product(MatrixView a, MatrixView b) {
//        OpenMatrix result = new OpenMatrix();
//        
//    }
}
