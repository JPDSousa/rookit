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
package org.rookit.dm.track.lyrics;

import org.rookit.api.dm.track.lyrics.LyricsMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class MutableLyricsImpl implements MutableLyrics {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableLyricsImpl.class);

    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;
    private final LyricsMetaType<?> properties;
    private boolean isSet;
    private boolean explicit;
    private String lyrics;

    MutableLyricsImpl(final Failsafe failsafe,
                      final OptionalFactory optionalFactory,
                      final LyricsMetaType<?> properties) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
        this.properties = properties;
    }

    @Override
    public void setExplicit(final boolean explicit) {
        this.explicit = explicit;
        this.isSet = true;
    }

    @Override
    public void setText(final String text) {
        this.failsafe.checkArgument().string().isNotBlank(logger, text, this.properties.text().propertyName());
        this.lyrics = text;
    }

    @Override
    public Optional<String> text() {
        return this.optionalFactory.ofNullable(this.lyrics);
    }

    @Override
    public OptionalBoolean explicit() {
        return this.isSet
                ? this.optionalFactory.ofBoolean(this.explicit)
                : this.optionalFactory.emptyBoolean();
    }

    @Override
    public String toString() {
        return "MutableLyricsImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                ", properties=" + this.properties +
                ", isSet=" + this.isSet +
                ", explicit=" + this.explicit +
                ", lyrics='" + this.lyrics + '\'' +
                "}";
    }
}
