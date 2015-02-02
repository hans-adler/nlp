package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.implementation.ConcreteScalar;
import com.github.hans_adler.nlp.la2.internal.VoS;

public interface Scalar extends VoS {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract double getValue();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SCALAR FACTORY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public static MutableScalar create() {
        return new ConcreteScalar(0.0);
    }

}
