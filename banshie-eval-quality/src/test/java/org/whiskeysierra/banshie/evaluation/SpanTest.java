package org.whiskeysierra.banshie.evaluation;

import org.junit.Assert;
import org.junit.Test;

public final class SpanTest {

    private Span newSpan(int start, int end) {
        final Span span = new Span();

        span.setStart(start);
        span.setEnd(end);

        return span;
    }

    @Test
    public void dontOverlap() {
        final Span first = newSpan(10, 20);
        final Span second = newSpan(25, 30);

        Assert.assertFalse(first.overlap(second));
    }


}
