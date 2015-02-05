package com.github.hans_adler.nlp.la.interation;

import java.util.Iterator;

/**
 * Interface for one-time Integer Iterable objects that return themselves
 * as Iterator objects.
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
 * In the last iteration step (when hasNext() returns true), future() is
 * Integer.MAX_VALUE. A further call to advance() or next() then throws a
 * NoSuchElementException.
 * 
 * TODO: Add reset() method so these things can be reused.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 */
public interface Interation extends Iterator<Integer>, Iterable<Integer> {
    
    @Override
    public default Iterator<Integer> iterator() {
        return this;
    }
    
    @Override
    public default Integer next() {
        advance();
        return index();
    }
    
    @Override
    public abstract boolean hasNext();
    
    /**
     * Current index.
     * 
     * @return Current index.
     */
    public abstract int index();
    /**
     * Next index. It will become the current index through a call to
     * {@link #advance()} or {@link #next()}. The user can change the future
     * index by calling {@link #skip(int)}.
     * 
     * Once we have reached the last element, future() returns
     * Integer.MAX_VALUE.
     * 
     * @return Next index.
     */
    public abstract int future();

    /**
     * Set the current index to what is now the future index. Then advance
     * the future index (using future = skip(future) or some optimised
     * version of this) so it is again greater than the current index.
     */
    abstract void advance();
    /**
     * Instructs the Interator to skip any remaining indices before i.
     * Obviously, for this to be useful, i must be greater than or equal to
     * the current index.
     * 
     * In particular, skip(future()) does nothing, and the same is true
     * for skip(0) or skip(index()). Using skip(Integer.MAX_VALUE) one
     * can make the Interation stop after the current step.
     * 
     * @param i A new inclusive lower bound for future indices.
     */
    abstract void skip(int i);
    
}
