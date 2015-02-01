package com.github.hans_adler.nlp.la.matrix;

import java.util.HashMap;
import java.util.Map;
import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.vector.OpenVector;
import com.github.hans_adler.nlp.la.vector.Vector;
import com.github.hans_adler.nlp.la.vector.VectorView;

public class OpenMatrixView implements MatrixView {

   
    protected Map<Integer, OpenVector> rows;
    protected Map<Integer, OpenVector> cols;
    protected double offset;
    protected int idim;
    protected int jdim;
    
    public OpenMatrixView() {
        this(ALL, ALL, 0.0, true, true);
    }
    /**
     * @param idim
     * @param jdim
     * @param defaultValue
     */
    protected OpenMatrixView(int idim, int jdim,
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
    public int[] getDimensions() {
        return new int[] { idim, jdim };
    }
    
    @Override
    public double getDefaultValue() {
        return offset;
    }
    
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Iterable<Map.Entry<Integer, ? extends VectorView>> getRows() {
        return (Iterable) rows.entrySet();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Iterable<Map.Entry<Integer, ? extends VectorView>> getCols() {
        return (Iterable) cols.entrySet();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * Protected methods
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the i-th row vector, creating it if necessary.
     * 
     * @param i
     * @return
     */
    protected OpenVector obtainRowVector(int i) {
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
    protected OpenVector obtainColVector(int j) {
        OpenVector result = cols.get(j);
        if (result == null) {
            result = new OpenVector(idim);
            cols.put(j, result);
        }
        return result;
    }
    
    protected void synchronizeColsForRow(int i) {
        for(Entry entry: obtainRowVector(i)) {
            obtainColVector(entry.index).set(i, entry.value);
        }
    }
    
    protected void synchronizeRowsForNewCol(int j) {
        for(Entry entry: obtainColVector(j)) {
            obtainRowVector(entry.index).set(j, entry.value);
        }
    }

}
