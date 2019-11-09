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
package org.rookit.auto.javax.runtime.element.variable.dependency;

import com.google.inject.Inject;
import io.reactivex.Observable;
import org.rookit.auto.javax.runtime.element.RuntimeElementFactory;
import org.rookit.auto.javax.runtime.element.type.parameter.dependency.TypeParameterDependencyFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;
import org.rookit.auto.javax.runtime.mirror.TypeMirrorFactory;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.MultiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class TypeVariableRegistry implements MultiRegistry<RuntimeTypeVariableEntity, Dependency<?>> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeVariableRegistry.class);

    private final TypeMirrorFactory mirrorFactory;
    private final TypeParameterDependencyFactory dependencyFactory;
    private final RuntimeElementFactory elementFactory;

    @Inject
    private TypeVariableRegistry(
            final TypeMirrorFactory mirrorFactory,
            final TypeParameterDependencyFactory dependencyFactory,
            final RuntimeElementFactory elementFactory) {
        this.mirrorFactory = mirrorFactory;
        this.dependencyFactory = dependencyFactory;
        this.elementFactory = elementFactory;
    }

    @Override
    public Observable<Dependency<?>> fetch(final RuntimeTypeVariableEntity key) {

        final Observable<Dependency<?>> bounds = Observable.fromIterable(key.bounds())
                .flatMapSingle(this.mirrorFactory::createFromType)
                .map(this.dependencyFactory::createBoundDependency);

        final Observable<Dependency<?>> genericDeclaration = Observable.just(key.genericDeclaration())
                .flatMapSingle(this.elementFactory::createElement)
                .map(this.dependencyFactory::createGenericElementDependency);

        return Observable.concat(bounds, genericDeclaration);
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

}
