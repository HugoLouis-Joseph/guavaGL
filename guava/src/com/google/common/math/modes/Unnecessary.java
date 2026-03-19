package com.google.common.math.modes;

public class Unnecessary extends ModeToRound {
    public Unnecessary() {}
    
    public int log2(long x) {
        checkRoundingUnnecessary(isPowerOfTwo(x));
        return (Long.SIZE - 1) - Long.numberOfLeadingZeros(x);
    }
}
