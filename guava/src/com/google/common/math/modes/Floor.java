package com.google.common.math.modes;

public class Floor extends ModeToRound {
    public Floor() {}
    
    public int log2(long x) {
        return (Long.SIZE - 1) - Long.numberOfLeadingZeros(x);
    }
}