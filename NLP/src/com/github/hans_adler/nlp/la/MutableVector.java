package com.github.hans_adler.nlp.la;

import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.iteration.EntryPair;
import com.github.hans_adler.nlp.la.iteration.Iterations;

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
        for (EntryPair<MutableScalar, Scalar> pair: 
                Iterations.union(this, other, getDefaultValue() == other.getDefaultValue())) {
            if (pair.one != null) {
                if (pair.two != null) {
                    pair.one.content.setValue(pair.two.content.getValue());
                } else {
                    pair.one.content.setValue(other.getDefaultValue());
                }
            } else {
                this.setValue(pair.index, pair.two.content.getValue());
            }
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
