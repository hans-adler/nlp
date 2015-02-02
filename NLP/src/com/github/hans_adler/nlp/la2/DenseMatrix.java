package com.github.hans_adler.nlp.la2;

import java.util.Iterator;

public class DenseMatrix<Vect extends Vector, TVect extends Vector>
                    implements Iterable<Entry<Vect>>,  Matrix<Vect, TVect> {

    protected Vect[] contentArray;
    int start;
    int size;
    
    DenseMatrix<TVect, Vect> twin = new DenseMatrix<>();
    
    private DenseMatrix() {
    }
    
    public DenseMatrix(int rows, int cols) {
        contentArray = new 
    }
    
    @Override
    public Vect see(int index) {
        return contentArray[index];
    }
    @Override
    public Iterable<Entry<Vect>> seeAll() {
        return this;
    }
    @Override
    public Iterable<Entry<Vect>> seeSparse() {
        return this;
    }
    @Override
    public Iterator<Entry<Vect>> iterator() {
        return new ArrayIterator<Vect>(contentArray);
    }
    @Override
    public Matrix<TVect, Vect> seeTransposed() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean matchesOrientation(Orientation orientation) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
