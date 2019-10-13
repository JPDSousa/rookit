/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.auto.source;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import io.reactivex.Maybe;
import org.rookit.auto.source.guice.JavadocTemplate;
import org.rookit.auto.source.spec.SpecModule;
import org.rookit.io.path.registry.PathRegistries;
import org.rookit.utils.object.DynamicObject;
import org.rookit.utils.registry.Registry;

import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Path;

public final class SourceLibModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new SourceLibModule(),
            SpecModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceLibModule() {}

    @Override
    protected void configure() {
        bind(CodeSourceFactories.class).to(CodeSourceFactoriesImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @JavadocTemplate
    Registry<String, DynamicObject> javadocs(final PathRegistries registries,
                                             final Registry<URI, FileSystem> fileSystemRegistry) {
        return Maybe.fromCallable(() -> getClass().getClassLoader().getResource("javadoc-template"))
                .map(URL::toURI)
                .flatMap(uri -> createPath(uri, fileSystemRegistry))
                .map(registries::serializedDirectoryRegistry)
                .blockingGet();
    }

    // TODO this shouldn't be here
    Maybe<Path> createPath(final URI uri, final Registry<URI, FileSystem> registry) {
        return registry.get(uri)
                .map(FileSystem::provider)
                .map(provider -> provider.getPath(uri));
    }
}
