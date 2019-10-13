/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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

package org.rookit.dm.track;

import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.dm.track.artists.MutableTrackArtists;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;

@SuppressWarnings("javadoc")
final class OriginalTrackImpl extends AbstractTrack {

    private final TrackTitle title;

    private final MutableTrackArtists artists;

    OriginalTrackImpl(final Genreable genreable,
                      final TrackTitle title,
                      final AudioContent audioContent,
                      final MutableLyrics lyrics,
                      final MutableTrackArtists artists,
                      final OptionalUtils optionalUtils,
                      final Failsafe failsafe) {
        super(genreable, audioContent, lyrics, optionalUtils, failsafe);
        this.title = title;
        this.artists = artists;
    }

    @Override
    public TrackTitle title() {
        return this.title;
    }

    @Override
    public TypeTrack type() {
        return TypeTrack.ORIGINAL;
    }

    @Override
    MutableTrackArtists mutableArtists() {
        return this.artists;
    }

    @Override
    public String toString() {
        return "OriginalTrackImpl{" +
                "title=" + this.title +
                ", artists=" + this.artists +
                "} " + super.toString();
    }
}
