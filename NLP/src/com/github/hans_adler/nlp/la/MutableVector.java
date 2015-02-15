package com.github.hans_adler.nlp.la;

import com.github.hans_adler.nlp.la.interation.Interation;
import com.github.hans_adler.nlp.la.interation.Interations;
import com.github.hans_adler.nlp.vector.Axis;

public interface MutableVector<A1 extends Axis> extends Vector<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * VIEWERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public abstract Interation indices(boolean sparse);

    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * TAKERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default MutableScalar take(int i) {
        throw new UnsupportedOperationException();
        //return (MutableScalar) view(i);
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * PASTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public default MutableVector<A1> paste(Vector<A1> other) {
        boolean sparse = getDefaultValue() == other.getDefaultValue();
        for (int j: Interations.union(this.indices(sparse), other.indices(sparse))) {
            this.setValue(j, other.getValue(j));
        }
        return this;
    }

    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * SETTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    
    public abstract MutableVector<A1> setValue(int j, double value);
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * ACTIONS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}
