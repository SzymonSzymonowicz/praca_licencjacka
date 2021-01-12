package com.myexaminer.modelDTO;

public class GenericOneValue {
    private Object firstValue;

    public Object getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(Object firstValue) {
        this.firstValue = firstValue;
    }

    public GenericOneValue(Object firstValue) {
        this.firstValue = firstValue;
    }

    public GenericOneValue() {
    }
}
