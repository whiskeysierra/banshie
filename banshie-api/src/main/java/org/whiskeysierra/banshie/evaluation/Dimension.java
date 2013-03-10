package org.whiskeysierra.banshie.evaluation;

public enum Dimension {

    // runtime performance
    CPU_TIME("CPU time"),
    MEMORY_CONSUMPTION("Memory consumption"),
    TIME("Execution time"),

    // quality
    PRECISION("Precision"),
    RECALL("Recall"),
    F_MEASURE("F-measure"),
    ACCUARCY("Accuracy"),

    // error measures
    ERROR_MEASURE("Error measure"),
    ERROR_PER_RESPONSE_FILL("Error per response fill"),
    SLOT_ERROR_RATE("Slot error rate");

    private final String name;

    private Dimension(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
