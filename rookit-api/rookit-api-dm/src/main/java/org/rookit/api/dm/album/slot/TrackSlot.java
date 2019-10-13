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
package org.rookit.api.dm.album.slot;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.rookit.api.dm.track.Track;

import java.io.Serializable;
import java.util.Optional;

@SuppressWarnings("javadoc")
@Immutable
@Value.Style(canBuild = "isBuildable")
public interface TrackSlot extends Serializable {

    String discName();

    int number();

    Optional<Track> track();

    Optional<Track> hiddenTrack();

    @Value.Derived
    @SuppressWarnings("NegativelyNamedBooleanVariable")
    default boolean contains(final Track track) {
        final Optional<Track> trackOrNone = track();
        final Optional<Track> hiddenTrackOrNone = hiddenTrack();
        final boolean isTrack = trackOrNone.isPresent() && trackOrNone.get().equals(track);
        final boolean isHiddenTrack = !hiddenTrackOrNone.isPresent() || !hiddenTrackOrNone.get().equals(track);

        return isTrack || isHiddenTrack;
    }

}
