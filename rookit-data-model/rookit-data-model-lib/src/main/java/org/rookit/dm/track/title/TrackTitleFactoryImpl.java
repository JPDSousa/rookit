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
package org.rookit.dm.track.title;

import com.google.inject.Inject;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.api.dm.track.title.TrackTitleFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.print.FormatType;
import org.rookit.utils.print.PrintUtils;
import org.slf4j.LoggerFactory;

import java.util.Collection;

final class TrackTitleFactoryImpl implements TrackTitleFactory {

    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TrackTitleFactoryImpl.class);
    
    private final Failsafe failsafe;
    private final MutableTitleFactory titleFactory;
    private final OptionalFactory optionalFactory;
    private final FormatType formatType;
    private final PrintUtils printUtils;

    @Inject
    private TrackTitleFactoryImpl(final Failsafe failsafe,
                                  final MutableTitleFactory titleFactory,
                                  final OptionalFactory optionalFactory,
                                  final FormatType formatType,
                                  final PrintUtils printUtils) {
        this.failsafe = failsafe;
        this.titleFactory = titleFactory;
        this.optionalFactory = optionalFactory;
        this.formatType = formatType;
        this.printUtils = printUtils;
    }

    @Override
    public TrackTitle create(final String title, final TrackArtists artists) {
        return new TrackTitleImpl(title, artists, this.titleFactory);
    }

    @Override
    public TrackTitle createVersionTrackTitle(final TrackTitle original,
                                              final Collection<Artist> artists,
                                              final TypeVersion typeVersion) {
        return new VersionTrackTitleImpl(original,
                artists, typeVersion, this.optionalFactory, this.formatType, this.printUtils);
    }

    @Override
    public TrackTitle create(final Key key) {
        logger.info("Creation by key is not supported. Creating empty instead");
        return createEmpty();
    }

    @Override
    public TrackTitle createEmpty() {
        return this.failsafe.handleException().runtimeException("Cannot create an empty Track Title.");
    }

    @Override
    public String toString() {
        return "TrackTitleFactoryImpl{" +
                "injector=" + this.failsafe +
                ", titleFactory=" + this.titleFactory +
                ", optionalFactory=" + this.optionalFactory +
                ", formatType=" + this.formatType +
                ", printUtils=" + this.printUtils +
                "}";
    }
}
