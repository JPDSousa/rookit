package org.rookit.dm.album;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.genre.able.GenreableFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.release.MutableReleaseFactory;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.album.tracks.MutableAlbumTracksFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;

final class VariousArtistsAlbumFactoryImpl implements AlbumFactory {

    private final Failsafe failsafe;
    private final MutableReleaseFactory releaseFactory;
    private final BiStreamFactory<Key> biStreamFactory;
    private final MutableAlbumTracksFactory albumTracksFactory;
    private final OptionalUtils optionalUtils;
    private final GenreableFactory genreableFactory;

    VariousArtistsAlbumFactoryImpl(final Failsafe failsafe,
                                   final MutableReleaseFactory releaseFactory,
                                   final BiStreamFactory<Key> biStreamFactory,
                                   final MutableAlbumTracksFactory albumTracksFactory,
                                   final OptionalUtils optionalUtils,
                                   final GenreableFactory genreableFactory) {
        this.failsafe = failsafe;
        this.releaseFactory = releaseFactory;
        this.biStreamFactory = biStreamFactory;
        this.albumTracksFactory = albumTracksFactory;
        this.optionalUtils = optionalUtils;
        this.genreableFactory = genreableFactory;
    }

    private Album create(final String title, final TypeRelease typeRelease) {
        final BiStream biStream = this.biStreamFactory.createEmpty();
        final MutableAlbumTracks albumTracks = this.albumTracksFactory.createEmpty();
        final MutableRelease release = this.releaseFactory.releaseOf(typeRelease);
        final Genreable genreable = this.genreableFactory.create();

        return new VariousArtistsAlbum(genreable,
                title,
                release,
                biStream,
                albumTracks,
                this.optionalUtils,
                this.failsafe);
    }

    @Override
    public Album create(final AlbumKey key) {
        return create(key.title(), key.releaseType());
    }

    @Override
    public Album createEmpty() {
        return this.failsafe.handleException().unsupportedOperation("Cannot create an empty VariousArtistsAlbum");
    }

    @Override
    public String toString() {
        return "VariousArtistsAlbumFactoryImpl{" +
                "injector=" + this.failsafe +
                ", releaseFactory=" + this.releaseFactory +
                ", biStreamFactory=" + this.biStreamFactory +
                ", albumTracksFactory=" + this.albumTracksFactory +
                ", optionalUtils=" + this.optionalUtils +
                ", genreableFactory=" + this.genreableFactory +
                "}";
    }
}
