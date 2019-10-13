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

import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;

import java.util.Collection;

final class MutableTrackArtistsTrackAdapter implements MutableTrackArtists {

    private final Track track;

    MutableTrackArtistsTrackAdapter(final Track track) {
        this.track = track;
    }

    @Override
    public void addFeature(final Artist artist) {
        this.track.addFeature(artist);
    }

    @Override
    public void removeFeature(final Artist artist) {
        this.track.removeFeature(artist);
    }

    @Override
    public void setFeatures(final Collection<Artist> artists) {
        this.track.setFeatures(artists);
    }

    @Override
    public void clearFeatures() {
        this.track.clearFeatures();
    }

    @Override
    public void addProducer(final Artist artist) {
        this.track.addProducer(artist);
    }

    @Override
    public void removeProducer(final Artist artist) {
        this.track.removeProducer(artist);
    }

    @Override
    public void setProducers(final Collection<Artist> artists) {
        this.track.setProducers(artists);
    }

    @Override
    public void clearProducers() {
        this.track.clearProducers();
    }

    @Override
    public Collection<Artist> mainArtists() {
        return this.track.artists().mainArtists();
    }

    @Override
    public Collection<Artist> features() {
        return this.track.artists().features();
    }

    @Override
    public Collection<Artist> producers() {
        return this.track.artists().producers();
    }

    @Override
    public String toString() {
        return "MutableTrackArtistsTrackAdapter{" +
                "track=" + this.track +
                "}";
    }
}
