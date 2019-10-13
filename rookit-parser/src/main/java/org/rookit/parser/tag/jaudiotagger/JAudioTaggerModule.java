package org.rookit.parser.tag.jaudiotagger;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.rookit.parser.tag.processor.TagProcessorFactory;

public final class JAudioTaggerModule extends AbstractModule {

    private static final Module MODULE = new JAudioTaggerModule();

    public static Module getModule() {
        return MODULE;
    }

    private JAudioTaggerModule() {}

    @Override
    protected void configure() {
        bind(TagProcessorFactory.class).to(JAudioTaggerTagProcessorFactory.class);
    }

}
