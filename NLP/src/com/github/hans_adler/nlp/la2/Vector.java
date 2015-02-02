package com.github.hans_adler.nlp.la2;


public interface Vector extends Matrix<Scalar, Scalar> {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract Vector seeTransposed();
    
    @Override
    public default Dimension seeDimension() {
        return Dimension.INFINITE;
    }
    
    @Override
    public abstract Scalar see(int j);
    
    @Override
    public abstract Iterable<Entry<Scalar>> seeAll();
    @Override
    public abstract Iterable<Entry<Scalar>> seeSparse();
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract boolean matchesOrientation(Orientation orientation);
    
    public default double getValue(int j) {
        return see(j).getValue();
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SUBINTERFACES
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static interface RowVector extends Vector {
        @Override
        public default boolean matchesOrientation(Orientation orientation) {
            return orientation == Orientation.HORIZONTAL || seeDimension().size == 0;
        }
    }

    public static interface ColVector extends Vector {
        @Override
        public default boolean matchesOrientation(Orientation orientation) {
            return orientation == Orientation.VERTICAL || seeDimension().size == 0;
        }
    }

}
