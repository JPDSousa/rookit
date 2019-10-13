package org.rookit.dm.album.slot;

import com.google.inject.Inject;
import org.rookit.api.dm.album.slot.ImmutableTrackSlot;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.Track;
import org.rookit.failsafe.Failsafe;
import org.slf4j.LoggerFactory;

final class ImmutableTrackSlotFactory implements TrackSlotFactory {

    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImmutableTrackSlotFactory.class);
    
    private final Failsafe failsafe;

    @Inject
    private ImmutableTrackSlotFactory(final Failsafe failsafe) {
        this.failsafe = failsafe;
    }

    @Override
    public TrackSlot copyOf(final TrackSlot slot) {
        return ImmutableTrackSlot.copyOf(slot);
    }

    @Override
    public TrackSlot createEmpty(final String disc, final int number) {
        return buildEmpty(disc, number)
                .build();
    }

    @SuppressWarnings({"MethodMayBeStatic", "MethodReturnOfConcreteClass"}) // helper field, used to avoid feature envy
    private ImmutableTrackSlot.Builder buildEmpty(final String disc, final int number) {
        return ImmutableTrackSlot.builder()
                .discName(disc)
                .number(number);
    }

    @Override
    public TrackSlot createWithTrack(final String disc, final int number, final Track track) {
        return buildEmpty(disc, number)
                .track(track)
                .build();
    }

    @Override
    public TrackSlot create(final Key key) {
        logger.warn("Creation by key is not supported. Redirecting to createEmpty.");
        return createEmpty();
    }

    @Override
    public TrackSlot createEmpty() {
        return this.failsafe.handleException()
                .unsupportedOperation("Cannot create an empty track slot. " +
                        "Must at least provide a number and disc");
    }

    @Override
    public String toString() {
        return "ImmutableTrackSlotFactory{" +
                "injector=" + this.failsafe +
                "}";
    }
}
