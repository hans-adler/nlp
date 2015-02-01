package com.github.hans_adler.nlp.la.vector;


public class ConstantVector implements VectorView {
    
    private final double value;
    private final int    dimension;
    
    public static final ConstantVector ZERO = new ConstantVector(0.0);
    public static final ConstantVector ONE  = new ConstantVector(1.0);

    public ConstantVector(double value, int dimension) {
        this.value = value;
        this.dimension = dimension;
    }
    
    public ConstantVector(double value) {
        this(value, ALL);
    }

    public ConstantVector(int dimension) {
        this(0.0, dimension);
    }

    public ConstantVector() {
        this(0.0, ALL);
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
