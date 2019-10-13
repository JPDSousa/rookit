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

import org.rookit.api.dm.track.title.Title;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

final class VersionTitleDecorator implements Title {

    private final Title delegate;
    private final String extras;
    private final OptionalFactory optionalFactory;

    VersionTitleDecorator(final Title delegate, final String extras, final OptionalFactory optionalFactory) {
        this.delegate = delegate;
        this.extras = extras;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public String title() {
        return this.delegate.title();
    }

    @Override
    public Optional<String> artists() {
        return this.delegate.artists();
    }

    @Override
    public Optional<String> extras() {
        return this.optionalFactory.of(this.extras);
    }

    @Override
    public Optional<String> features() {
        return this.delegate.features();
    }

    @Override
    public Optional<String> hiddenTrack() {
        return this.delegate.features();
    }

    @Override
    public String toString() {
        return "VersionTitleDecorator{" +
                "delegate=" + this.delegate +
                ", extras='" + this.extras + '\'' +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
