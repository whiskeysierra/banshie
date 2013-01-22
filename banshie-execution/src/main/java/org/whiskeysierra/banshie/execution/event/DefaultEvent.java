package org.whiskeysierra.banshie.execution.event;

import java.util.Date;

final class DefaultEvent implements Event {

    private Date date;
    private String key;
    private long value;

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
