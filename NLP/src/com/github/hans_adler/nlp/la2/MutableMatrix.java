package com.github.hans_adler.nlp.la2;

public interface MutableMatrix<A1 extends Axis, A2 extends Axis>
                                                     extends Matrix<A1, A2> {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract MutableMatrix<A1, A2> setValue(int i, int j, double value);

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}
