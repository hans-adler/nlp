package com.github.hans_adler.nlp.la2;

public class Axis {
    
    public static final int UNBOUNDED = Integer.MAX_VALUE;
    
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

}
