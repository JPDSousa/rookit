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

import com.google.common.collect.Lists;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.dm.track.artists.MutableTrackArtists;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("javadoc")
final class VersionTrackImpl extends AbstractTrack implements VersionTrack {

    private final Collection<Artist> versionArtists;

    private final String versionToken;// e.g. club remix

    private final Track original;
    private final MutableTrackArtists originalTrackArtists;

    private final TypeVersion versionType;

    VersionTrackImpl(final Genreable genreable,
                     final Track original,
                     final MutableTrackArtists originalTrackArtists,
                     final TypeVersion versionType,
                     final Iterable<Artist> versionArtists,
                     final String versionToken,
                     final AudioContent audioContent,
                     final MutableLyrics lyrics,
                     final OptionalUtils optionalUtils,
                     final Failsafe failsafe) {
        super(genreable, audioContent, lyrics, optionalUtils, failsafe);
        this.originalTrackArtists = originalTrackArtists;
        this.versionToken = versionToken;
        this.versionArtists = Lists.newArrayList(versionArtists);
        this.original = original;
        this.versionType = versionType;
    }

    @Override
    public Track original() {
        return this.original;
    }

    @Override
    public TrackTitle title() {
        return this.original.title();
    }

    @Override
    public TypeTrack type() {
        return TypeTrack.VERSION;
    }

    @Override
    public Collection<Artist> versionArtists() {
        return Collections.unmodifiableCollection(this.versionArtists);
    }

    @Override
    public String versionToken() {
        return this.versionToken;
    }

    @Override
    public TypeVersion versionType() {
        return this.versionType;
    }

    @Override
    MutableTrackArtists mutableArtists() {
        return this.originalTrackArtists;
    }

    @Override
    public String toString() {
        return "VersionTrackImpl{" +
                "versionArtists=" + this.versionArtists +
                ", versionToken='" + this.versionToken + '\'' +
                ", original=" + this.original +
                ", originalTrackArtists=" + this.originalTrackArtists +
                ", versionType=" + this.versionType +
                "} " + super.toString();
    }
}
