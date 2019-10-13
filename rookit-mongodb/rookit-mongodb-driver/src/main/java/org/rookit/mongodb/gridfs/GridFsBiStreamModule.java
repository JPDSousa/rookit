package org.rookit.mongodb.gridfs;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.factory.AlbumBiStream;
import org.rookit.dm.artist.factory.ArtistBiStream;
import org.rookit.dm.play.factory.PlaylistBiStream;
import org.rookit.dm.track.factory.TrackBiStream;
import org.rookit.utils.source.Prototype;

@Prototype
public final class GridFsBiStreamModule extends AbstractModule {

    private static final Module MODULE = new GridFsBiStreamModule();

    private static final TypeLiteral<BiStreamFactory<Key>> BISTREAM_FACTORY_TYPE_LITERAL =
            new TypeLiteral<BiStreamFactory<Key>>() {
                // intentionally empty
            };

    public static Module getModule() {
        return MODULE;
    }

    private GridFsBiStreamModule() {}

    @Override
    protected void configure() {
        // artist
        bind(BISTREAM_FACTORY_TYPE_LITERAL)
                .annotatedWith(ArtistBiStream.class)
                .toProvider(ArtistBiStreamFactoryProvider.class);

        // album
        bind(BISTREAM_FACTORY_TYPE_LITERAL)
                .annotatedWith(AlbumBiStream.class)
                .toProvider(AlbumBiStreamFactoryProvider.class);

        // track
        bind(BISTREAM_FACTORY_TYPE_LITERAL)
                .annotatedWith(TrackBiStream.class)
                .toProvider(TrackBiStreamFactoryProvider.class);

        // playlist
        bind(BISTREAM_FACTORY_TYPE_LITERAL)
                .annotatedWith(PlaylistBiStream.class)
                .toProvider(PlaylistBiStreamFactoryProvider.class);
    }
}
