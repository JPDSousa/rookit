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

import com.google.common.collect.ImmutableList;
import io.reactivex.Completable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

final class MultiDependencyWrapperImpl<D> implements MultiDependencyWrapper<D> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MultiDependencyWrapperImpl.class);

    private List<D> values;
    private final Function<D, Dependency> dependencyFactory;
    private final String dependencyName;

    MultiDependencyWrapperImpl(
            final List<D> values,
            final Function<D, Dependency> dependencyFactory,
            final String dependencyName) {
        this.values = new ArrayList<>(values);
        this.dependencyFactory = dependencyFactory;
        this.dependencyName = dependencyName;
    }

    @Override
    public List<D> get() {
        return Collections.unmodifiableList(this.values);
    }

    @Override
    public Completable set(final Collection<? extends D> values) {
        logger.trace("Setting {} to '{}'", this.dependencyName, values);
        this.values = new ArrayList<>(values);
        return Completable.complete();
    }

    @Override
    public Completable reset() {
        // TODO improve the sizing heuristic
        this.values = new ArrayList<>(this.values.size());
        return Completable.complete();
    }

    @Override
    public Completable add(final D value) {
        logger.trace("Adding '{}' to enclosed elements", value);
        this.values.add(value);
        return Completable.complete();
    }

    @Override
    public Collection<Dependency> asDependency() {
        return get().stream()
                .map(this.dependencyFactory)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public String toString() {
        return "MultiDependencyWrapperImpl{" +
                "values=" + this.values +
                ", dependencyFactory=" + this.dependencyFactory +
                ", dependencyName='" + this.dependencyName + '\'' +
                "}";
    }

}
