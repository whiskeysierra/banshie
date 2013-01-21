package org.whiskeysierra.banshie.execution;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.easymock.EasyMock;
import org.junit.Test;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.execution.io.IoModule;
import org.whiskeysierra.banshie.execution.logging.LoggingModule;
import org.whiskeysierra.banshie.execution.monitor.MonitorModule;
import org.whiskeysierra.banshie.execution.monitor.ProcessMonitor;
import org.whiskeysierra.banshie.execution.process.ProcessModule;
import org.whiskeysierra.banshie.execution.process.ProcessService;
import org.whiskeysierra.banshie.extractors.Extractor;

import java.io.File;
import java.util.UUID;

public final class DefaultEngineTest {

    @Test
    public void test() {
        final Injector injector = Guice.createInjector(
            new IoModule(), new LoggingModule(),
            new MonitorModule(), new ProcessModule());

        final ProcessService service = injector.getInstance(ProcessService.class);
        final Provider<ProcessMonitor> provider = injector.getProvider(ProcessMonitor.class);
        final Engine unit = new DefaultEngine(service, provider);

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
