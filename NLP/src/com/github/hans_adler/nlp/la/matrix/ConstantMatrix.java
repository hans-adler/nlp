package com.github.hans_adler.nlp.la.matrix;

import java.util.Collections;


public class ConstantMatrix extends OpenMatrixView {
    
    public static final ConstantMatrix ZERO = new ConstantMatrix(ALL, ALL, 0.0);
    public static final ConstantMatrix ONE  = new ConstantMatrix(ALL, ALL, 1.0);
    
    public ConstantMatrix(int idim, int jdim, double value) {
        super(idim, jdim, value, false, false);
        rows = Collections.emptyMap();
        cols = Collections.emptyMap();
    }

}
