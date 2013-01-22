package org.whiskeysierra.banshie.evaluation;

final class SimpleValue implements Value {

    private final float value;

    SimpleValue(float value) {
        this.value = value;
    }

    @Override
    public float getValue() {
        return value;
    }

}
