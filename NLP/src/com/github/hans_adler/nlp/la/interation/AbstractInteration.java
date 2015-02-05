package com.github.hans_adler.nlp.la.interation;


public abstract class AbstractInteration<T extends AbstractInteration<T>> 
                implements Interation<T> {
    
    protected int index = Integer.MIN_VALUE;
    protected int futureIndex = Integer.MIN_VALUE;
    
    @Override
    public abstract void advance();

    @Override
    public final Interation<T> iterator() {
        return this;
    }
    
    @Override
    public final Interation<T> next() {
        advance();
        return this;
    }
    
    @Override
    public final boolean hasNext() {
        return futureIndex < Integer.MIN_VALUE;
    }

    @Override
    public final int index() {
        return index;
    }

    @Override
    public final int future() {
        return futureIndex;
    }

}
