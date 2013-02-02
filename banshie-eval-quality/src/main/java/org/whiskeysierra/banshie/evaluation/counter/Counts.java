package org.whiskeysierra.banshie.evaluation.counter;

public interface Counts {

    int getTruePositives();

    int getFalsePositives();

    int getFalseNegatives();

    int getSubstitutionErrors();

    int getDeletionErrors();

    int getInsertionErrors();

}
