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

import com.google.common.collect.Sets;
import org.rookit.api.dm.artist.Artist;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;

@NotThreadSafe
abstract class AbstractTrackArtists implements MutableTrackArtists {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractTrackArtists.class);

    private final Failsafe failsafe;
    private Collection<Artist> features;
    private Collection<Artist> producers;

    AbstractTrackArtists(final Failsafe failsafe) {
        this.failsafe = failsafe;
        this.features = Sets.newLinkedHashSet();
        this.producers = Sets.newLinkedHashSet();
    }
    @Override
    public void addFeature(final Artist artist) {
        final String feature = "feature";
        this.failsafe.checkArgument().collection().isNotContainedIn(logger, artist, mainArtists(), feature);
        this.failsafe.checkArgument().collection().isNotContainedIn(logger, artist, producers(), feature);
        this.features.add(artist);
    }

    @Override
    public void removeFeature(final Artist artist) {
        this.failsafe.checkArgument().isNotNull(logger, artist, "artist");
        this.features.remove(artist);
    }

    @Override
    public void setFeatures(final Collection<Artist> artists) {
        this.failsafe.checkArgument().collection().isNotIntersecting(logger, artists, mainArtists(),
                "features", "mainArtists");
        this.failsafe.checkArgument().collection().isNotIntersecting(logger, artists, producers(),
                "features", "producers");
        this.features = Sets.newHashSet(artists);
    }

    @Override
    public void clearFeatures() {
        this.features.clear();
    }

    @Override
    public void addProducer(final Artist artist) {
        final String producer = "producer";
        this.failsafe.checkArgument().collection().isNotContainedIn(logger, artist, mainArtists(), producer);
        this.failsafe.checkArgument().collection().isNotContainedIn(logger, artist, features(), producer);
        this.producers.add(artist);
    }

    @Override
    public void removeProducer(final Artist artist) {
        this.failsafe.checkArgument().isNotNull(logger, artist, "artist");
        this.producers.remove(artist);
    }

    @Override
    public void setProducers(final Collection<Artist> artists) {
        this.failsafe.checkArgument().collection().isNotIntersecting(logger, artists,
                mainArtists(), "producers", "mainArtists");
        this.failsafe.checkArgument().collection().isNotIntersecting(logger, artists,
                features(), "producers", "features");
        this.producers = Sets.newHashSet(artists);
    }

    @Override
    public void clearProducers() {
        this.producers.clear();
    }

    @Override
    public Collection<Artist> features() {
        return Collections.unmodifiableCollection(this.features);
    }

    @Override
    public Collection<Artist> producers() {
        return Collections.unmodifiableCollection(this.producers);
    }

    @Override
    public String toString() {
        return "AbstractTrackArtists{" +
                "injector=" + this.failsafe +
                ", features=" + this.features +
                ", producers=" + this.producers +
                "}";
    }
}
