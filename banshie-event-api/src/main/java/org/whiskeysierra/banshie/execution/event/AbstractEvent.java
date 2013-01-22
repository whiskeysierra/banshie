package org.whiskeysierra.banshie.execution.event;

public abstract class AbstractEvent implements Event {

    private long time;
    private long value;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }

}
