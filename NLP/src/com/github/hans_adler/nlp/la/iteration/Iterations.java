package com.github.hans_adler.nlp.la.iteration;

import com.github.hans_adler.nlp.la.MutableScalar;
import com.github.hans_adler.nlp.la.MutableVector;
import com.github.hans_adler.nlp.la.Scalar;
import com.github.hans_adler.nlp.la.Vector;
import com.github.hans_adler.nlp.la.internal.VoS;


public final class Iterations {
    
    private Iterations() {
    }
    
    public static <T1 extends VoS, T2 extends VoS> 
    Iteration<EntryPair<T1, T2>> intersect(
            Iterable<Entry<T1>> one,
            Iterable<Entry<T2>> two
    ) {
        return new IntersectionIteration<T1, T2>(one.iterator(), two.iterator());
    }
    
    public static <T1 extends VoS, T2 extends VoS> 
    Iteration<EntryPair<T1, T2>> union(
            Iterable<Entry<T1>> one,
            Iterable<Entry<T2>> two
    ) {
        return new UnionIteration<T1, T2>(one.iterator(), two.iterator());
    }

    public static <T1 extends MutableVector<?>, T2 extends Vector<?>> 
    Iteration<EntryPair<MutableScalar, Scalar>> union(
            T1 vector1,
            T2 vector2,
            boolean sparse
    ) {
        return new UnionIteration<MutableScalar, Scalar>(vector1.takeAll(sparse).iterator(), vector2.viewAll(sparse).iterator());
    }

}
