package com.github.hans_adler.nlp.la.matrix;

import java.util.Map;
import com.github.hans_adler.nlp.la.vector.OpenVector;
import com.github.hans_adler.nlp.la.vector.VectorView;

/**
 * For sparse matrices of variable structure. Even the dimensions are dynamic.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 */
public class OpenMatrix extends OpenMatrixView implements Matrix {

    public OpenMatrix() {
        super();
    }
    
    protected OpenMatrix(int idim, int jdim,
            double offset,
            boolean hasRows, boolean hasCols) {
        super(idim, jdim, offset, hasRows, hasCols);
    }

    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SET
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Override
    public OpenMatrix set(int i, int j, double newValue) {
//        System.err.printf("set(%d, %d, %f)\n", i, j, value);
        assert i >= -1 && j >= -1;
        assert idim < 0 || i < idim;
        assert jdim < 0 || j < jdim;
        if (i < 0 && j < 0) {
            if (idim < 0 || jdim < 0) throw new RuntimeException();
            rows.clear();
            cols.clear();
            offset = newValue;
            return this;
        }
        if (i >= 0) {
            obtainRowVector(i).set(j, newValue);
        } else {
            if (idim < 0) throw new RuntimeException();
            for (int ii = 0; ii < idim; ii++) {
                obtainRowVector(ii).set(j, newValue);
            }
        }
        if (j >= 0) {
            obtainColVector(j).set(i, newValue);
        } else {
            if (jdim < 0) throw new RuntimeException();
            for (int jj = 0; jj < jdim; jj++) {
                obtainColVector(jj).set(i, newValue);
            }
        }
        return this;
    }
    
    @Override
    public OpenMatrix set(MatrixView other) {
        assert other.getDefaultValue() == 0.0;
        idim = other.getDimensions()[0];
        jdim = other.getDimensions()[1];
        rows.clear();
        for (Map.Entry<Integer, ? extends VectorView> entry: getRows()) {
            rows.put(entry.getKey(), new OpenVector(entry.getValue()));
        }
        cols.clear();
        for (Map.Entry<Integer, ? extends VectorView> entry: getCols()) {
            cols.put(entry.getKey(), new OpenVector(entry.getValue()));
        }
        return this;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ADD
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public OpenMatrix add(int i, int j, double summand) {
        assert i >= -1 && j >= -1;
        assert idim < 0 || i < idim;
        assert jdim < 0 || j < jdim;
        if (i < 0 && j < 0) {
            if (idim < 0 || jdim < 0) throw new RuntimeException();
            offset += summand;
            return this;
        }
        if (i >= 0) {
            obtainRowVector(i).add(j, summand);
        } else {
            if (idim < 0) throw new RuntimeException();
            for (int ii = 0; ii < idim; ii++) {
                obtainRowVector(ii).add(j, summand);
            }
        }
        if (j >= 0) {
            obtainColVector(j).add(i, summand);
        } else {
            if (jdim < 0) throw new RuntimeException();
            for (int jj = 0; jj < jdim; jj++) {
                obtainColVector(jj).add(i, summand);
            }
        }
        return this;
    }

    @Override
    public OpenMatrix add(int i, int j, VectorView vector) {
        assert vector.getDefaultValue() == 0.0; 
        if (i == ALL) {
            assert 0 <= j && (jdim == ALL || j < jdim);
            obtainColVector(j).add(vector);
            synchronizeRowsForNewCol(j);
            return this;
        } else if (j == ALL) {
            assert 0 <= i && (idim == ALL || i < idim);
            obtainRowVector(i).add(vector);
            synchronizeColsForRow(i);
            return this;
        }
        throw new AssertionError();
    }

    @Override
    public OpenMatrix add(MatrixView other) {
        for (Map.Entry<Integer, ? extends VectorView> entry: other.getCols()) {
            add(ALL, entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * MULTIPLY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Override
    public OpenMatrix multiply(int i, int j, double factor) {
        assert i >= -1 && j >= -1;
        assert idim < 0 || i < idim;
        assert jdim < 0 || j < jdim;
        if (factor == 1.0) return this;
        if (factor == 0.0) set(i, j, 0.0);
        if (i < 0) {
            if (j < 0) {
                offset *= factor;
            } else {
                obtainColVector(j).multiply(ALL, factor);
                synchronizeRowsForNewCol(j);
            }
        } else {
            if (j < 0) {
                obtainRowVector(i).multiply(ALL, factor);
                synchronizeColsForRow(i);
            } else {
                set(i, j, get(i, j) * factor);
            }
        }
        return this;
    }

    @Override
    public OpenMatrix multiply(int i, int j, VectorView vector) {
        assert (i < 0 && j >= 0) || (j < 0 && i >= 0);
        if (i < 0) {
            assert i == ALL;
            assert 0 <= j && j < jdim;
            obtainColVector(j).multiplyDiagonal(vector);
            synchronizeRowsForNewCol(j);
        } else {
            assert j == ALL;
            assert 0 <= i && i < idim;
            obtainRowVector(i).multiplyDiagonal(vector);
            synchronizeColsForRow(i);
        }
        return this;
    }
    
}
