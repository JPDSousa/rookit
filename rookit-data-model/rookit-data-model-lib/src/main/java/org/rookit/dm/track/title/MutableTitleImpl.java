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
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.print.FormatType;
import org.rookit.utils.print.PrintUtils;

import java.util.Collection;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@SuppressWarnings("javadoc")
final class MutableTitleImpl implements MutableTitle {

    private final PrintUtils printUtils;
    private final FormatType formatType;
    private final OptionalFactory optionalFactory;
    private final String title;

    private String artists;
    private String extras;
    private String feats;

    private String tokens;
    private String hiddenTrack;

    MutableTitleImpl(final PrintUtils printUtils,
                     final FormatType formatType,
                     final OptionalFactory optionalFactory,
                     final String title) {
        this.printUtils = printUtils;
        this.formatType = formatType;
        this.optionalFactory = optionalFactory;
        checkArgument(Objects.nonNull(title));
        this.title = title;
    }

    @Override
    public MutableTitle appendArtists(final Collection<Artist> artists) {
        checkArgument(Objects.nonNull(artists));
        this.artists = this.printUtils.getIterableAsString(artists, this.formatType);
        return this;
    }

    @Override
    public MutableTitle appendExtras(final String extras) {
        checkArgument(Objects.nonNull(extras));
        this.extras = extras;
        return this;
    }

    @Override
    public MutableTitle appendFeats(final Collection<Artist> artists) {
        checkArgument(Objects.nonNull(artists));
        this.feats = this.printUtils.getIterableAsString(artists, this.formatType);
        return this;
    }

    @Override
    public MutableTitle appendHiddenTrack(final String hiddenTrack) {
        checkArgument(Objects.nonNull(hiddenTrack));
        this.hiddenTrack = hiddenTrack;
        return this;
    }

    @Override
    public Optional<String> artists() {
        return this.optionalFactory.ofNullable(this.artists);
    }

    @Override
    public Optional<String> extras() {
        return this.optionalFactory.ofNullable(this.extras);
    }

    @Override
    public Optional<String> features() {
        return this.optionalFactory.ofNullable(this.feats);
    }

    @Override
    public Optional<String> hiddenTrack() {
        return this.optionalFactory.ofNullable(this.hiddenTrack);
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String toString() {
        return "MutableTitleImpl{" +
                "printUtils=" + this.printUtils +
                ", formatType=" + this.formatType +
                ", optionalFactory=" + this.optionalFactory +
                ", title='" + this.title + '\'' +
                ", artists='" + this.artists + '\'' +
                ", extras='" + this.extras + '\'' +
                ", feats='" + this.feats + '\'' +
                ", tokens='" + this.tokens + '\'' +
                ", hiddenTrack='" + this.hiddenTrack + '\'' +
                "}";
    }
}
