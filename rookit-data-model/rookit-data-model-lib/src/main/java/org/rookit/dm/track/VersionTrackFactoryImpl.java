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
package org.rookit.dm.track;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.genre.able.GenreableFactory;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.audio.AudioContentFactory;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.api.dm.track.key.TrackKey;
import org.rookit.dm.track.artists.MutableTrackArtistsFactory;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.dm.track.lyrics.MutableLyricsFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class VersionTrackFactoryImpl implements VersionTrackFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(VersionTrackFactoryImpl.class);

    private final Failsafe failsafe;
    private final MutableTrackArtistsFactory artistsFactory;
    private final AudioContentFactory audioContentFactory;
    private final MutableLyricsFactory lyricsFactory;
    private final OptionalUtils optionalUtils;
    private final GenreableFactory genreableFactory;

    @Inject
    private VersionTrackFactoryImpl(final Failsafe failsafe,
                                    final MutableTrackArtistsFactory artistsFactory,
                                    final AudioContentFactory audioContentFactory,
                                    final MutableLyricsFactory lyricsFactory,
                                    final OptionalUtils optionalUtils,
                                    final GenreableFactory genreableFactory) {
        this.failsafe = failsafe;
        this.artistsFactory = artistsFactory;
        this.audioContentFactory = audioContentFactory;
        this.lyricsFactory = lyricsFactory;
        this.genreableFactory = genreableFactory;
        this.optionalUtils = optionalUtils;
    }

    @Override
    public VersionTrack create(final Track original,
                               final TypeVersion versionType,
                               final Iterable<Artist> versionArtists,
                               final String versionToken) {
        final AudioContent audioContent = this.audioContentFactory.createEmpty();
        final MutableLyrics lyrics = this.lyricsFactory.createEmpty();
        final Genreable genreable = this.genreableFactory.create();

        return new VersionTrackImpl(genreable,
                original,
                this.artistsFactory.create(original),
                versionType,
                versionArtists,
                versionToken,
                audioContent,
                lyrics,
                this.optionalUtils,
                this.failsafe);
    }

    @Override
    public VersionTrack create(final TrackKey key) {
        this.failsafe.checkArgument().isNotNull(logger, key, "key");

        return create(key.original(), key.getVersionType(), key.getVersionArtists(),
                key.versionToken().orElse(StringUtils.EMPTY));
    }

    @Override
    public VersionTrack createEmpty() {
        return this.failsafe.handleException().unsupportedOperation("Cannot create an empty version track.");
    }

    @Override
    public String toString() {
        return "VersionTrackFactoryImpl{" +
                "injector=" + this.failsafe +
                ", artistsFactory=" + this.artistsFactory +
                ", audioContentFactory=" + this.audioContentFactory +
                ", lyricsFactory=" + this.lyricsFactory +
                ", optionalUtils=" + this.optionalUtils +
                ", genreableFactory=" + this.genreableFactory +
                "}";
    }
}
