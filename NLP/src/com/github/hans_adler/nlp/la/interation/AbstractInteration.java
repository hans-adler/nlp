package com.github.hans_adler.nlp.la.interation;

import java.util.Iterator;
import java.util.NoSuchElementException;


public abstract class AbstractInteration
                implements Interation {
    
    protected int index = Integer.MIN_VALUE;
    protected int future = Integer.MIN_VALUE;
    
    
    /*
     *  The only interface method that can be implemented / needs implementing.
     */
    @Override
    public abstract void skip(int i);

    
    
    @Override
    public final Iterator<Integer> iterator() {
        return this;
    }
    
    @Override
    public final Integer next() {
        System.out.println("next(): " + this);
        advance();
        return index();
    }
    
    @Override
    public final boolean hasNext() {
        System.out.println("hasNext(): " + this);
        return future < Integer.MAX_VALUE;
    }

    @Override
    public final int index() {
        System.out.println("index(): " + this);
        return index;
    }

    @Override
    public final int future() {
        System.out.println("future(): " + this);
        return future;
    }

    @Override
    public final void advance() {
        assert future > index || (future == index && index == Integer.MIN_VALUE);
        if (future == Integer.MAX_VALUE) throw new NoSuchElementException();
        int newIndex = future;
        skip(future+1);
        index = newIndex;
    }

}
