package com.github.hans_adler.nlp.la.unused;

import java.util.Iterator;
import com.github.hans_adler.nlp.la.Vector;
import com.github.hans_adler.nlp.la.iteration.Entry;

public class ArrayIterator<T extends Vector<?>> extends Entry<T>
                                                implements Iterator<Entry<T>> {
    
    private T[] contentArray;
    private int[] indexArray;
    private int key = 0;
    
    public ArrayIterator(T[] contentArray, int[] indexArray) {
        this.contentArray = contentArray;
        this.indexArray = indexArray;
    }
    public ArrayIterator(T[] contentArray) {
        this.contentArray = contentArray;
    }

    @Override
    public boolean hasNext() {
        return key < contentArray.length;
    }

    @Override
    public Entry<T> next() {
        content = contentArray[key];
        if (indexArray != null) {
            index = indexArray[key];
        } else {
            index = key;
        }
        key++;
        return this;
    }

}
