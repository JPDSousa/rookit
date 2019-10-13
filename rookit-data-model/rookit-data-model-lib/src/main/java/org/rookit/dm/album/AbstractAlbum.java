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

package org.rookit.dm.album;

import org.apache.commons.io.IOUtils;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.release.Release;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.genre.able.DelegateGenreable;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;


/**
 * Abstract implementation of the {@link Album} interface. Extend this class in
 * order to create a custom album release.
 */
public abstract class AbstractAlbum extends DelegateGenreable implements Album {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractAlbum.class);

    /**
     * Title of the album
     */
    private final String title;
    private final MutableRelease release;

    /**
     * Smof GridFS Reference containing the image of the album
     */
    private final BiStream cover;
    private final MutableAlbumTracks tracks;
    private final OptionalUtils optionalUtils;
    private final Failsafe failsafe;

    /**
     * Default constructor for the object. All subclasses should use this
     * constructor in order to create a fully functional album.
     * @param name title of the album
     * @param release release information about the album
     * @param optionalUtils optional utilities.
     * @param failsafe The injector handler
     */
    AbstractAlbum(final Genreable genreable,
                  final String name,
                  final MutableRelease release,
                  final BiStream cover,
                  final MutableAlbumTracks tracks,
                  final OptionalUtils optionalUtils,
                  final Failsafe failsafe) {
        super(genreable);
        this.title = name;
        this.release = release;
        this.cover = cover;
        this.tracks = tracks;
        this.optionalUtils = optionalUtils;
        this.failsafe = failsafe;
    }

    @Override
    public void addTrack(final Track track, final int number, final String discName) {
        this.tracks.addTrack(track, number, discName);
    }

    @Override
    public final void addTrackLast(final Track track, final String discName) {
        this.tracks.addTrackLast(track, discName);
    }

    @Override
    public void addTrack(final TrackSlot slot) {
        slot.track().ifPresent(track -> addTrack(track, slot.number(), slot.discName()));
    }

    @Override
    public void clearTracks() {
        this.tracks.clearTracks();
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public Collection<Genre> allGenres() {
        return Album.super.allGenres();
    }

    @Override
    public BiStream cover() {
        return this.cover;
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public Optional<Duration> duration() {
        return Album.super.duration();
    }

    @Override
    public final Release release() {
        return this.release;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public final void relocate(final String discName,
            final int number,
            final String newDiscName,
            final int newNumber) {
        this.tracks.relocate(discName, number, newDiscName, newNumber);
    }

    @Override
    public void removeTrack(final int number, final String disc) {
        this.tracks.removeTrack(number, disc);
    }

    @Override
    public void removeTrack(final Track track) {
        this.tracks.removeTrack(track);
    }

    @Override
    public final void setCover(final byte[] bytes) {
        this.failsafe.checkArgument().isNotNull(logger, bytes, "bytes");
        try (final OutputStream output = this.cover.writeTo()) {
            output.write(bytes);
        } catch (final IOException e) {
            this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public final void setDuration(final Duration duration) {
        this.failsafe.handleException().unsupportedOperation("Cannot withProperty duration for albums");
    }

    @Override
    public void setCover(final BiStream image) {
        try (final InputStream input = image.readFrom(); final OutputStream output = this.cover.writeTo()) {
            IOUtils.copy(input, output);
        } catch (final IOException e) {
            this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public final void setReleaseDate(final LocalDate date) {
        this.failsafe.checkArgument().isNotNull(logger, date, "year");
        this.release.setReleaseDate(date);
    }

    @Override
    public AlbumTracks tracks() {
        return this.tracks;
    }

    @Override
    public int compareTo(final Album o) {
        final int title = title().compareTo(o.title());
        return (title == 0) ? this.optionalUtils.compare(id(), o.id()) : title;
    }

    @Override
    public String toString() {
        return "AbstractAlbum{" +
                "title='" + this.title + '\'' +
                ", release=" + this.release +
                ", cover=" + this.cover +
                ", tracks=" + this.tracks +
                ", optionalUtils=" + this.optionalUtils +
                ", injector=" + this.failsafe +
                "} " + super.toString();
    }
}
