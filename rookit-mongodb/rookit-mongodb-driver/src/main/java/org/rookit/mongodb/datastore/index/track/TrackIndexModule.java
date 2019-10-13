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
package org.rookit.mongodb.datastore.index.track;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.mongodb.datastore.MongoIndex;
import org.rookit.mongodb.guice.Unique;

@SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
public final class TrackIndexModule extends AbstractModule {

    private static final Module MODULE = new TrackIndexModule();

    public static Module getModule() {
        return MODULE;
    }

    private TrackIndexModule() {}

    @Override
    protected void configure() {
        final Multibinder<MongoIndex<Track>> multibinder = Multibinder.newSetBinder(binder(),
                new TypeLiteral<MongoIndex<Track>>() {});
        multibinder.addBinding().to(TrackTextIndex.class).in(Singleton.class);
        multibinder.addBinding().to(Key.get(new TypeLiteral<MongoIndex<Track>>() {}, Unique.class)).in(Singleton.class);

        bind(new TypeLiteral<MongoIndex<Track>>() {}).annotatedWith(Unique.class).to(TrackUniqueIndex.class)
                .in(Singleton.class);

        final Multibinder<MongoIndex<VersionTrack>> vtMBinder = Multibinder.newSetBinder(binder(),
                new TypeLiteral<MongoIndex<VersionTrack>>() {});
        vtMBinder.addBinding().to(Key.get(new TypeLiteral<MongoIndex<VersionTrack>>() {}, Unique.class))
                .in(Singleton.class);

        bind(new TypeLiteral<MongoIndex<VersionTrack>>() {}).annotatedWith(Unique.class)
                .to(VersionTrackUniqueIndex.class).in(Singleton.class);
    }
}
