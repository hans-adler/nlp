package com.github.hans_adler.nlp.la2.implementation;

import com.github.hans_adler.nlp.la2.MutableScalar;
import com.github.hans_adler.nlp.la2.Scalar;


public class ConcreteScalar implements MutableScalar {
    
    public static Scalar ZERO       = new ConcreteScalar( 0.0);
    public static Scalar ONE        = new ConcreteScalar( 1.0);
    public static Scalar MINUS_ONE  = new ConcreteScalar(-1.0);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * FIELDS / CONSTRUCTORS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected double value;
    
    public ConcreteScalar(double value) {
        this.value = value;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public double getValue() {
        return value;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Override
    public MutableScalar setValue(double value) {
        this.value = value;
        return this;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}
