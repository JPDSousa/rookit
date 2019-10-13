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

package org.rookit.dm.genre;

import com.google.inject.Inject;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.api.dm.genre.key.GenreKey;
import org.rookit.api.dm.play.able.PlayableFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
final class GenreFactoryImpl implements GenreFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GenreFactoryImpl.class);

    private final Failsafe failsafe;
    private final OptionalUtils optionalUtils;
    private final PlayableFactory playableFactory;
    private final OptionalFactory optionalFactory;

    @Inject
    private GenreFactoryImpl(final Failsafe failsafe,
                             final OptionalUtils optionalUtils,
                             final PlayableFactory playableFactory,
                             final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalUtils = optionalUtils;
        this.playableFactory = playableFactory;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Genre create(final GenreKey key) {
        this.failsafe.checkArgument().isNotNull(logger, key, "key");
        return createGenre(key.name());
    }

    @Override
    public Genre createEmpty() {
        return this.failsafe.handleException().unsupportedOperation("Cannot create an empty genre");
    }

    @Override
    public Genre createGenre(final String name) {
        this.failsafe.checkArgument().string().isNotBlank(logger, name, "official");
        return new GenreImpl(name,
                this.playableFactory.create(),
                this.optionalUtils,
                this.failsafe,
                this.optionalFactory);
    }

    @Override
    public String toString() {
        return "GenreFactoryImpl{" +
                "injector=" + this.failsafe +
                ", optionalUtils=" + this.optionalUtils +
                ", playableFactory=" + this.playableFactory +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
