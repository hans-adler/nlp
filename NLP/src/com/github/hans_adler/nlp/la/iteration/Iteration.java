package com.github.hans_adler.nlp.la.iteration;

import java.util.Iterator;

/**
 * Interface for one-time Iterables that return themselves as iterator.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 * @param <T>
 */
public interface Iteration<T> extends Iterable<T>, Iterator<T> {
    
    @Override
    public default Iteration<T> iterator() {
        return this;
    }

}
