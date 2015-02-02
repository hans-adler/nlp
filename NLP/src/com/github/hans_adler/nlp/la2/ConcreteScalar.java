package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.core.Scalar;


public class ConcreteScalar implements Scalar {
    
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
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public double getValue() {
        return value;
    }

}
