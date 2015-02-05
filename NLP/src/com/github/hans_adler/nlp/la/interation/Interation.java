package com.github.hans_adler.nlp.la.interation;

import java.util.Iterator;

/**
 * Interface for one-time Iterables that return themselves as iterator and
 * have a method for accessing an int index. This is a scheme for iterating over
 * huge sequences of ints without creating mountains of short-lived Integer
 * objects. Moreover, the iterator always returns itself with the next()
 * method.
 * 
 * In order to facilitate subclasses with an additional payload that varies
 * with the index and can be accessed through the Interation more quickly than
 * by normal indexing, Interations also have a future() method for a
 * one-element look-ahead. This permits chaining of sparse Interation.
 * 
 * The contract for Interation includes the following points:
 * 
 * It returns a sequence of ints that is strictly increasing.
 * Calling advance() or next() has exactly the same effect, except that next()
 * also returns the Interation itself.
 * Before advance()/next() is called for the first time, index() returns
 * Integer.MIN_VALUE and future() returns the first int of the sequence
 * or Integer.MAX_VALUE if the sequence is empty.
 * After the last element of the sequence we get one or two instances of
 * Integer.MAX_VALUE. (Which is better?) A further call throws an
 * IllegalStateException.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 * @param <T>
 */
public interface Interation<T extends Interation<T>> extends Iterator<Interation<T>>, Iterable<Interation<T>> {

    @Override
    public default Interation<T> iterator() {
        return this;
    }
    
    @Override
    public default Interation<T> next() {
        advance();
        return this;
    }
    
    @Override
    public abstract boolean hasNext();
    
    public abstract int index();
    public abstract int future();

    abstract void advance();
        
}
