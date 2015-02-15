package com.github.hans_adler.nlp.vector;

public class Axis {
    
    public static final int UNBOUNDED = Integer.MAX_VALUE;
    
    public final String description;
    public final int bound;
    public final boolean bounded;

    public Axis(String description, int bound) {
        if (bound <= 0) throw new IllegalArgumentException(String.format("Axis bound %s requested; should be > 0", bound));
        this.description = description == null ? getClass().getSimpleName() : description;
        this.bound = bound;
        this.bounded = bound < UNBOUNDED;
    }
    public Axis(int bound) {
        this(null, bound);
    }
    public Axis(String description) {
        this(description, UNBOUNDED);
    }
    public Axis() {
        this(null, UNBOUNDED);
    }
    
    /**
     * This method is declared final so that it can definitely be optimised
     * away when assertions are turned off, and inlined when they are turned on.
     * 
     * @param i
     */
    public final void assertRange(int i) {
        assert 0 <= i && i < bound;;
    }
    
    /**
     * Final so it can be inlined.
     * 
     * @param i
     */
    public final void checkRange(int i) {
        if (i < 0 || i >= bound) throw new RangeException(this, i);
    }
    
    @Override
    public String toString() {
        return description;
    }

    public static class RangeException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;

        public RangeException(Axis axis, int i) {
            super(String.format("Index %d not in range for axis %s (0 ... %d)",
                    i, axis, axis.bound-1));
        }

    }
}
