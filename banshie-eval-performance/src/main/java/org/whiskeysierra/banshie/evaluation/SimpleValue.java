package org.whiskeysierra.banshie.evaluation;

final class SimpleValue implements Value {

    private final double value;

    SimpleValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
