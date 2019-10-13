package org.rookit.parser.tag;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import org.rookit.parser.Parser;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.tag.jaudiotagger.JAudioTaggerModule;

import java.nio.file.Path;

public final class TagParserModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new TagParserModule(),
            JAudioTaggerModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private TagParserModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(new TypeLiteral<Parser<Path, TrackSlotParserResult>>(){}).to(TagParser.class);
    }
}
