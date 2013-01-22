package org.whiskeysierra.banshie.execution.process;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.io.File;

final class DefaultProcessService implements ProcessService {

    private String[] toString(Object... arguments) {
        final String[] array = new String[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            array[i] = toString(arguments[i]);
        }
        return array;
    }

    private String toString(Object argument) {
        return argument == null ? null : argument.toString();
    }

    @Override
    public ManagedProcess prepare(File script, Object... arguments) {
        return prepare(script.getAbsolutePath(), arguments);
    }

    @Override
    public ManagedProcess prepare(File script, Iterable<?> arguments) {
        return prepare(script, Iterables.toArray(arguments, Object.class));
    }

    @Override
    public ManagedProcess prepare(String command, Object... arguments) {
        final ProcessBuilder builder = new ProcessBuilder();
        final String[] array = toString(arguments);
        builder.command(Lists.asList(command, array));
        return new DefaultManagedProcess(builder);
    }

    @Override
    public ManagedProcess prepare(String command, Iterable<?> arguments) {
        return prepare(command, Iterables.toArray(arguments, Object.class));
    }

}
