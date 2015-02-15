package com.github.hans_adler.nlp.vector.test;

import static org.junit.Assert.*;
import static com.github.hans_adler.nlp.vector.test.TestAxes.*;
import static com.github.hans_adler.nlp.vector.Vector.DENSE;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.github.hans_adler.nlp.vector.Axis;
import com.github.hans_adler.nlp.vector.Vector;
import com.github.hans_adler.nlp.vector.impl.*;
import com.github.hans_adler.nlp.vector.test.TestAxes.*;
import org.hamcrest.core.Is;
import static org.hamcrest.CoreMatchers.*;

@SuppressWarnings("unused")
public class VectorTestBasic1 {
    
    @Test(expected = NullPointerException.class)
    public void testConstructionNullAxis() {
        new VectorImpl<Axis>(null, 1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructionDenseUnbounded() {
        new VectorImpl<AxisU>(axisU, DENSE);
    }
    
    @Test
    public void testConstructionDense() {
        new VectorImpl<Axis300>(axis300, DENSE);
        new VectorImpl<Axis20>(axis20, DENSE);
        new VectorImpl<Axis1>(axis1, DENSE);
    }

    @Test
    public void testConstructionSparse() {
        new VectorImpl<AxisU>(axisU, 0);
        new VectorImpl<Axis300>(axis300, 0);
        new VectorImpl<Axis20>(axis20, 0);
        new VectorImpl<Axis1>(axis1, 0);
    }
    
    @Test
    public void testRange() {
        VectorImpl<Axis1> v1 = new VectorImpl<TestAxes.Axis1>(axis1, 0);
        v1.getValue(0);
        v1.setValue(0, 1.0);
        v1.getValue(0);
        VectorImpl<Axis20> v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        v20.getValue(0);
        v20.getValue(19);
        v20.setValue(0, 1.0);
        v20.getValue(0);
        v20.getValue(19);
        v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        v20.getValue(0);
        v20.getValue(19);
        v20.setValue(19, 1.0);
        v20.getValue(0);
        v20.getValue(19);
        VectorImpl<Axis300> v300 = new VectorImpl<TestAxes.Axis300>(axis300, 0).clone();
        v20.getValue(0);
        v20.getValue(19);
        v20.setValue(0, 1.0);
        v20.setValue(19, 1.0);
        v20.getValue(0);
        v20.getValue(19);
    }
    
    @Test(expected = Axis.RangeException.class)
    public void testRangeException1() {
        VectorImpl<Axis20> v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        v20.getValue(-1);
    }
    @Test(expected = Axis.RangeException.class)
    public void testRangeException2() {
        VectorImpl<Axis20> v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        v20.setValue(-1, 0.0);
    }
    @Test(expected = Axis.RangeException.class)
    public void testRangeException3() {
        VectorImpl<Axis20> v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        v20.getValue(20);
    }
    @Test(expected = Axis.RangeException.class)
    public void testRangeException4() {
        VectorImpl<Axis20> v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        v20.setValue(20, 0.0);
    }
    
    @Test
    public void testDefault() {
        VectorImpl<Axis20> v20 = new VectorImpl<TestAxes.Axis20>(axis20, 0);
        assertThat(v20.getValue(4), is(0.0));
        assertThat(v20.getDefaultValue(), is(0.0));
        v20.setDefaultValue(-0.5);
        assertThat(v20.getDefaultValue(), is(-0.5));
        assertThat(v20.getValue(0), is(-0.5));
    }

}
