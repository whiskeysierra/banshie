package org.whiskeysierra.banshie.evaluation;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.whiskeysierra.banshie.evaluation.counter.CounterModule;
import org.whiskeysierra.banshie.evaluation.score.ScoreModule;
import org.whiskeysierra.banshie.evaluation.similarity.SimilarityModule;

import javax.xml.stream.XMLInputFactory;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class QualityEvaluationModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new CounterModule());
        install(new ScoreModule());
        install(new SimilarityModule());

        bind(QualityEvaluator.class).to(DefaultQualityEvaluator.class).in(Singleton.class);
        bind(export(QualityEvaluator.class)).toProvider(service(QualityEvaluator.class).export());
    }

    @Provides
    @Singleton
    public XMLInputFactory provideXmlInputFactory() {
        return XMLInputFactory.newFactory();
    }

}
