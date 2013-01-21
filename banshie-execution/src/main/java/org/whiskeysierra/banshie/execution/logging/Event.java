package org.whiskeysierra.banshie.execution.logging;

import java.util.Date;

public final class Event {

    private Date date;
    private String key;
    private long value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
