package org.whiskeysierra.banshie.execution.event;

import org.codehaus.jackson.annotate.JsonProperty;

public final class CpuTimeEvent extends AbstractEvent {

    private long systemTime;

    @JsonProperty("cpus")
    private int availableProcessors;

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

}
