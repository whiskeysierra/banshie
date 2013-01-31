package org.whiskeysierra.banshie.evaluation;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Set;

final class JaccardSimilarity implements Similarity {

    private final Splitter splitter = Splitter.on(CharMatcher.ANY);

    private float threshold = .3f;

    @Inject(optional = true)
    public void setThreshold(@Named("similarity.jaccard.threshold") float threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean similar(String left, String right) {
        final Set<String> s1 = tokenSet(left);
        final Set<String> s2 = tokenSet(right);

        final float match = Sets.intersection(s1, s2).size();
        final float total = Sets.union(s1, s2).size() - match;

        final float proxmity = match / total;
        final float distance = 1 - proxmity;

        return distance < threshold;
    }

    private Set<String> tokenSet(String s) {
        return Sets.newHashSet(splitter.split(s));
    }

}
