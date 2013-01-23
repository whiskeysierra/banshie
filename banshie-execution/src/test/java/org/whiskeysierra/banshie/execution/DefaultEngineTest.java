package org.whiskeysierra.banshie.execution;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.easymock.EasyMock;
import org.junit.Test;
import org.whiskeysierra.banshie.common.InstallMode;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.execution.event.EventModule;
import org.whiskeysierra.banshie.execution.logging.LoggingModule;
import org.whiskeysierra.banshie.execution.monitor.MonitorModule;
import org.whiskeysierra.banshie.execution.monitor.ProcessMonitorFactory;
import org.whiskeysierra.banshie.execution.process.ProcessModule;
import org.whiskeysierra.banshie.execution.process.ProcessService;
import org.whiskeysierra.banshie.extractors.Extractor;

import java.io.File;
import java.util.UUID;

public final class DefaultEngineTest {

    @Test
    public void test() {
        final Injector injector = Guice.createInjector(
            new LoggingModule(),
            new EventModule(InstallMode.STANDALONE),
            new MonitorModule(InstallMode.STANDALONE),
            new ProcessModule());

        final ProcessService service = injector.getInstance(ProcessService.class);
        final ProcessMonitorFactory factory = injector.getInstance(ProcessMonitorFactory.class);
        final Engine unit = new DefaultEngine(service, factory);

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
