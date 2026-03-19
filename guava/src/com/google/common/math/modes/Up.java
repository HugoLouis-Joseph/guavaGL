package com.google.common.math.modes;

public class Up extends ModeToRound {
    public Up() {}
    
    public int log2(long x) {
        return Long.SIZE - Long.numberOfLeadingZeros(x - 1);
    }
}