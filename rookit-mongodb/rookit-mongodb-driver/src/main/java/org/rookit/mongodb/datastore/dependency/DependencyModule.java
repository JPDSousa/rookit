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
package org.rookit.mongodb.datastore.dependency;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.mongodb.DependencyHandler;
import org.rookit.utils.primitive.VoidUtils;

@SuppressWarnings("MethodMayBeStatic")
public final class DependencyModule extends AbstractModule {

    private static final Module MODULE = new DependencyModule();

    public static Module getModule() {
        return MODULE;
    }

    private DependencyModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(new TypeLiteral<DependencyHandler<Album>>() {}).to(AlbumDependencyHandler.class).in(Singleton.class);
        bind(new TypeLiteral<DependencyHandler<Artist>>() {}).to(ArtistDependencyHandler.class).in(Singleton.class);
        bind(new TypeLiteral<DependencyHandler<Playlist>>() {}).to(PlaylistDependencyHandler.class).in(Singleton.class);
        bind(new TypeLiteral<DependencyHandler<Track>>() {}).to(TrackDependencyHandler.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    DependencyHandler<Genre> genreDependencyHandler(final VoidUtils voidUtils) {
        return new EmptyDependencyHandler<>(voidUtils);
    }
}
