package com.github.hans_adler.nlp.la;

import com.github.hans_adler.nlp.la.iteration.Entry;

public interface MutableVector<A1 extends Axis> extends Vector<A1> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * TAKERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public default MutableScalar take(int i) {
        throw new UnsupportedOperationException();
        //return (MutableScalar) view(i);v
    }
    
    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public default Iterable<Entry<MutableScalar>> takeAll(boolean sparse) {
        throw new UnsupportedOperationException();
        //return (Iterable) takeAll(sparse);
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\  
    * PASTERS
    \* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public default MutableVector<A1> paste(Vector<A1> other) {
        if (getDefaultValue() == other.getDefaultValue()) {
            
        } else {
            throw new UnsupportedOperationException();
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