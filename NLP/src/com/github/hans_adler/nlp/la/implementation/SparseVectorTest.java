package com.github.hans_adler.nlp.la.implementation;

import static org.junit.Assert.*;
import org.junit.Test;
import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.test.XAxis;
import com.github.hans_adler.nlp.la.test.YAxis;

public class SparseVectorTest {

    @Test
    public void testSparseVector() {
        new SparseVector(XAxis.OBJECT);
        new SparseVector(YAxis.OBJECT);
    }

    @Test
    public void testSeeAll() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        for (Entry<?> entry: v.viewAll(true)) {System.out.println("*****");}
    }

    @Test
    public void testSee() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        v.view(3);
    }

    @Test
    public void testGetValue() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        assertEquals(0, v.getValue(15), 0.000001);
    }

    @Test
    public void testGetAxis1() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        assertEquals(XAxis.OBJECT, v.getAxis1());
    }

    @Test
    public void testSetValue() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        v.setValue(3,  12);
        assertEquals(12, v.getValue(3), 0.00000001);
        for (int i = 0; i < 100; i+=5) {
            v.setValue(i, i);
        }
        assertEquals(50, v.getValue(50), 0.00000001);
    }

}
