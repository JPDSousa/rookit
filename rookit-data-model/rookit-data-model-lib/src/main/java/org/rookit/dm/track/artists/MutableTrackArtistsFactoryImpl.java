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
package org.rookit.dm.track.artists;

import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.artist.TrackArtistsFactory;
import org.rookit.failsafe.Failsafe;

final class MutableTrackArtistsFactoryImpl implements MutableTrackArtistsFactory {

    private final TrackArtistsFactory factory;
    private final Mapper mapper;
    private final Failsafe failsafe;

    @Inject
    private MutableTrackArtistsFactoryImpl(final TrackArtistsFactory factory,
                                           final Mapper mapper,
                                           final Failsafe failsafe) {
        this.factory = factory;
        this.mapper = mapper;
        this.failsafe = failsafe;
    }

    private MutableTrackArtists fromTrackArtists(final TrackArtists trackArtists) {
        if (trackArtists instanceof  MutableTrackArtists) {
            return (MutableTrackArtists) trackArtists;
        }
        final MutableTrackArtists artists = new MutableTrackArtistsImpl(trackArtists.mainArtists(), this.failsafe);
        this.mapper.map(trackArtists, artists);
        return artists;
    }

    @Override
    public MutableTrackArtists create(final Iterable<Artist> artists) {
        return fromTrackArtists(this.factory.create(artists));
    }

    @Override
    public MutableTrackArtists create(final Track track) {
        return new MutableTrackArtistsTrackAdapter(track);
    }

    @Override
    public MutableTrackArtists create(final Key key) {
        return fromTrackArtists(this.factory.create(key));
    }

    @Override
    public MutableTrackArtists createEmpty() {
        return fromTrackArtists(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return "MutableTrackArtistsFactoryImpl{" +
                "factory=" + this.factory +
                ", mapper=" + this.mapper +
                ", injector=" + this.failsafe +
                "}";
    }
}
