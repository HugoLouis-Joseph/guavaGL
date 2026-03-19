package com.google.common.math.modes;

public class Down extends ModeToRound {
    public Down() {}
    
    public int log2(long x) {
        return (Long.SIZE - 1) - Long.numberOfLeadingZeros(x);
    }
}