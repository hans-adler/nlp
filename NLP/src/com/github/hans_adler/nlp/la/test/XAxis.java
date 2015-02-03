package com.github.hans_adler.nlp.la.test;

import com.github.hans_adler.nlp.la.Axis;

public class XAxis extends Axis {
    
    public static final XAxis OBJECT = new XAxis();

    public XAxis() {
        super("x-Achse", UNBOUNDED);
    }

}
