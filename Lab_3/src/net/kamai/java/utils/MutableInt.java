package net.kamai.java.utils;

public class MutableInt {

    private int value = 0;

    public void increment() {
        ++value;
    }

    public int getValue(){
        return value;
    }

}
