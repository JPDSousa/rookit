package org.rookit.dm.album.tracks;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.tracks.AlbumTrackSlotsAdapter;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.disc.MutableDisc;
import org.rookit.dm.album.disc.MutableDiscFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@NotThreadSafe
final class MutableAlbumTracksImpl implements MutableAlbumTracks {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableAlbumTracksImpl.class);
    
    private final Failsafe failsafe;
    
    /**
     * Keyed of discs, containing the discs of the album Key ({@link String}) -
     * official of the disc Value ({@link Disc}}Disc) - disc object
     */
    private final Map<String, MutableDisc> discs;
    private int tracks;
    private final MutableDiscFactory discFactory;
    private final OptionalFactory optionalFactory;

    MutableAlbumTracksImpl(final Failsafe failsafe,
                           final MutableDiscFactory discFactory,
                           final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
        this.discs = Maps.newHashMap();
        this.tracks = 0;
        this.discFactory = discFactory;
    }

    @Override
    public AlbumTrackSlotsAdapter asSlots() {
        return new AlbumTrackSlotsAdapterImpl(this.discs, this.tracks);
    }

    @Override
    public Optional<Disc> disc(final String discName) {
        return this.optionalFactory.ofNullable(this.discs.get(discName));
    }

    @Override
    public boolean contains(final String discName) {
        return false;
    }

    @Override
    public boolean contains(final Track track) {
        this.failsafe.checkArgument().isNotNull(logger, track, "track");

        return this.discs.values().stream()
                .anyMatch(disc -> disc.contains(track));
    }

    @Override
    public Collection<Disc> discs() {
        return Collections.unmodifiableCollection(this.discs.values());
    }

    @Override
    public Stream<Track> stream() {
        return this.discs.values().stream()
                .flatMap(Disc::stream);
    }

    @Override
    public int size() {
        return this.tracks;
    }

    @Override
    public Optional<Duration> duration() {
        return this.optionalFactory.of(discs().stream()
                .map(Disc::duration)
                .reduce(Duration.ZERO, Duration::plus));
    }

    @Override
    public void clearTracks() {
        this.discs.values().forEach(MutableDisc::clear);
        this.tracks = 0;
    }

    @Override
    public void removeTrack(final Track track) {
        this.failsafe.checkArgument().isNotNull(logger, track, "track");
        final int removedTracks = this.discs.values()
                .stream()
                .mapToInt(disc -> disc.remove(track))
                .sum();
        this.tracks-=removedTracks;
    }

    @Override
    public void removeTrack(final int number, final String disc) {
        this.failsafe.checkArgument().number().isPositive(logger, number, "primitive");
        this.failsafe.checkArgument().string().isNotBlank(logger, disc, "disc");

        final Track removedTrack = getDisc(disc, false).remove(number);
        if (Objects.nonNull(removedTrack)) {
            this.tracks--;
        }
    }

    @Override
    public void relocate(final String discName, final int number, final String newDiscName, final int newNumber) {
        this.failsafe.checkArgument().string().isNotBlank(logger, discName, "discName");
        this.failsafe.checkArgument().string().isNotBlank(logger, newDiscName, "newDiscName");
        // discName and newDiscName are validated by disc()
        final MutableDisc oldDisc = getDisc(discName, false);
        if (Objects.equals(discName, newDiscName)) {
            oldDisc.relocate(number, newNumber);
            return;
        }

        final MutableDisc newDisc = getDisc(newDiscName, true);
        this.failsafe.checkState().map().isNotContainedIn(logger, newNumber, newDisc.trackMap(),
                "trackNumber", "disc");
        final Track track = oldDisc.remove(number);
        this.failsafe.checkState().isNotNull(logger, track, "there is no track in [%s|%s] to relocate");

        if (!newDisc.putIfAbsent(track, newNumber)) {
            logger.warn("A track was already contained in {}:{}. Reverting remove operation on {}:{}",
                    newDisc, newNumber, oldDisc, number);
            oldDisc.putIfAbsent(track, number);
        }
    }

    @Override
    public void addTrackSlot(final TrackSlot slot) {
        this.failsafe.checkArgument().isNotNull(logger, slot, "slot");

        final java.util.Optional<Track> trackOrNone = slot.track();
        if (trackOrNone.isPresent()) {
            addTrack(trackOrNone.get(), slot.number(), slot.discName());
        } else {
            logger.info("No track found for slot {}. Ignoring request to add a track", slot);
        }
    }

    @Override
    public void addTrack(final Track track, final int number, final String discName) {
        this.failsafe.checkArgument().isNotNull(logger, track, "track");
        this.failsafe.checkArgument().string().isNotBlank(logger, discName, "discName");

        getDisc(discName, true).putIfAbsent(track, number);
    }

    @Override
    public void addTrackLast(final Track track, final String discName) {
        this.failsafe.checkArgument().isNotNull(logger, track, "track");
        this.failsafe.checkArgument().string().isNotBlank(logger, discName, "discName");

        getDisc(discName, true).putNextEmpty(track);
    }

    private MutableDisc getDisc(final String discName, final boolean create) {
        MutableDisc disc = this.discs.get(discName);
        if (!create) {
            this.failsafe.checkState()
                    .is(logger, Objects.nonNull(disc), "The disc %s was not found in container: %s",
                            discName, this);
        } else if (Objects.isNull(disc)) {
            disc = this.discFactory.create(discName);
            this.discs.put(discName, disc);
        }
        return disc;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if ((object == null) || (getClass() != object.getClass())) return false;
        final MutableAlbumTracksImpl otherAlbumTracks = (MutableAlbumTracksImpl) object;
        return com.google.common.base.Objects.equal(this.discs, otherAlbumTracks.discs);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.discs);
    }

    @Override
    public Iterator<Track> iterator() {
        return Iterables.concat(this.discs.values()).iterator();
    }

    @Override
    public String toString() {
        return "MutableAlbumTracksImpl{" +
                "injector=" + this.failsafe +
                ", discs=" + this.discs +
                ", tracks=" + this.tracks +
                ", discFactory=" + this.discFactory +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
