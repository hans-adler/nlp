package com.github.hans_adler.nlp.vector.test;

import com.github.hans_adler.nlp.vector.Axis;

public final class TestAxes {
    
    public static final Axis1   axis1   = new Axis1();
    public static final Axis20  axis20  = new Axis20();
    public static final Axis300 axis300 = new Axis300();
    public static final AxisU   axisU   = new AxisU();
    public static final AxisV   axisV   = new AxisV();
    
    /////////////////////////////////////////////////////////////////
    // From here on implementation details
    
    private TestAxes() {throw new RuntimeException();}

    public static final class Axis1 extends Axis {
        private Axis1() {
            super("Axis1", 1);
        }
    }

    public static final class Axis20 extends Axis {
        private Axis20() {
            super("Axis20", 20);
        }
    }

    public static final class Axis300 extends Axis {
        private Axis300() {
            super("Axis300", 300);
        }
    }

    public static final class AxisU extends Axis {
        private AxisU() {}
    }

    public static final class AxisV extends Axis {
        private AxisV() {}
    }
}
