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
package org.rookit.utils.graph;

import io.reactivex.Completable;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

final class DependencyWrapperImpl<D> implements DependencyWrapper<D> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DependencyWrapperImpl.class);

    @Nullable
    private D value;
    private final OptionalFactory optionalFactory;
    private final Function<D, Dependency> dependencyFactory;
    private final String dependencyName;

    DependencyWrapperImpl(final OptionalFactory optionalFactory,
                          final Function<D, Dependency> dependencyFactory,
                          final String dependencyName) {
        this(null, optionalFactory, dependencyFactory, dependencyName);
    }

    DependencyWrapperImpl(
            @Nullable final D value,
            final OptionalFactory optionalFactory,
            final Function<D, Dependency> dependencyFactory,
            final String dependencyName) {
        this.value = value;
        this.optionalFactory = optionalFactory;
        this.dependencyFactory = dependencyFactory;
        this.dependencyName = dependencyName;
    }

    @Override
    public Optional<D> get() {
        return this.optionalFactory.ofNullable(this.value);
    }

    @Override
    public Completable set(final D value) {
        if (logger.isTraceEnabled()) {
            logger.trace("Setting '{}' to '{}'", this.dependencyName, value);
        }
        if (Objects.nonNull(this.value)) {
            logger.debug("Overwriting '{}'", this.value);
        }
        this.value = value;
        return Completable.complete();
    }

    @Override
    public Optional<Dependency> asDependency() {
        return get()
                .map(this.dependencyFactory);
    }

}
