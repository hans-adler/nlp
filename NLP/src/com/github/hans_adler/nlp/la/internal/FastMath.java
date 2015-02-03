package com.github.hans_adler.nlp.la.internal;

/**
 * For the fast inaccurate functions, see
 * http://martin.ankerl.com/2007/10/04/optimized-pow-approximation-for-java-and-c-c/
 * and references there.
 * 
 * @author Hans Adler (johannes.aquila@gmail.com) 2015
 *
 */
public final class FastMath {

    private FastMath() {}
    
    /**
     * <p>Variant of {@link java.lang.Math#pow(double, double)} that returns
     * precise results for various frequently occurring values of b
     * and should be faster.</p>
     * 
     * <p>If the result is mathematically undefined or infinite, the behaviour
     * is undefined. In particular, there is no guarantee that an exception
     * is thrown or NaN returned.</p>
     * 
     * @param a
     * @param b
     * @return Exactly the same result as Math.pow(a, b).
     */
    public static double pow(final double a, final double b) {
        if (b == 0.0) return 1.0;
        if (b == 1.0) return b;
        if (b == 2.0) return b*b;
        if (b == -1.0) return 1.0/b;
        if (a == 0.0) return 0.0;
        if (a == 1.0) return 1.0;
        if (b == 0.5) return Math.sqrt(a);
        if (b == 3.0) return b*b*b;
        return Math.pow(a, b);
    }
    
    public static double inaccurate_exp(double val) {
        final long tmp = (long) (1512775 * val) + (1072693248 - 60801);
        return Double.longBitsToDouble(tmp << 32);
    }
    
    public double inaccurate_ln(double val) {
        final double x = (Double.doubleToLongBits(val) >> 32);
        return (x - 1072632447) / 1512775;
    }
    
//    public static double pow(final double a, final double b) {
//        final int x = (int) (Double.doubleToLongBits(a) >> 32);
//        final int y = (int) (b * (x - 1072632447) + 1072632447);
//        return Double.longBitsToDouble(((long) y) << 32);
//    }
    
    public static double inaccurate_pow(final double a, final double b) {
        final long tmp = Double.doubleToLongBits(a);
        final long tmp2 = (long)(b * (tmp - 4606921280493453312L)) + 4606921280493453312L;
        return Double.longBitsToDouble(tmp2);
    }
    
//    public static void main(String[] args) {
//        for (double i = 0; i < 10001; i+= 100) {
//            System.out.println(Math.pow(i,3.2));
//            System.out.println(pow1(i,3.2));
//            System.out.println(pow2(i,3.2));
//            System.out.println();
//        }
//    }
    
}
