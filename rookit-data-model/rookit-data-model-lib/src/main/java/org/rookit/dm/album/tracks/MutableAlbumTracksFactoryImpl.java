package org.rookit.dm.album.tracks;

import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.album.tracks.AlbumTracksFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.disc.MutableDiscFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;

final class MutableAlbumTracksFactoryImpl implements MutableAlbumTracksFactory {

    private final MutableDiscFactory discFactory;
    private final AlbumTracksFactory albumTracksFactory;
    private final Mapper mapper;
    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    @Inject
    private MutableAlbumTracksFactoryImpl(final MutableDiscFactory discFactory,
                                          final AlbumTracksFactory albumTracksFactory,
                                          final Mapper mapper,
                                          final Failsafe failsafe,
                                          final OptionalFactory optionalFactory) {
        this.discFactory = discFactory;
        this.albumTracksFactory = albumTracksFactory;
        this.mapper = mapper;
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    private MutableAlbumTracks fromAlbumTracks(final AlbumTracks albumTracks) {
        if (albumTracks instanceof MutableAlbumTracks) {
            return (MutableAlbumTracks) albumTracks;
        }

        final MutableAlbumTracks mutableAlbumTracks = new MutableAlbumTracksImpl(this.failsafe,
                this.discFactory,
                this.optionalFactory);
        this.mapper.map(albumTracks, mutableAlbumTracks);
        return mutableAlbumTracks;
    }

    @Override
    public MutableAlbumTracks create(final Key key) {
        return fromAlbumTracks(this.albumTracksFactory.create(key));
    }

    @Override
    public MutableAlbumTracks createEmpty() {
        return fromAlbumTracks(this.albumTracksFactory.createEmpty());
    }

    @Override
    public String toString() {
        return "MutableAlbumTracksFactoryImpl{" +
                "discFactory=" + this.discFactory +
                ", albumTracksFactory=" + this.albumTracksFactory +
                ", mapper=" + this.mapper +
                ", injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
