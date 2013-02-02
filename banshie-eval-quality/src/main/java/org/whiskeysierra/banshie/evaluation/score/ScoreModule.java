package org.whiskeysierra.banshie.evaluation.score;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import org.whiskeysierra.banshie.evaluation.Dimension;

public final class ScoreModule extends AbstractModule {

    @Override
    protected void configure() {
        final MapBinder<Dimension, Score> binder = MapBinder.newMapBinder(binder(),
            Dimension.class, Score.class);

        binder.addBinding(Dimension.ERROR_MEASURE).to(ErrorMeasure.class);
        binder.addBinding(Dimension.ERROR_RATE).to(ErrorRate.class);
        binder.addBinding(Dimension.F_MEASURE).to(FMeasure.class);
        binder.addBinding(Dimension.PRECISION).to(Precision.class);
        binder.addBinding(Dimension.RECALL).to(Recall.class);
        binder.addBinding(Dimension.SLOT_ERROR_RATE).to(SlotErrorRate.class);
    }

}
