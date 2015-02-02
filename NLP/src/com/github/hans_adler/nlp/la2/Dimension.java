package com.github.hans_adler.nlp.la2;

public class Dimension {
    
    public static final Dimension ZERO     = new Dimension(0);
    public static final Dimension ONE      = new Dimension(1);
    public static final Dimension TWO      = new Dimension(2);
    public static final Dimension INFINITE = new Dimension();
    
    public final int size;
    public final boolean finite;
    
    public Dimension(int size) {
        this.size = size;
        this.finite = (size < Integer.MAX_VALUE);
    }
    public Dimension() {
        this(Integer.MAX_VALUE);
    }
    

}
