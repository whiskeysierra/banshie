package org.whiskeysierra.banshie.execution;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.extractors.Extractor;

import java.io.File;
import java.util.UUID;

public final class DefaultEngineTest {

    @Test
    public void test() {
        final Engine unit = new DefaultEngine();

        final Extractor extractor = EasyMock.createMock(Extractor.class);
        final Corpus corpus = EasyMock.createMock(Corpus.class);

        EasyMock.expect(extractor.getUuid()).andReturn(UUID.randomUUID()).anyTimes();
        EasyMock.expect(extractor.getPath()).andReturn(new File("src/test/resources/echo.jar")).anyTimes();

        EasyMock.expect(corpus.getUuid()).andReturn(UUID.randomUUID()).anyTimes();
        EasyMock.expect(corpus.getInput()).andReturn(new File("src/test/resources/input.txt")).anyTimes();

        EasyMock.replay(extractor, corpus);

        final ExecutionResult result = unit.execute(extractor, corpus);

        // TODO run assertions

        EasyMock.verify(extractor, corpus);
    }

}
