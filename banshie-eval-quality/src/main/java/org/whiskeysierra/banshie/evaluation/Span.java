package org.whiskeysierra.banshie.evaluation;

public final class Span {

    private String value;
    private String type;

    private int start;
    private int end;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Span type=" + type + " '" + value + "' [" + start + ":" + end + "]";
    }

}
