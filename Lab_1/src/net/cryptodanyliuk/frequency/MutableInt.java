package net.cryptodanyliuk.frequency;

public class MutableInt {

    private int value = 0;

    public void increment() {
        ++value;
    }

    public int getValue(){
        return value;
    }

}
