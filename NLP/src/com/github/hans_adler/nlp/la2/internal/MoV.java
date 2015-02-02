package com.github.hans_adler.nlp.la2.internal;

import com.github.hans_adler.nlp.la2.Axis;

public interface MoV<A1 extends Axis> extends MoVoS {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ASPECTS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract VoS see(int i);
    
    @SuppressWarnings("rawtypes")
    public abstract Iterable seeAll(boolean sparse);
    
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
