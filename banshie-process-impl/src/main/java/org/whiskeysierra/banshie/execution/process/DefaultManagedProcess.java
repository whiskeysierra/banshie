package org.whiskeysierra.banshie.execution.process;

import com.google.common.base.Joiner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

final class DefaultManagedProcess implements ManagedProcess {

    private final ProcessBuilder builder;

    public DefaultManagedProcess(ProcessBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ManagedProcess in(File directory) {
        builder.directory(directory);
        return this;
    }

    @Override
    public ManagedProcess with(String variable, String value) {
        builder.environment().put(variable, value);
        return this;
    }

    @Override
    public ManagedProcess with(Map<String, String> properties) {
        builder.environment().putAll(properties);
        return this;
    }

    @Override
    public RunningProcess call() throws IOException{
        return new DefaultRunningProcess(builder);
    }

    @Override
    public String toString() {
        return Joiner.on(' ').join(builder.command()) + " [new]";
    }

}
