package com.myexaminer.modelDTO;

public class GenericTwoValues {
    private Object firstValue;

    private Object secondValue;

    public Object getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(Object firstValue) {
        this.firstValue = firstValue;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }

    public GenericTwoValues(Object firstValue, Object secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public GenericTwoValues() {
    }
}
