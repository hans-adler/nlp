package com.github.hans_adler.nlp.la2;

public class Axis {
    
    public static int UNBOUNDED = Integer.MAX_VALUE;
    
    public final String description;
    public final int bound;
    public final boolean bounded;

    public Axis(String description, int bound) {
        assert description != null;
        assert bound > 0;
        this.description = description;
        this.bound = bound;
        this.bounded = bound < UNBOUNDED;
    }
    
    /**
     * This method is declared final so that it can definitely be optimised
     * away when assertions are turned off, and inlined when the are turned on.
     * 
     * @param index
     */
    public final void checkIndex(int index) {
        assert 0 <= index && index < bound;;
    }

}
