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

import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.title.Title;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.print.FormatType;
import org.rookit.utils.print.PrintUtils;

import java.util.Collection;
import java.util.Collections;

final class VersionTrackTitleImpl implements TrackTitle {

    private final TrackTitle original;
    private final Collection<Artist> versionArtists;
    private final TypeVersion typeVersion;
    private final OptionalFactory optionalFactory;
    private final FormatType formatType;
    private final PrintUtils printUtils;

    VersionTrackTitleImpl(final TrackTitle original,
                          final Collection<Artist> versionArtists,
                          final TypeVersion typeVersion,
                          final OptionalFactory optionalFactory,
                          final FormatType formatType,
                          final PrintUtils printUtils) {
        this.original = original;
        this.versionArtists = Collections.unmodifiableCollection(versionArtists);
        this.typeVersion = typeVersion;
        this.optionalFactory = optionalFactory;
        this.formatType = formatType;
        this.printUtils = printUtils;
    }

    @Override
    public Title singleTitle() {
        return this.original.singleTitle();
    }

    @Override
    public Title longFullTitle() {
        return new VersionTitleDecorator(this.original.longFullTitle(), getExtras(), this.optionalFactory);
    }

    @Override
    public Title fullTitle() {
        return new VersionTitleDecorator(this.original.fullTitle(), getExtras(), this.optionalFactory);
    }

    private String getExtras() {
        return this.printUtils.getIterableAsString(
                this.versionArtists,
                this.formatType) + " " + this.typeVersion.getName();
    }

    @Override
    public String toString() {
        return "VersionTrackTitleImpl{" +
                "original=" + this.original +
                ", versionArtists=" + this.versionArtists +
                ", typeVersion=" + this.typeVersion +
                ", optionalFactory=" + this.optionalFactory +
                ", formatType=" + this.formatType +
                ", printUtils=" + this.printUtils +
                "}";
    }
}
