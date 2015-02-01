package com.github.hans_adler.nlp.la.vector;


public class ConstantVector implements VectorView {
    
    private final int    dimension;
    private final double value;
    
    public static final ConstantVector ZERO = new ConstantVector();
    public static final ConstantVector ONE  = new ConstantVector(ALL, 1.0);

    public ConstantVector(int dimension, double value) {
        this.dimension = dimension;
        this.value = value;
    }
    
    public ConstantVector(int dimension) {
        this(dimension, 0.0);
    }

    public ConstantVector() {
        this(ALL, 0.0);
    }

    @Override
    public double get(int index) {
        return value;
    }
    
    @Override
    public int getDimension() {
        return dimension;
    }

}
