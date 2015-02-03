package com.github.hans_adler.nlp.la.iteration;

import java.util.Iterator;
import com.github.hans_adler.nlp.la.internal.VoS;


public final class Iterations {
    
    private Iterations() {
    }
    
    public static <T1 extends VoS, T2 extends VoS> 
    Iteration<EntryPair<T1, T2>> intersect(
            Iterator<Entry<T1>> one,
            Iterator<Entry<T2>> two
    ) {
        return new IntersectionIteration<T1, T2>(one, two);
    }
    
    public static <T1 extends VoS, T2 extends VoS> 
    Iteration<EntryPair<T1, T2>> union(
            Iterator<Entry<T1>> one,
            Iterator<Entry<T2>> two
    ) {
        return new UnionIteration<T1, T2>(one, two);
    }
    
}
