package com.github.hans_adler.nlp.la.interation;

public class Interations {

    private Interations() {}

    public static Interation union(Interation one, Interation two) {
        return new Union(one, two);
    }

    public static class Union extends AbstractInteration {

        private final Interation i1;
        private final Interation i2;

        protected Union(Interation i1, Interation i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        @Override
        public void skip(int i) {
            i1.skip(i);
            i2.skip(i);
            future = Math.min(i1.future(), i2.future());
        }
        
    }

    public class Intersection extends AbstractInteration {

        private final Interation i1;
        private final Interation i2;

        protected Intersection(Interation i1, Interation i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        @Override
        public void skip(int i) {
            if (i <= future) return;
            i1.skip(i);
            while (true) {
                i2.skip(future = i1.future());
                if (i2.future() == future) return;
                i1.skip(future = i2.future());
                if (i1.future() == future) return;
            }
        }

    }
}
