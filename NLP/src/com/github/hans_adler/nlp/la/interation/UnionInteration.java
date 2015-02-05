package com.github.hans_adler.nlp.la.interation;

public class UnionInteration<T1 extends Interation<T1>, T2 extends Interation<T2>>
       extends AbstractInteration<UnionInteration<T1, T2>> {

    private final Interation<T1> i1;
    private final Interation<T2> i2;
    
    protected UnionInteration(Interation<T1> i1, Interation<T2> i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    @Override
    public void advance() {
        assert index == Math.min(i1.index(), i2.index());
        long diff = i1.index() - i2.index();
        if (diff <= 0) i1.advance();
        if (diff >= 0) i2.advance();
        index = futureIndex;
        assert index == Math.min(i1.index(), i2.index());
        futureIndex = Math.min(i1.future(), i2.future());
    }

}
