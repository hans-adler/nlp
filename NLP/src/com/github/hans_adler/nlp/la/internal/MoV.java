package com.github.hans_adler.nlp.la.internal;

import com.github.hans_adler.nlp.la.Axis;
import com.github.hans_adler.nlp.la.interation.Interation;

public interface MoV<A1 extends Axis> extends MoVoS {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract VoS view(int i);
    
    @SuppressWarnings("rawtypes")
    public abstract Interation indices(boolean sparse);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * GETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default double getDefaultValue() {
        return 0.0;
    }
    
    public abstract A1 getAxis1();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default void checkIndex(int i) {
        getAxis1().checkIndex(i);
    }
    
}
