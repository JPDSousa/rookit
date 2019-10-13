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

import com.google.common.base.Objects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.lyrics.Lyrics;
import org.rookit.dm.genre.able.DelegateGenreable;
import org.rookit.dm.track.artists.MutableTrackArtists;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

abstract class AbstractTrack extends DelegateGenreable implements Track {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractTrack.class);

    private final AudioContent content;
    private final MutableLyrics lyrics;

    private final OptionalUtils optionalUtils;
    private final Failsafe failsafe;
    
    AbstractTrack(final Genreable genreable,
                  final AudioContent content,
                  final MutableLyrics lyrics,
                  final OptionalUtils optionalUtils,
                  final Failsafe failsafe) {
        super(genreable);
        this.content = content;
        this.lyrics = lyrics;
        this.optionalUtils = optionalUtils;
        this.failsafe = failsafe;
    }

    abstract MutableTrackArtists mutableArtists();

    @Override
    public TrackArtists artists() {
        return mutableArtists();
    }

    @Override
    public AudioContent audio() {
        return this.content;
    }

    @Override
    public Lyrics lyrics() {
        return this.lyrics;
    }

    @Override
    public void addFeature(final Artist artist) {
        this.failsafe.checkArgument().isNotNull(logger, artist, "artist");
        mutableArtists().addFeature(artist);
    }

    @Override
    public void addProducer(final Artist producer) {
        this.failsafe.checkArgument().isNotNull(logger, producer, "producer");
        mutableArtists().addProducer(producer);
    }

    @Override
    public void clearFeatures() {
        mutableArtists().clearFeatures();
    }

    @Override
    public void clearProducers() {
        mutableArtists().clearProducers();
    }

    @Override
    public void removeFeature(final Artist artist) {
        this.failsafe.checkArgument().isNotNull(logger, artist, "artist");
        mutableArtists().removeFeature(artist);
    }

    @Override
    public void removeProducer(final Artist producer) {
        this.failsafe.checkArgument().isNotNull(logger, producer, "producer");
        mutableArtists().removeProducer(producer);
    }

    @Override
    public void setAudioContent(final byte[] audioContent) {
        this.failsafe.checkArgument().isNotNull(logger, audioContent, "audio");
        final BiStream content = audio().content();
        content.clear();
        try (final OutputStream output = content.writeTo()) {
            output.write(audioContent);
        } catch (final IOException e) {
            this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public void setExplicit(final boolean explicit) {
        this.lyrics.setExplicit(explicit);
    }

    @Override
    public void setFeatures(final Collection<Artist> features) {
        this.failsafe.checkArgument().isNotNull(logger, features, "features");
        mutableArtists().setFeatures(features);
    }

    @Override
    public void setLyrics(final String lyrics) {
        this.failsafe.checkArgument().string().isNotBlank(logger, lyrics, "lyrics");
        this.lyrics.setText(lyrics);
    }

    @Override
    public void setProducers(final Collection<Artist> producers) {
        this.failsafe.checkArgument().isNotNull(logger, producers, "producers");
        mutableArtists().setProducers(producers);
    }

    @Override
    public int compareTo(final Track o) {
        final int title = title().toString().compareTo(o.title().toString());
        return (title == 0) ? this.optionalUtils.compare(id(), o.id()) : title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractTrack)) return false;
        return compareTo((AbstractTrack) o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), this.content, this.lyrics, this.optionalUtils, this.failsafe);
    }

    @Override
    public String toString() {
        return "AbstractTrack{" +
                "content=" + this.content +
                ", lyrics=" + this.lyrics +
                ", optionalUtils=" + this.optionalUtils +
                ", injector=" + this.failsafe +
                "} " + super.toString();
    }
}
