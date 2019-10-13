package org.rookit.parser;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.parser.format.FieldParserModule;
import org.rookit.parser.tag.TagParserModule;

@SuppressWarnings("javadoc")
public final class RookitParser extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            TagParserModule.getModule(),
            FieldParserModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private RookitParser() {
    }

    @Override
    protected void configure() {

    }
}
