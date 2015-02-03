package com.github.hans_adler.nlp.la2;

import com.github.hans_adler.nlp.la2.implementation.ConcreteScalar;
import com.github.hans_adler.nlp.la2.internal.VoS;

public interface Scalar extends VoS {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * VIEWERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract double getValue();


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * FACTORY
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public static MutableScalar create() {
        return new ConcreteScalar(0.0);
    }

}
