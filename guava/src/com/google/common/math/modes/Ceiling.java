package com.google.common.math.modes;

public class Ceiling extends ModeToRound {
    public Ceiling() {}
    
    public int log2(long x) {
        return Long.SIZE - Long.numberOfLeadingZeros(x - 1);
    }
}