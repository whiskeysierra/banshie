package org.whiskeysierra.banshie.evaluation.similarity;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang.StringUtils;

final class LevenshteinSimilarity implements Similarity {

    /**
     * The maximum distance between two strings to be considered
     * similar expressed in the proportion to the length of the greater
     * of the two.
     */
    private float threshold = .3f;

    @Inject(optional = true)
    public void setThreshold(@Named("similarity.levenshtein.threshold") float threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean similar(String left, String right) {
        final float distance = StringUtils.getLevenshteinDistance(left, right);
        final float length = Math.max(left.length(), right.length());

        return distance / length < threshold;
    }

}
