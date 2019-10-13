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
package org.rookit.auto.javax.runtime.element.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.node.dependency.EnclosedDependency;
import org.rookit.auto.javax.runtime.element.node.dependency.EnclosingDependency;
import org.rookit.auto.javax.runtime.element.node.dependency.ElementDependencyVisitor;
import org.rookit.auto.javax.runtime.element.node.dependency.TypeMirrorDependency;
import org.rookit.utils.graph.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ElementDependencySetter implements ElementDependencyVisitor<Single<MutableNodeElement>,
        MutableNodeElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ElementDependencySetter.class);

    @Inject
    private ElementDependencySetter() {}

    @Override
    public Single<MutableNodeElement> visitEnclosedDependency(final EnclosedDependency dependency,
                                                              final MutableNodeElement parameter) {
        return parameter.addEnclosedElement(dependency.element())
                .toSingleDefault(parameter);
    }

    @Override
    public Single<MutableNodeElement> visitEnclosingDependency(final EnclosingDependency dependency,
                                                               final MutableNodeElement parameter) {
        return parameter.enclosingElement(dependency.element())
                .toSingleDefault(parameter);
    }

    @Override
    public Single<MutableNodeElement> visitTypeMirrorDependency(final TypeMirrorDependency dependency,
                                                                final MutableNodeElement parameter) {
        return parameter.typeMirror(dependency.typeMirror())
                .toSingleDefault(parameter);
    }

    @Override
    public Single<MutableNodeElement> visitUnknown(final Dependency dependency,
                                                   final MutableNodeElement parameter) {
        logger.warn("Visiting unknown dependency.");
        return Single.just(parameter);
    }

}
