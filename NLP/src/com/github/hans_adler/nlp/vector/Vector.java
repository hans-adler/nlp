package com.github.hans_adler.nlp.vector;


public interface Vector<A extends Axis> {

    public static int DENSE = Integer.MAX_VALUE;
    
    public abstract Axis   getAxis();

    public abstract double getDefaultValue();
    public abstract void   setDefaultValue(double newDefault);

    public abstract double getValue      (int i);
    public abstract void   setValue      (int i, double x);
    public abstract void   addValue      (int i, double b);
    public abstract void   multiplyValue (int i, double lambda);
    public abstract void   transformValue(int i, double lambda, double b);
}