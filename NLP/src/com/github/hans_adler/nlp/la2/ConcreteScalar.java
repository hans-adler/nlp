package com.github.hans_adler.nlp.la2;


public class ConcreteScalar extends Entry<Scalar> implements Scalar {
    
    public static Scalar ZERO       = new ConcreteScalar( 0.0);
    public static Scalar ONE        = new ConcreteScalar( 1.0);
    public static Scalar MINUS_ONE  = new ConcreteScalar(-1.0);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * FIELDS / CONSTRUCTORS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected double value;
    
    public ConcreteScalar(double value) {
        this.index = 0;
        this.content = this;
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
