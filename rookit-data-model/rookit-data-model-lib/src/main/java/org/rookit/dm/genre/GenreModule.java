package org.rookit.dm.genre;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.dm.genre.able.GenreableModule;

public final class GenreModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new GenreModule(),
            GenreableModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private GenreModule() {}

    @Override
    protected void configure() {
        bind(GenreFactory.class).to(GenreFactoryImpl.class);
    }
}
