package com.github.hans_adler.nlp.vector.test;

import static org.junit.Assert.*;
import static com.github.hans_adler.nlp.vector.test.TestAxes.*;
import static com.github.hans_adler.nlp.vector.Vector.DENSE;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import com.github.hans_adler.nlp.vector.impl.*;

public class VectorTestBasic2 {
    
    VectorImpl<Axis1>   w1   = new VectorImpl<Axis1>  (axis1, 1);
    VectorImpl<Axis20>  w20a = new VectorImpl<Axis20> (axis20, 1);
    VectorImpl<Axis20>  w20b = new VectorImpl<Axis20> (axis20, 20);
    VectorImpl<Axis20>  w20c = new VectorImpl<Axis20> (axis20, DENSE);
    VectorImpl<Axis300> w300 = new VectorImpl<Axis300>(axis300, 1);

    VectorImpl<Axis1>   v1;
    VectorImpl<Axis20>  v20a;
    VectorImpl<Axis20>  v20b;
    VectorImpl<Axis20>  v20c;
    VectorImpl<Axis300> v300;

    
    @Before
    public void setUp() throws Exception {
        v1 = w1.clone();
        v20a = w20a.clone();
        v20b = w20b.clone();
        v20c = w20c.clone();
        v300 = w300.clone();
    }

    @Test
    public void testShift() {
        v20a.setValue(6, 6.0);
        v20a.setValue(5, 5.0);
        assertThat(v20a.getValue(5), is(5.0));
        assertThat(v20a.getValue(6), is(6.0));
    }

    @Test
    public void testAdd() {
        v20a.setValue(7, 7.0);
        v20a.setValue(3, 3.0);
        v20a.setValue(5, 5.0);
        assertThat(v20a.getValue(3), is(3.0));
        assertThat(v20a.getValue(5), is(5.0));
        assertThat(v20a.getValue(7), is(7.0));
        v20a.add(v20a);
        assertThat(v20a.getValue(3), is(6.0));
        assertThat(v20a.getValue(5), is(10.0));
        assertThat(v20a.getValue(7), is(14.0));
    }
}
