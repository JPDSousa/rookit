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
package org.rookit.dm.serializer;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumMetaType;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.ArtistMetaType;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.GenreMetaType;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.PlaylistMetaType;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackMetaType;
import org.rookit.convention.ConventionUtils;
import org.rookit.convention.instance.InstanceBuilderFactory;
import org.rookit.convention.serializer.ConventionSerializer;
import org.rookit.serialization.Serializer;
import org.rookit.utils.optional.OptionalFactory;

public final class RookitDataModelSerializerModule extends AbstractModule {

    private static final Module MODULE = new RookitDataModelSerializerModule();

    public static Module getModule() {
        return MODULE;
    }

    private RookitDataModelSerializerModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        final Multibinder<Serializer<?>> mBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<Serializer<?>>() {
        });

        mBinder.addBinding().to(new TypeLiteral<Serializer<Genre>>() {}).in(Singleton.class);
        mBinder.addBinding().to(new TypeLiteral<Serializer<Track>>() {}).in(Singleton.class);
        mBinder.addBinding().to(new TypeLiteral<Serializer<Artist>>() {}).in(Singleton.class);
        mBinder.addBinding().to(new TypeLiteral<Serializer<Album>>() {}).in(Singleton.class);
        mBinder.addBinding().to(new TypeLiteral<Serializer<Playlist>>() {}).in(Singleton.class);
    }

    @Provides
    @Singleton
    Serializer<Genre> genreSerializer(final GenreMetaType metaType,
                                      final InstanceBuilderFactory<Genre> instanceFactory,
                                      final ConventionUtils conventionUtils,
                                      final OptionalFactory optionalFactory) {
        return ConventionSerializer.create(metaType, instanceFactory, conventionUtils, optionalFactory);
    }

    @Provides
    @Singleton
    Serializer<Artist> artistSerializer(final ArtistMetaType metaType,
                                        final InstanceBuilderFactory<Artist> instanceFactory,
                                        final ConventionUtils conventionUtils,
                                        final OptionalFactory optionalFactory) {
        return ConventionSerializer.create(metaType, instanceFactory, conventionUtils, optionalFactory);
    }

    @Provides
    @Singleton
    Serializer<Track> trackSerializer(final TrackMetaType metaType,
                                      final InstanceBuilderFactory<Track> instanceFactory,
                                      final ConventionUtils conventionUtils,
                                      final OptionalFactory optionalFactory) {
        return ConventionSerializer.create(metaType, instanceFactory, conventionUtils, optionalFactory);
    }

    @Provides
    @Singleton
    Serializer<Album> albumSerializer(final AlbumMetaType metaType,
                                      final InstanceBuilderFactory<Album> instanceFactory,
                                      final ConventionUtils conventionUtils,
                                      final OptionalFactory optionalFactory) {
        return ConventionSerializer.create(metaType, instanceFactory, conventionUtils, optionalFactory);
    }

    @Provides
    @Singleton
    Serializer<Playlist> playlistSerializer(final PlaylistMetaType metaType,
                                            final InstanceBuilderFactory<Playlist> instanceFactory,
                                            final ConventionUtils conventionUtils,
                                            final OptionalFactory optionalFactory) {
        return ConventionSerializer.create(metaType, instanceFactory, conventionUtils, optionalFactory);
    }

}
