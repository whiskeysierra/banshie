package org.whiskeysierra.banshie.evaluation.counter;

import org.junit.Assert;
import org.junit.Test;
import org.whiskeysierra.banshie.evaluation.Span;

import java.util.Collections;
import java.util.List;

public final class DefaultCounterTest {

    private final Counter unit = new DefaultCounter();

    private Span newSpan(int start, int end) {
        final Span span = new Span();

        span.setStart(start);
        span.setEnd(end);

        return span;
    }

    private void test(Span first, Span second) {
        final List<Span> references = Collections.singletonList(first);
        final List<Span> predictions = Collections.singletonList(second);

        final Counts counts = unit.count(references, predictions);

        Assert.assertEquals(1, counts.getTruePositives());
        Assert.assertEquals(0, counts.getFalseNegatives());
        Assert.assertEquals(0, counts.getFalsePositives());
    }

    @Test
    public void exact() {
        final Span first = newSpan(10, 20);
        final Span second = newSpan(10, 20);

        test(first, second);
    }

    @Test
    public void referenceContains() {
        final Span first = newSpan(10, 20);
        final Span second = newSpan(12, 17);

        test(first, second);
    }

    @Test
    public void predictionContains() {
        final Span first = newSpan(12, 17);
        final Span second = newSpan(10, 20);

        test(first, second);
    }

    @Test
    public void referenceOverlaps() {
        final Span first = newSpan(10, 20);
        final Span second = newSpan(15, 25);

        test(first, second);
    }

    @Test
    public void predictionOverlaps() {
        final Span first = newSpan(10, 20);
        final Span second = newSpan(5, 15);

        test(first, second);
    }

    @Test
    public void missing() {
        final Span first = newSpan(10, 20);
        final Span second = newSpan(25, 30);

        final List<Span> references = Collections.singletonList(first);
        final List<Span> predictions = Collections.singletonList(second);

        final Counts counts = unit.count(references, predictions);

        Assert.assertEquals(0, counts.getTruePositives());
        Assert.assertEquals(1, counts.getFalseNegatives());
        Assert.assertEquals(1, counts.getFalsePositives());
    }

}
