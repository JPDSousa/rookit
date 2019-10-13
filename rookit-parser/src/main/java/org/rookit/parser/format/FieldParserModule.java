package org.rookit.parser.format;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.parser.format.field.FieldModule;

public final class FieldParserModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            FieldModule.getModule(),
            new FieldParserModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private FieldParserModule() {}

    @Override
    protected void configure() {
        bind(FieldParserFactory.class).to(FieldParserFactoryImpl.class);
    }
}
