package com.github.hans_adler.nlp.la2;

import static com.github.hans_adler.nlp.la2.Vector.RowVector;
import static com.github.hans_adler.nlp.la2.Vector.ColVector;

/**
 * <p>Aspect methods: Methods starting with "see" provide a live view into an
 * aspect of the object. Such methods do not have external side effects.</p>
 * 
 * <p>Getters: Methods starting with "get" return an atomic value or provide a
 * new copy.
 * Methods starting with a verb in third person indicative mood return
 * a boolean.
 * Methods of these categories do not have external side effects.</p>
 * 
 * <p>Actions: Methods starting with any other infinitive modify the object and
 * return the object itself for chaining.</p>
 * 
 * @author Hans Adler (johannes.aquila@gmail.com)
 *
 */

public interface Matrix<Vect extends Vector, TVect extends Vector> {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract Matrix<TVect, Vect> seeTransposed();
    
    public default Dimension seeDimension() {
        return Dimension.INFINITE;
    }
    
    public abstract Vect see(int index);
    
    public abstract Iterable<Entry<Vect>> seeAll();
    public abstract Iterable<Entry<Vect>> seeSparse();
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract boolean matchesOrientation(Orientation orientation);
    
    public default double getValue(int i, int j) {
        return see(i).getValue(j);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SUBINTERFACES
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static interface RowMatrix extends Matrix<RowVector, ColVector> {
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
         * ASPECTS
        \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        @Override
        public abstract Matrix<ColVector, RowVector> seeTransposed();
         
        @Override
        public default Dimension seeDimension() {
             return Dimension.INFINITE;
        }
         
        @Override
        public abstract RowVector see(int index);
         
        @Override
        public abstract Iterable<Entry<RowVector>> seeAll();
        @Override
        public abstract Iterable<Entry<RowVector>> seeSparse();
         
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
         * GETTERS
        \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        @Override
        public default boolean matchesOrientation(Orientation orientation) {
             return orientation == Orientation.VERTICAL || seeDimension().size == 0;
        }
         
        @Override
        public default double getValue(int i, int j) {
             return see(i).getValue(j);
        }
    }

    public static interface ColMatrix extends Matrix<ColVector, RowVector> {
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
         * ASPECTS
        \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        @Override
        public abstract Matrix<RowVector, ColVector> seeTransposed();
         
        @Override
        public default Dimension seeDimension() {
             return Dimension.INFINITE;
        }
         
        @Override
        public abstract ColVector see(int index);
         
        @Override
        public abstract Iterable<Entry<ColVector>> seeAll();
        @Override
        public abstract Iterable<Entry<ColVector>> seeSparse();
         
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
         * GETTERS
        \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        @Override
        public default boolean matchesOrientation(Orientation orientation) {
             return orientation == Orientation.HORIZONTAL || seeDimension().size == 0;
        }
         
        @Override
        public default double getValue(int i, int j) {
             return see(i).getValue(j);
        }
    }

}
