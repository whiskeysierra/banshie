package org.whiskeysierra.banshie.evaluation.similarity;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class SimilarityModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Similarity.class).to(JaccardSimilarity.class).in(Singleton.class);
    }

}
