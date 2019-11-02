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
package org.rookit.auto.javax.runtime.element.type.dependency;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.rookit.auto.javax.runtime.element.type.node.TypeDependencyFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.mirror.declared.DeclaredTypeFactory;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.registry.MultiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SuperClass implements MultiRegistry<RuntimeClassEntity, Dependency<?>> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(SuperClass.class);

    private final TypeDependencyFactory dependencyFactory;
    private final DeclaredTypeFactory typeFactory;

    @Inject
    private SuperClass(final TypeDependencyFactory dependencyFactory,
                       final DeclaredTypeFactory typeFactory) {
        this.dependencyFactory = dependencyFactory;
        this.typeFactory = typeFactory;
    }

    @Override
    public Observable<Dependency<?>> fetch(final RuntimeClassEntity key) {
        return Maybe.just(key.superClass())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMapSingle(this.typeFactory::createFromClass)
                .<Dependency<?>>map(this.dependencyFactory::createSuperClassDependency)
                .toObservable();
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

    @Override
    public String toString() {
        return "SuperClass{" +
                "dependencyFactory=" + this.dependencyFactory +
                ", typeFactory=" + this.typeFactory +
                "}";
    }

}
