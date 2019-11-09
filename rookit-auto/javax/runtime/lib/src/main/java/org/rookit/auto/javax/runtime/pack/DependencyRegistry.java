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
package org.rookit.auto.javax.runtime.pack;

import com.google.inject.Inject;
import io.reactivex.Observable;
import org.reflections.Reflections;
import org.rookit.auto.javax.runtime.element.type.TypeElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityFactory;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.auto.javax.runtime.element.node.dependency.DependencyFactory;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.MultiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DependencyRegistry implements MultiRegistry<RuntimePackageEntity, Dependency<?>> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DependencyRegistry.class);

    private final TypeElementFactory typeFactory;
    private final RuntimeEntityFactory entityFactory;
    private final DependencyFactory dependencyFactory;

    @Inject
    private DependencyRegistry(
            final TypeElementFactory typeFactory,
            final RuntimeEntityFactory entityFactory,
            final DependencyFactory dependencyFactory) {
        this.typeFactory = typeFactory;
        this.entityFactory = entityFactory;
        this.dependencyFactory = dependencyFactory;
    }

    @Override
    public Observable<Dependency<?>> fetch(final RuntimePackageEntity key) {
        final Package pack = key.pack();
        logger.debug("Creating reflections object");
        final Reflections reflections = new Reflections(pack.getName());

        logger.debug("Fetching all types in package '{}'", key.name());
        // TODO confirm that this does not include inner classes.
        return Observable.fromIterable(reflections.getSubTypesOf(Object.class))
                .map(this.entityFactory::fromClass)
                .flatMapSingle(this.typeFactory::createElement)
                .map(this.dependencyFactory::enclosedDependency);
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

    @Override
    public String toString() {
        return "DependencyRegistry{" +
                "typeFactory=" + this.typeFactory +
                ", entityFactory=" + this.entityFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                "}";
    }

}
