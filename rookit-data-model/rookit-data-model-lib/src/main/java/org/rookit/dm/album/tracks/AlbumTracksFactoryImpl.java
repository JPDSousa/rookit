package org.rookit.dm.album.tracks;

import com.google.inject.Inject;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.album.tracks.AlbumTracksFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.disc.MutableDiscFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class AlbumTracksFactoryImpl implements AlbumTracksFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AlbumTracksFactoryImpl.class);

    private final MutableDiscFactory discFactory;
    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    @Inject
    private AlbumTracksFactoryImpl(final MutableDiscFactory discFactory,
                                   final Failsafe failsafe,
                                   final OptionalFactory optionalFactory) {
        this.discFactory = discFactory;
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public AlbumTracks create(final Key key) {
        logger.warn("Creation by key is not supported for album tracks. Using createEmpty instead.");
        return createEmpty();
    }

    @Override
    public AlbumTracks createEmpty() {
        return new MutableAlbumTracksImpl(this.failsafe, this.discFactory, this.optionalFactory);
    }

    @Override
    public String toString() {
        return "AlbumTracksFactoryImpl{" +
                "discFactory=" + this.discFactory +
                ", injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
