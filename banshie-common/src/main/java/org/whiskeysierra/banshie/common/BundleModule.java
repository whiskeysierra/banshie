package org.whiskeysierra.banshie.common;

import com.google.inject.AbstractModule;

public abstract class BundleModule extends AbstractModule {

    private final InstallMode mode;

    protected BundleModule() {
        this(InstallMode.BUNDLE);
    }

    protected BundleModule(InstallMode mode) {
        this.mode = mode;
    }

    @Override
    protected final void configure() {
        switch (mode) {
            case BUNDLE: {
                configureBundle();
                // intended fall-through
            }
            case STANDALONE: {
                configureStandalone();
            }
        }
    }

    protected abstract void configureStandalone();

    protected abstract void configureBundle();

}
