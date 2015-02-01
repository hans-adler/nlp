package hans_adler.linear_algebra.matrix;

import hans_adler.linear_algebra.vector.OpenVector;
import hans_adler.linear_algebra.vector.Vector;
import hans_adler.linear_algebra.vector.VectorView;

/**
 * Wrapper around a read-only vector that presents it as a diagonal matrix.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 */
public class DiagonalMatrixView implements MatrixView {
    
    private final VectorView vector;

    public DiagonalMatrixView(VectorView vector) {
        this.vector = vector;
    }

    @Override
    public double get(int i, int j) {
        if (i >= 0) {
            if (j >= 0) {
                if (i == j) return vector.get(i); else return 0.0;
            } else {
                return vector.get(i);
            }
        } else {
            if (j >= 0) {
                return vector.get(j);
            } else {
                return vector.getSum(1.0, 1.0);
            }
        }
    }

    @Override
    public VectorView getSlice(int i, int j) {
        Vector result = new OpenVector(vector.getDimension());
        if (i < 0) {
            assert j >= 0;
            result.set(j, vector.get(j));
            return result;
        } else {
            assert j < 0;
            result.set(i, vector.get(i));
            return result;
        }
    }

    @Override
    public VectorView getRowSumColVector(
            double individualExponent, double sumExponent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VectorView getColSumRowVector(
            double individualExponent, double sumExponent) {
        throw new UnsupportedOperationException();
    }

}
