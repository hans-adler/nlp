package com.github.hans_adler.nlp.la.implementation;

import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;
import com.github.hans_adler.nlp.la.Scalar;
import com.github.hans_adler.nlp.la.iteration.Entry;
import com.github.hans_adler.nlp.la.test.XAxis;
import com.github.hans_adler.nlp.la.test.YAxis;

public class SparseVectorTest {
    
    void assertIdentical(double a, double b) {
        assertEquals(a, b, 0);
    }

    @Test
    public void testSparseVector() {
        new SparseVector<XAxis>(XAxis.OBJECT);
        new SparseVector<YAxis>(YAxis.OBJECT);
    }

    @Test
    public void testSeeAll() {
        // Iterator with no elements
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        for (@SuppressWarnings("unused") int j: v.indices(true)) {
            fail();
        }
        
        // Iterator with one element
        v.setValue(4,4);
        for (int j: v.indices(true)) {
            assertEquals(4, j);
            assertIdentical(4, v.getValue(j));
        }
        
        // Iterator with two elements
        v.setValue(2, 2);
        Iterator<Integer> i = v.indices(true).iterator();
        assertTrue(i.hasNext());
        int j = i.next();
        assertEquals(2, j);
        assertIdentical(2, v.getValue(j));
        assertTrue(i.hasNext());
        j = i.next();
        assertEquals(4, j);
        assertIdentical(4, v.getValue(j));
        assertFalse(i.hasNext());
    }

    @Test
    public void testSee() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        v.view(3);
    }

    @Test
    public void testGetValue() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        assertIdentical(0, v.getValue(15));
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
        assertIdentical(12, v.getValue(3));
        for (int i = 0; i < 100; i+=5) {
            v.setValue(i, i);
        }
        assertIdentical(50, v.getValue(50));
    }
    
    @Test
    public void testPaste() {
        SparseVector<XAxis> v = new SparseVector<XAxis>(XAxis.OBJECT);
        SparseVector<XAxis> w = new SparseVector<XAxis>(XAxis.OBJECT);
        w.setValue(7, 7);
        v.setValue(19, 19);
        v.setValue(12, 12);
        v.paste(w);
        assertIdentical(7, v.getValue(7));
        assertIdentical(12, v.getValue(12));
        assertIdentical(19, v.getValue(19));
        v.setValue(8, 8);
        assertIdentical(7, v.getValue(7));
        assertIdentical(8, v.getValue(8));
        assertIdentical(12, v.getValue(12));
        assertIdentical(19, v.getValue(19));
        w.setValue(12, 12.5);
        w.setValue(2,2);
        assertIdentical(12.5, w.getValue(12));
        assertIdentical(2, w.getValue(2));
        v.paste(w);
        assertIdentical(2, v.getValue(2));
        assertIdentical(7, v.getValue(7));
        assertIdentical(8, v.getValue(8));
        assertIdentical(12.5, v.getValue(12));
        assertIdentical(19, v.getValue(19));
    }

}
