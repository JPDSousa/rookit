
package org.rookit.dm.album.disc;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.track.Track;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.supplier.SupplierUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h1>Disc class.</h1> This class is private and its only use is to create an
 * abstraction level that eases the process of manage discs inside albums.
 *
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 */
@NotThreadSafe
final class MutableDiscImpl implements MutableDisc {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableDiscImpl.class);

    /**
     * Keyed that contains the tracks.
     * <p>
     * Key - track primitive
     * <p>
     * Value - track object
     */
    private final Int2ObjectMap<Track> tracks;

    private final String name;

    private final TrackSlotFactory trackSlotFactory;
    private final OptionalFactory optionalFactory;
    private final SupplierUtils supplierUtils;
    private final Failsafe failsafe;

    MutableDiscImpl(final String name,
                    final TrackSlotFactory trackSlotFactory,
                    final OptionalFactory optionalFactory,
                    final SupplierUtils supplierUtils,
                    final Failsafe failsafe) {
        this.name = name;
        this.trackSlotFactory = trackSlotFactory;
        this.optionalFactory = optionalFactory;
        this.supplierUtils = supplierUtils;
        this.failsafe = failsafe;
        this.tracks = Int2ObjectMaps.synchronize(new Int2ObjectArrayMap<>());
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Optional<Track> track(final int number) {
        return this.optionalFactory.ofNullable(this.tracks.get(number));
    }

    @Override
    public TrackSlot trackAsSlot(final int number) {
        return track(number)
                .map(track -> this.trackSlotFactory.createWithTrack(name(), number, track))
                .orElseGet(() -> this.trackSlotFactory.createEmpty(name(), number));
    }

    @Override
    public int size() {
        return this.tracks.size();
    }

    @Override
    public Int2ObjectMap<Track> trackMap() {
        return Int2ObjectMaps.unmodifiable(this.tracks);
    }

    @Override
    public Collection<Track> asTrackCollection() {
        return Collections.unmodifiableCollection(this.tracks.values());
    }

    @Override
    public Collection<TrackSlot> asTrackSlotsCollection() {
        return IntStream.of(this.tracks.keySet().toIntArray())
                .mapToObj(this::trackAsSlot)
                .collect(Collectors.toList());
    }

    @Override
    public boolean putIfAbsent(final Track track, final int number) {
        return Objects.isNull(this.tracks.putIfAbsent(number, track));
    }

    @Override
    public void putNextEmpty(final Track track) {
        final int emptySlot = IntStream.generate(this.supplierUtils.incrementalSupplier(1))
                .filter(index -> !this.tracks.containsKey(index))
                .findFirst()
                .orElseThrow(() -> this.failsafe.handleException()
                        .<RuntimeException>runtimeException("Cannot find an empty slot"));
        this.tracks.put(emptySlot, track);
    }

    @Override
    public void relocate(final int number, final int newNumber) {
        // TODO create int version of this
        this.failsafe.checkState().map().isNotContainedIn(logger, newNumber, trackMap(), "trackNumber", "disc");

        final Track track = this.tracks.remove(number);
        this.failsafe.checkState().is(logger, Objects.nonNull(track),
                "there is no track in [%s|%s] to relocate",
                this.name,
                number);

        this.tracks.put(newNumber, track);
    }

    @Override
    public Track remove(final int trackNumber) {
        return this.tracks.remove(trackNumber);
    }

    @Override
    public void clear() {
        this.tracks.clear();
    }

    @Override
    public boolean contains(final Track track) {
        return this.tracks.containsValue(track);
    }

    @Override
    public boolean contains(final int trackNumber) {
        return this.tracks.containsKey(trackNumber);
    }

    @Override
    public Stream<Track> stream() {
        return this.tracks.values().stream();
    }

    @Override
    public int remove(final Track track) {
        this.failsafe.checkArgument().isNotNull(logger, track, "track");

        final int[] numbersToRemove = IntStream.of(this.tracks.keySet().toIntArray())
                .filter(number -> this.tracks.get(number).equals(track))
                .toArray();
        return IntStream.of(numbersToRemove)
                .mapToObj(this.tracks::remove)
                .filter(Objects::nonNull)
                .mapToInt(removedTrack -> 1)
                .sum();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        final MutableDiscImpl disc = (MutableDiscImpl) o;
        return com.google.common.base.Objects.equal(this.tracks, disc.tracks) &&
                com.google.common.base.Objects.equal(this.name, disc.name) &&
                com.google.common.base.Objects.equal(this.trackSlotFactory, disc.trackSlotFactory);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.tracks, this.name, this.trackSlotFactory);
    }

    @Override
    public Iterator<Track> iterator() {
        return this.tracks.values().iterator();
    }

    @Override
    public String toString() {
        return "MutableDiscImpl{" +
                "tracks=" + this.tracks +
                ", className='" + this.name + '\'' +
                ", trackSlotFactory=" + this.trackSlotFactory +
                ", optionalFactory=" + this.optionalFactory +
                ", supplierUtils=" + this.supplierUtils +
                ", injector=" + this.failsafe +
                "}";
    }
}
