package com.github.hans_adler.nlp.la.matrix;

import java.util.HashMap;
import java.util.Map;
import com.github.hans_adler.nlp.la.vector.Entry;
import com.github.hans_adler.nlp.la.vector.OpenVector;
import com.github.hans_adler.nlp.la.vector.Vector;
import com.github.hans_adler.nlp.la.vector.VectorView;

/**
 * For sparse matrices of variable structure. Even the dimensions are dynamic.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 */
public class OpenMatrix implements Matrix {
	
	private Map<Integer, OpenVector> rows;
	private Map<Integer, OpenVector> cols;
	private double offset;
	private int idim;
	private int jdim;
	
	public OpenMatrix() {
		this(ALL, ALL, 0.0, true, true);
	}
	/**
	 * @param idim
	 * @param jdim
	 * @param defaultValue
	 */
	protected OpenMatrix(int idim, int jdim,
			double offset,
			boolean hasRows, boolean hasCols) {
		assert idim >= -1 && jdim >= -1;
		if (!(hasRows || hasCols)) throw new RuntimeException();
		if (offset != 0.0 && !(idim > 0 && jdim > 0)) throw new RuntimeException(); 
		this.idim = idim;
		this.jdim = jdim;
		this.offset = offset;
		if (hasRows) rows = new HashMap<>();
		if (hasCols) cols = new HashMap<>();
	}
	
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * MatrixView implementation
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public double get(int i, int j) {
        assert i >= -1 && j >= -1;
        assert idim < 0 || i < idim;
        assert jdim < 0 || j < jdim;
        double result = offset;
        if (result != 0.0) {
            if (i < 0) result *= idim;
            if (j < 0) result *= jdim;
        }
        if (i > 0) {
            result += rows.get(i).get(j);
        } else {
            for (Vector row: rows.values()) {
                result += row.get(j);
            }
        }
        return result;
    }
    
    @Override
    public VectorView getSlice(int i, int j) {
        assert (i < 0 && j >= 0) || (j < 0 && i >= 0);
        if (i < 0) {
            assert j >= 0;
            return obtainColVector(j);
        } else {
            assert j < 0;
            return obtainRowVector(i);
        }
    }

    @Override
    public Vector getRowSumColVector(
            double individualExponent, double sumExponent) {
        Vector result = new OpenVector(idim);
        for (int i: rows.keySet()) {
            double rowSum = rows.get(i).getSum(individualExponent, sumExponent);
            if (rowSum != 0.0) result.set(i, rowSum);
        }
        return result;
    }

    @Override
    public Vector getColSumRowVector(
            double individualExponent, double sumExponent) {
        Vector result = new OpenVector(jdim);
        for (int j: cols.keySet()) {
            double colSum = cols.get(j).getSum(individualExponent, sumExponent);
            if (colSum != 0.0) result.set(j, colSum);
        }
        return result;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Matrix implementation
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Override
	public OpenMatrix set(int i, int j, double newValue) {
//	    System.err.printf("set(%d, %d, %f)\n", i, j, value);
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
                synchronizeForNewCol(j);
            }
        } else {
            if (j < 0) {
                obtainRowVector(i).multiply(ALL, factor);
                synchronizeForNewRow(i);
            } else {
                set(i, j, get(i, j) * factor);
            }
        }
        return this;
    }

    @Override
    public Matrix multiplySlice(int i, int j, VectorView vector) {
        assert (i < 0 && j >= 0) || (j < 0 && i >= 0);
        if (i < 0) {
            assert i == ALL;
            assert 0 <= j && j < jdim;
            obtainColVector(j).multiplyDiagonal(vector);
            synchronizeForNewCol(j);
        } else {
            assert j == ALL;
            assert 0 <= i && i < idim;
            obtainRowVector(i).multiplyDiagonal(vector);
            synchronizeForNewRow(i);
        }
        return this;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Private methods
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Returns the i-th row vector, creating it if necessary.
	 * 
	 * @param i
	 * @return
	 */
	private OpenVector obtainRowVector(int i) {
		OpenVector result = rows.get(i);
		if (result == null) {
			result = new OpenVector(jdim);
			rows.put(i, result);
		}
		return result;
	}
	
	/**
	 * Returns the j-th column vector, creating it if necessary.
	 * 
	 * @param j
	 * @return
	 */
	private OpenVector obtainColVector(int j) {
		OpenVector result = cols.get(j);
		if (result == null) {
			result = new OpenVector(idim);
			cols.put(j, result);
		}
		return result;
	}
	
	private void synchronizeForNewRow(int i) {
	    for(Entry entry: obtainRowVector(i)) {
	        obtainColVector(entry.index).set(i, entry.value);
	    }
	}
    
    private void synchronizeForNewCol(int j) {
        for(Entry entry: obtainColVector(j)) {
            obtainRowVector(entry.index).set(j, entry.value);
        }
    }
}
